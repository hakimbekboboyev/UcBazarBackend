package ru.moscow.ucbazar.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.moscow.ucbazar.dto.PaymentUcDto;
import ru.moscow.ucbazar.dto.payment.ConfirmPayment;
import ru.moscow.ucbazar.dto.payment.UcPaymentDto;
import ru.moscow.ucbazar.entity.payment.PaymentUcEntity;
import ru.moscow.ucbazar.entity.UcEntity;
import ru.moscow.ucbazar.entity.payment.UcPayment;
import ru.moscow.ucbazar.enums.PaymentStatusEnum;
import ru.moscow.ucbazar.repository.PaymentUcRepository;
import ru.moscow.ucbazar.repository.PaymentWithRegsRepository;
import ru.moscow.ucbazar.repository.UcRepository;
import ru.moscow.ucbazar.responses.payment.ConfirmResponse;
import ru.moscow.ucbazar.responses.objectResponse.Error;
import ru.moscow.ucbazar.responses.objectResponse.ResponseAll;
import ru.moscow.ucbazar.responses.payment.ResponsePaymentWithoutRegistration;
import ru.moscow.ucbazar.responses.ResponseUcInfo;
import ru.moscow.ucbazar.responses.ResponseUcPayment;

import java.util.Optional;
import java.util.UUID;

@Service
public class UcPaymentService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UcRepository ucRepository;
    @Autowired
    PaymentUcRepository paymentUcRepository;

    @Autowired
    PaymentWithRegsRepository ucPaymentRep;

    @Value("${api.payment}")
    private String api;

    @Value("${api.user.name}")
    private String username;

    @Value("${api.user.password}")
    private String password;

    public ResponseUcPayment postPubgIdUc(HttpServletRequest request, PaymentUcDto paymentUcDto){
        String uuid = request.getHeader("user-uuid");
        ResponseUcPayment responseUcPayment;
        Optional<UcEntity> get_uc = ucRepository.findById(paymentUcDto.getUc_id());
        if(get_uc.isPresent()){
            UcEntity uc = get_uc.get();

            PaymentUcEntity paymentUcEntity = PaymentUcEntity.builder()
                    .card_number(paymentUcDto.getCard_number())
                    .expiry_date(paymentUcDto.getExpiry_date())
                    .pubg_id(paymentUcDto.getPubg_id())
                    .uuid(uuid)
                    .uc_id(paymentUcDto.getUc_id())
                    .build();

            ResponseUcInfo responseUcInfo = ResponseUcInfo.builder()
                    .uc_id(uc.getId())
                    .uc_name(uc.getUc_name())
                    .uc_amount(uc.getUc_amount())
                    .uc_value(uc.getUc_value())
                    .bonus(uc.getBonus())
                    .build();
            responseUcPayment = ResponseUcPayment.builder()
                    .card_number(paymentUcDto.getCard_number())
                    .expiry_date(paymentUcDto.getExpiry_date())
                    .uuid(uuid)
                    .uc_info(responseUcInfo)
                    .build();

            paymentUcRepository.save(paymentUcEntity);
        }
        else {
            return null;
        }
        return responseUcPayment;
    }

    public ResponseAll<ResponsePaymentWithoutRegistration> paymentWithoutRegistration(UcPaymentDto ucPaymentDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        String guid = UUID.randomUUID().toString();
        ucPaymentDto.setExtraId(guid);

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UcPaymentDto> entity = new HttpEntity<>(ucPaymentDto, headers);

        try {
            ResponsePaymentWithoutRegistration result =  restTemplate.exchange(this.api+"/Payment/paymentWithoutRegistration",
                    HttpMethod.POST,
                    entity, ResponsePaymentWithoutRegistration.class).getBody();

            assert result != null;
            UcPayment ucPayment = UcPayment.builder()
                    .sessionId(result.getResult().getSession())
                    .amount(ucPaymentDto.getAmount())
                    .status(PaymentStatusEnum.CREATED)
                    .build();

            ucPaymentRep.save(ucPayment);

            return ResponseAll.<ResponsePaymentWithoutRegistration>builder()
                    .response(result)
                    .status(200)
                    .build();
        }
        catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 400) {
                ResponsePaymentWithoutRegistration result =  e.getResponseBodyAs(ResponsePaymentWithoutRegistration.class);
                return ResponseAll.<ResponsePaymentWithoutRegistration>builder()
                        .response(result)
                        .status(400)
                        .build();


            }
            if(e.getStatusCode().value() == 403){
                Error error = Error.builder()
                        .errorCode(403)
                        .errorMessage(e.getMessage())
                        .build();
                ResponsePaymentWithoutRegistration response = ResponsePaymentWithoutRegistration.builder()
                        .result(null)
                        .error(error)
                        .build();
                return ResponseAll.<ResponsePaymentWithoutRegistration>builder()
                        .response(response)
                        .status(403)
                        .build();
            }
            throw e;


        }
    }

    public ResponseAll<ConfirmResponse> confirmPayment(ConfirmPayment confirmPayment){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ConfirmPayment> entity = new HttpEntity<>(confirmPayment, headers);

        try {

            ConfirmResponse result = restTemplate.exchange(this.api + "/Payment/confirmPayment",
                    HttpMethod.POST,
                    entity, ConfirmResponse.class).getBody();

            assert result != null;

            Optional<UcPayment> byId = ucPaymentRep.findBySessionId(confirmPayment.getSession());
            if(byId.isPresent()){
                UcPayment ucPayment = byId.get();
                ucPayment.setUuid(UUID.randomUUID().toString());
                ucPayment.setCardNumber(result.getResult().getCardNumber());
                ucPayment.setStatus(PaymentStatusEnum.SUCCEEDED);
                ucPaymentRep.save(ucPayment);
            }

            return ResponseAll.<ConfirmResponse>builder()
                    .response(result)
                    .status(200)
                    .build();
        }
        catch (HttpClientErrorException e) {


            if (e.getStatusCode().value() == 400) {
                ConfirmResponse result =  e.getResponseBodyAs(ConfirmResponse.class);

                assert result != null;

                Optional<UcPayment> byId = ucPaymentRep.findBySessionId(confirmPayment.getSession());
                if(byId.isPresent()){
                    UcPayment ucPayment = byId.get();
                    ucPayment.setStatus(PaymentStatusEnum.FAILED);
                    ucPaymentRep.save(ucPayment);
                }

                return ResponseAll.<ConfirmResponse>builder()
                        .response(result)
                        .status(400)
                        .build();
            }
            if(e.getStatusCode().value() == 403){
                Error error = Error.builder()
                        .errorCode(403)
                        .errorMessage(e.getMessage())
                        .build();
                ConfirmResponse response = ConfirmResponse.builder()
                        .result(null)
                        .error(error)
                        .build();
                return ResponseAll.<ConfirmResponse>builder()
                        .response(response)
                        .status(403)
                        .build();
            }
            throw e;
        }
    }
}
