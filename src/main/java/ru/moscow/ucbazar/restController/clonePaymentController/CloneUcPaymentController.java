package ru.moscow.ucbazar.restController.clonePaymentController;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.moscow.ucbazar.dto.payment.ConfirmPayment;
import ru.moscow.ucbazar.dto.payment.UcPaymentDto;
import ru.moscow.ucbazar.responses.payment.ConfirmResponse;
import ru.moscow.ucbazar.responses.payment.ResponseAll;
import ru.moscow.ucbazar.responses.payment.ResponsePaymentWithoutRegistration;
import ru.moscow.ucbazar.service.ClonePaymentService;
import ru.moscow.ucbazar.service.UcPaymentService;

@RestController()
@RequestMapping("/api/clone")
public class CloneUcPaymentController {
    @Autowired
    ClonePaymentService ucPaymentService;

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
