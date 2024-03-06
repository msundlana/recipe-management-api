package com.github.msundlana.recipemanagementservice.models;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ErrorResponseDto {
    private final String message;
}
