package ru.moscow.ucbazar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.moscow.ucbazar.dto.OwnerCardNum;
import ru.moscow.ucbazar.dto.payment.ConfirmPayment;
import ru.moscow.ucbazar.dto.payment.UcPaymentDto;

import ru.moscow.ucbazar.model.GetCardOwnerInfo;
import ru.moscow.ucbazar.entity.payment.UcPayment;
import ru.moscow.ucbazar.enums.PaymentStatusEnum;

import ru.moscow.ucbazar.repository.PaymentWithRegsRepository;
import ru.moscow.ucbazar.repository.UcRepository;
import ru.moscow.ucbazar.responses.objectResponse.ResponseResult;

import ru.moscow.ucbazar.responses.objectResponse.Error;
import ru.moscow.ucbazar.responses.objectResponse.ResponseAll;
import ru.moscow.ucbazar.responses.payment.ConfirmResult;
import ru.moscow.ucbazar.responses.payment.SentOtpResult;


import java.util.Optional;
import java.util.UUID;

@Service
public class UcPaymentService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UcRepository ucRepository;
    /*@Autowired
    PaymentUcRepository paymentUcRepository;*/

    @Autowired
    PaymentWithRegsRepository ucPaymentRep;

    @Value("${api.payment}")
    private String api;

    @Value("${api.user.name}")
    private String username;

    @Value("${api.user.password}")
    private String password;

    /*public ResponseUcPayment postPubgIdUc(HttpServletRequest request, PaymentUcDto paymentUcDto){
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
    }*/

    public ResponseAll<ResponseResult<SentOtpResult>> paymentWithoutRegistration(UcPaymentDto ucPaymentDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        String guid = UUID.randomUUID().toString();
        ucPaymentDto.setExtraId(guid);

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UcPaymentDto> entity = new HttpEntity<>(ucPaymentDto, headers);

        try {
            ResponseResult<SentOtpResult> result =  restTemplate.exchange(this.api + "/Payment/paymentWithoutRegistration",
                    HttpMethod.POST,
                    entity, new ParameterizedTypeReference<ResponseResult<SentOtpResult>>() {
                    }).getBody();

            assert result != null;
            OwnerCardNum ownerCardNum = OwnerCardNum.builder()
                    .cardNumber(ucPaymentDto.getCardNumber())
                    .build();
            ResponseAll<ResponseResult<GetCardOwnerInfo>> cardInfoByCard = getCardInfoByCard(ownerCardNum);
            UcPayment ucPayment = UcPayment.builder()
                    .cardNumber(cardInfoByCard.getResponse().getResult().getCardNumber())
                    .sessionId(result.getResult().getSession())
                    .amount(ucPaymentDto.getAmount())
                    .status(PaymentStatusEnum.CREATED)
                    .uuid(UUID.randomUUID().toString())
                    .build();

            ucPaymentRep.save(ucPayment);

            return ResponseAll.<ResponseResult<SentOtpResult>>builder()
                    .response(result)
                    .status(200)
                    .build();
        }
        catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 400) {
                ResponseResult<SentOtpResult> result =  e.getResponseBodyAs(new ParameterizedTypeReference<ResponseResult<SentOtpResult>>() {});
                return ResponseAll.<ResponseResult<SentOtpResult>>builder()
                        .response(result)
                        .status(400)
                        .build();


            }
            if(e.getStatusCode().value() == 403){
                Error error = Error.builder()
                        .errorCode(403)
                        .errorMessage(e.getMessage())
                        .build();
                ResponseResult<SentOtpResult> response = ResponseResult.<SentOtpResult>builder()
                        .result(null)
                        .error(error)
                        .build();
                return ResponseAll.<ResponseResult<SentOtpResult>>builder()
                        .response(response)
                        .status(403)
                        .build();
            }
            throw e;


        }
    }

    public ResponseAll<ResponseResult<ConfirmResult>> confirmPayment(ConfirmPayment confirmPayment){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ConfirmPayment> entity = new HttpEntity<>(confirmPayment, headers);

        try {

            ResponseResult<ConfirmResult> result = restTemplate.exchange(this.api + "/Payment/confirmPayment",
                    HttpMethod.POST,
                    entity, new ParameterizedTypeReference<ResponseResult<ConfirmResult>>() {
                    }).getBody();

            assert result != null;

            Optional<UcPayment> byId = ucPaymentRep.findBySessionId(confirmPayment.getSession());
            if(byId.isPresent()){
                UcPayment ucPayment = byId.get();
                ucPayment.setUuid(UUID.randomUUID().toString());
                ucPayment.setCardNumber(result.getResult().getCardNumber());
                ucPayment.setStatus(PaymentStatusEnum.SUCCEEDED);
                ucPaymentRep.save(ucPayment);
            }

            return ResponseAll.<ResponseResult<ConfirmResult>>builder()
                    .response(result)
                    .status(200)
                    .build();
        }
        catch (HttpClientErrorException e) {


            if (e.getStatusCode().value() == 400) {
                ResponseResult<ConfirmResult> result =  e.getResponseBodyAs(new ParameterizedTypeReference<ResponseResult<ConfirmResult>>() {});

                assert result != null;

                Optional<UcPayment> byId = ucPaymentRep.findBySessionId(confirmPayment.getSession());
                if(byId.isPresent()){
                    UcPayment ucPayment = byId.get();
                    ucPayment.setStatus(PaymentStatusEnum.FAILED);
                    ucPaymentRep.save(ucPayment);
                }

                return ResponseAll.<ResponseResult<ConfirmResult>>builder()
                        .response(result)
                        .status(400)
                        .build();
            }
            if(e.getStatusCode().value() == 403){
                Error error = Error.builder()
                        .errorCode(403)
                        .errorMessage(e.getMessage())
                        .build();
                ResponseResult<ConfirmResult> response = ResponseResult.<ConfirmResult>builder()
                        .result(null)
                        .error(error)
                        .build();
                return ResponseAll.<ResponseResult<ConfirmResult>>builder()
                        .response(response)
                        .status(403)
                        .build();
            }
            throw e;
        }
    }


    public ResponseAll<ResponseResult<GetCardOwnerInfo>> getCardInfoByCard(OwnerCardNum ownerCardNum){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OwnerCardNum> entity = new HttpEntity<>(ownerCardNum, headers);

        try {
            ResponseEntity<ResponseResult<GetCardOwnerInfo>> response = restTemplate.exchange(
                    this.api + "/UserCard/getCardOwnerInfoByPan",
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<ResponseResult<GetCardOwnerInfo>>() {}
            );

            ResponseResult<GetCardOwnerInfo> result = response.getBody();

            assert result != null;


            return ResponseAll.<ResponseResult<GetCardOwnerInfo>>builder()
                    .response(result)
                    .status(200)
                    .build();
        }catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 400) {
                ResponseResult<GetCardOwnerInfo> result =  e.getResponseBodyAs(new ParameterizedTypeReference<ResponseResult<GetCardOwnerInfo>>() {});

                assert result != null;

                return ResponseAll.<ResponseResult<GetCardOwnerInfo>>builder()
                        .response(result)
                        .status(400)
                        .build();
            }
            if(e.getStatusCode().value() == 403){
                Error error = Error.builder()
                        .errorCode(403)
                        .errorMessage(e.getMessage())
                        .build();
                ResponseResult<GetCardOwnerInfo> response = ResponseResult.<GetCardOwnerInfo>builder()
                        .result(null)
                        .error(error)
                        .build();
                return ResponseAll.<ResponseResult<GetCardOwnerInfo>>builder()
                        .response(response)
                        .status(403)
                        .build();
            }
            throw e;
        }




    }
}
