package ru.moscow.ucbazar.responses.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ConfirmResult {
    private Long transactionId;
    private String utrno;
    private Integer status;
    private String statusComment;
    private String terminalId;
    private String merchantId;
    private String cardNumber;
    private Date date;
    private BigDecimal amount;
    private Long cardId;
    private BigDecimal commission;
    private BigDecimal totalAmount;
    private String transactionData;

}
