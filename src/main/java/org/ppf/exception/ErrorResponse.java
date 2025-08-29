package org.ppf.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private Instant timestamp;
    private String path;
}