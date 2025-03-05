package ru.moscow.ucbazar.responses.objectResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseResult<T> {
    private T result;
    private Error error;
}
