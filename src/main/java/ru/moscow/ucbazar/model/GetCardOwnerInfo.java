package ru.moscow.ucbazar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class GetCardOwnerInfo {

    private String cardNumber;

    private String owner;

    private BankInfo bankInfo;

}
