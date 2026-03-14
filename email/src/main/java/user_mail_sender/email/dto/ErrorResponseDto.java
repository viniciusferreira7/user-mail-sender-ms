package user_mail_sender.email.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Error response payload")
public record ErrorResponseDto(
        @Schema(description = "HTTP status code", example = "400")
        int status,

        @Schema(description = "Error message", example = "Validation failed")
        String message,

        @Schema(description = "Timestamp of the error")
        LocalDateTime timestamp,

        @Schema(description = "Field-level validation errors")
        Map<String, String> errors
) {
    public ErrorResponseDto(int status, String message) {
        this(status, message, LocalDateTime.now(), null);
    }

    public ErrorResponseDto(int status, String message, Map<String, String> errors) {
        this(status, message, LocalDateTime.now(), errors);
    }
}
