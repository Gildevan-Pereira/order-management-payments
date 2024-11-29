package com.ms_payments.exception;

import com.ms_payments.exception.dto.ErrorDto;
import com.ms_payments.exception.dto.ErrorResponseDto;
import com.ms_payments.messages.MessageEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
        log.error("ApiExceptionHandler.handleException: Error received: | message: {}", ex.getMessage());
        var errors = List.of(new ErrorDto(MessageEnum.GENERIC_ERROR.getCode(), MessageEnum.GENERIC_ERROR.getMessage()));
        var responseBody = new ErrorResponseDto(errors);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException ex) {
        log.error("ApiExceptionHandler.handleBusinessException: Error received: | message: {}", ex.getMessage());
        var responseBody = new ErrorResponseDto(List.of(new ErrorDto(ex.getCode(), ex.getMessage())));
        return ResponseEntity.status(ex.getHttpStatus().value()).body(responseBody);
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<ErrorResponseDto> handleInternalException(InternalException ex) {
        log.error("ApiExceptionHandler.handleInternalException: Error received: | message: {}", ex.getMessage());
        var responseBody = new ErrorResponseDto(List.of(new ErrorDto(ex.getCode(), ex.getMessage())));
        return ResponseEntity.status(ex.getHttpStatus().value()).body(responseBody);
    }
}
