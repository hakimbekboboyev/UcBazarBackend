package ru.moscow.ucbazar.responses.objectResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseAll<T> {
    private T response;
    private int status;
}
