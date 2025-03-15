package ru.moscow.ucbazar.restController.exceptionController;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import ru.moscow.ucbazar.responses.objectResponse.Error;
import ru.moscow.ucbazar.responses.objectResponse.ResponseResult;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Error> methodNotSupport(Exception ex, WebRequest request) {
        Error error = new Error(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Error> httpClientError(HttpClientErrorException ex, WebRequest request) {
        ResponseResult<Object> result = ex.getResponseBodyAs(new ParameterizedTypeReference<>() {
        });
        assert result != null;
        Error error = Error.builder()
                .errorMessage(result.getError().getErrorMessage())
                .errorCode(result.getError().getErrorCode())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<Error> forbidden(HttpClientErrorException ex, WebRequest request) {

        Error error = new Error(HttpStatus.FORBIDDEN.value(), "403 Forbidden");
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<Error> unauthorized(HttpClientErrorException ex, WebRequest request) {
        Error error = new Error(HttpStatus.UNAUTHORIZED.value(), "401 Unauthorized");
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> notReadable(HttpMessageNotReadableException ex, WebRequest request) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), "Required request body is missing");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Error> missingServletRequestParameterException(MissingServletRequestParameterException ex, WebRequest request) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
