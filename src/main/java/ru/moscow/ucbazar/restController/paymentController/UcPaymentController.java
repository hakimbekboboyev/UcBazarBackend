package ru.moscow.ucbazar.restController.paymentController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.moscow.ucbazar.dto.PaymentUcDto;
import ru.moscow.ucbazar.dto.payment.ConfirmPayment;
import ru.moscow.ucbazar.dto.payment.UcPaymentDto;
import ru.moscow.ucbazar.responses.payment.ConfirmResponse;
import ru.moscow.ucbazar.responses.payment.ResponseAll;
import ru.moscow.ucbazar.responses.payment.ResponsePaymentWithoutRegistration;
import ru.moscow.ucbazar.responses.ResponseUcPayment;
import ru.moscow.ucbazar.service.UcPaymentService;

@RestController
@RequestMapping("/api")
public class UcPaymentController {
    @Autowired
    UcPaymentService ucPaymentService;

    @Operation(summary = "createPayment", description = "Create Payment")
    @PostMapping("/createPayment")
    public ResponseEntity<ResponseUcPayment> postPaymentUc(@RequestBody PaymentUcDto paymentUcDto,
                                                           @RequestHeader(value="user-uuid") HttpServletRequest request){
        ResponseUcPayment responseUcPayment = ucPaymentService.postPubgIdUc(request, paymentUcDto);

        return ResponseEntity.ok(responseUcPayment);

    }

    @Operation(summary = "paymentWithRegs", description = "Pyment WithReg")
    @PostMapping("/payment")
    public ResponseEntity<ResponsePaymentWithoutRegistration> payment(@RequestBody UcPaymentDto ucPaymentDto){
        ResponseAll<ResponsePaymentWithoutRegistration> result = ucPaymentService.paymentWithoutRegistration(ucPaymentDto);

        return ResponseEntity.status(result.getStatus()).body(result.getPayment());
    }

    @Operation(summary = "confirmPayment", description = "Pyment Confirm")
    @PostMapping("/paymentConfirm")
    public ResponseEntity<ConfirmResponse> payment(@RequestBody ConfirmPayment confirmPayment){
        ResponseAll<ConfirmResponse> result = ucPaymentService.confirmPayment(confirmPayment);

        return ResponseEntity.status(result.getStatus()).body(result.getPayment());
    }

}
