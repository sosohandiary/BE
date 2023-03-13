package com.hanghae.sosohandiary.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {

    private final String msg;
    private final int statusCode;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorHandling errorHandling) {
        return ResponseEntity
                .status(errorHandling.getHttpStatus())
                .body(ErrorResponse.builder()
                        .msg(errorHandling.getMsg())
                        .statusCode(errorHandling.getHttpStatus().value())
                        .build());

    }

}
