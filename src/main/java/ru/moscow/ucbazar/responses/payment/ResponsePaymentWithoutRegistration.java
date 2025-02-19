package ru.moscow.ucbazar.responses.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class ResponsePaymentWithoutRegistration {

    private SentOtpResult result;

    private Error error;

}
