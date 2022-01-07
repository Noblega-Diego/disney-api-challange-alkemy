package com.history.api.disney.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private String requestUri;

    public ErrorResponse(HttpStatus status, Exception e, String requestURI) {
        this.status = status.value();
        this.message = e.getMessage();
        this.requestUri = requestURI;
    }
}
