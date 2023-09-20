package com.university.userservice.response;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class Response {

    private HttpStatus httpStatus;
    private String message;

    @Nullable
    private Object data;
}
