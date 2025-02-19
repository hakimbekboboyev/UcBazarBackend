package ru.moscow.ucbazar.responses.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SentOtpResult {
    private Long session;

    private Long transactionId;

    private String otpSentPhone;

}
