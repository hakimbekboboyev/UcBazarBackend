package ru.moscow.ucbazar.restController.paymentController;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.moscow.ucbazar.dto.OwnerCardNum;
import ru.moscow.ucbazar.dto.payment.ConfirmPayment;
import ru.moscow.ucbazar.dto.payment.UcPaymentDto;
import ru.moscow.ucbazar.model.GetCardOwnerInfo;
import ru.moscow.ucbazar.model.ResendOtp;
import ru.moscow.ucbazar.responses.objectResponse.ResponseResult;

import ru.moscow.ucbazar.responses.objectResponse.ResponseAll;
import ru.moscow.ucbazar.responses.payment.ConfirmResult;


import ru.moscow.ucbazar.responses.payment.SentOtpResult;
import ru.moscow.ucbazar.service.UcPaymentService;

@RestController
@RequestMapping("/api")
public class UcPaymentController {
    @Autowired
    UcPaymentService ucPaymentService;
/*
    @Operation(summary = "createPayment", description = "Create Payment")
    @PostMapping("/createPayment")
    public ResponseEntity<ResponseUcPayment> postPaymentUc(@RequestBody PaymentUcDto paymentUcDto,
                                                           @RequestHeader(value="user-uuid") HttpServletRequest request){
        ResponseUcPayment responseUcPayment = ucPaymentService.postPubgIdUc(request, paymentUcDto);

        return ResponseEntity.ok(responseUcPayment);

    }*/

    @Operation(summary = "paymentWithRegs", description = "Pyment WithReg")
    @PostMapping("/payment")
    public ResponseEntity<ResponseResult<SentOtpResult>> payment(@RequestBody UcPaymentDto ucPaymentDto){
        ResponseAll<ResponseResult<SentOtpResult>> result = ucPaymentService.paymentWithoutRegistration(ucPaymentDto);

        return ResponseEntity.status(result.getStatus()).body(result.getResponse());
    }

    @Operation(summary = "confirmPayment", description = "Pyment Confirm")
    @PostMapping("/paymentConfirm")
    public ResponseEntity<ResponseResult<ConfirmResult>> payment(@RequestBody ConfirmPayment confirmPayment){
        ResponseAll<ResponseResult<ConfirmResult>> result = ucPaymentService.confirmPayment(confirmPayment);

        return ResponseEntity.status(result.getStatus()).body(result.getResponse());
    }

    @Operation(summary = "GetCardInfo", description = "Get Card Info")
    @PostMapping("/getInfo")
    public ResponseEntity<ResponseResult<GetCardOwnerInfo>> getCardInfo(@RequestBody OwnerCardNum ownerCardNum){
        ResponseAll<ResponseResult<GetCardOwnerInfo>> result = ucPaymentService.getCardInfoByCard(ownerCardNum);

        return ResponseEntity.status(result.getStatus()).body(result.getResponse());
    }


    @Operation(summary = "resendOtp", description = "Resend Code Otp")
    @GetMapping("/resendOtp")
    public ResponseEntity<ResponseResult<ResendOtp>> getCardInfo(@RequestParam Long session){
        ResponseAll<ResponseResult<ResendOtp>> result = ucPaymentService.resendOtp(session);

        return ResponseEntity.status(result.getStatus()).body(result.getResponse());
    }

}
