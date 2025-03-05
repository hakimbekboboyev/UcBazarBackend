package ru.moscow.ucbazar.service;

import org.springframework.stereotype.Service;
import ru.moscow.ucbazar.dto.payment.ConfirmPayment;
import ru.moscow.ucbazar.dto.payment.UcPaymentDto;
import ru.moscow.ucbazar.responses.objectResponse.ResponseAll;
import ru.moscow.ucbazar.responses.payment.*;
import ru.moscow.ucbazar.responses.objectResponse.Error;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class ClonePaymentService {



    public ResponseAll<ResponsePaymentWithoutRegistration> paymentWithoutRegistration(UcPaymentDto ucPaymentDto){

        SentOtpResult sentOtpResult = SentOtpResult.builder()
                .otpSentPhone("+99893*****89")
                .session(1236L)
                .transactionId(536L)
                .build();
        Error sentOtpError = Error.builder()
                .errorMessage("Error bo'lsa keladi bo'lmasa null")
                .errorCode(400)
                .build();

        ResponsePaymentWithoutRegistration result = ResponsePaymentWithoutRegistration.builder()
                .result(sentOtpResult)
                .error(sentOtpError)
                .build();


        return ResponseAll.<ResponsePaymentWithoutRegistration>builder()
                .response(result)
                .status(200)
                .build();
    }

    /*karta ma'lumotlarini kiritgandan keyin raqamga keladigan kodni olib tasdiqlash*/
    public ResponseAll<ConfirmResponse> confirmPayment(ConfirmPayment confirmPayment){
        ConfirmResult confirmResult = ConfirmResult.builder()
                .transactionId(148L)
                .utrno("00842659412")
                .status(1)
                .statusComment("Success")
                .terminalId("92403966")
                .merchantId("90621515")
                .cardNumber("561468******6659")
                .date(new Date())
                .amount(BigDecimal.valueOf(1000))
                .commission(BigDecimal.valueOf(0))
                .totalAmount(BigDecimal.valueOf(1000))
                .cardId(1L)
                .transactionData("Now #1234")
                .build();

        Error error = Error.builder()
                .errorMessage("Error bo'lsa keladi bo'lmasa null")
                .errorCode(400)
                .build();

        ConfirmResponse result = ConfirmResponse.builder()
                .result(confirmResult)
                .error(error)
                .build();

        return ResponseAll.<ConfirmResponse>builder()
                .response(result)
                .status(200)
                .build();
    }


}
