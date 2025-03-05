package ru.moscow.ucbazar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "ucEntity")
public class UcEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private String uc_name;

    private String uc_image;

    private Integer uc_amount;

    private Integer uc_value;

    private Integer bonus;




}
