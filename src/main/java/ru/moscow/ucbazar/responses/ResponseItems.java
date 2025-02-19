package ru.moscow.ucbazar.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moscow.ucbazar.entity.UcEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseItems {
    private String banner_image;
    private String title;
    private boolean isActive;
    private List<UcEntity> uc;
}
