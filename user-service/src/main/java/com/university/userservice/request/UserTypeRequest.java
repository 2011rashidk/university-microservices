package com.university.userservice.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserTypeRequest {

    @NotEmpty
    private String typeName;
}
