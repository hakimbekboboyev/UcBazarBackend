package ru.moscow.ucbazar.entity.payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moscow.ucbazar.enums.PaymentStatusEnum;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "_payment")
public class UcPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String uuid;

    private long sessionId;

    private String cardNumber;

    private BigDecimal amount;

    private long uc_id;

    private long pubg_id;


    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum status;


}
