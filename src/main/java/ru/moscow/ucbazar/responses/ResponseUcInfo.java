package ru.moscow.ucbazar.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseUcInfo {
    private long uc_id;

    private String uc_name;

    private Integer uc_amount;

    private Integer uc_value;

    private Integer bonus;

}
