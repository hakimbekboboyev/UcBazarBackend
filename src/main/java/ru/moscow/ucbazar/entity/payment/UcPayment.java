package ru.moscow.ucbazar.entity.payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "_payment")
public class UcPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long gameId;

    private String cardNumber;

    private Double amount;

    private String expireDate;

    private String extraId;

    private String transactionData;

    private String status;


}
