package ru.moscow.ucbazar.dto.payment;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class UcPaymentDto {

    private String cardNumber;

    private String expireDate;

    private BigDecimal amount;

    @Hidden
    private String extraId;

    private String transactionData;
}
