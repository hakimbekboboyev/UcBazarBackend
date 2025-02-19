package ru.moscow.ucbazar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentUcDto {
    private String card_number;
    private String expiry_date;
    private long uc_id;
    private long pubg_id;
}
