package user_mail_sender.email.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import user_mail_sender.email.domain.enums.EmailStatus;

@Schema(description = "Payload for sending an email")
@Builder
public record EmailRequestDto(
        @Schema(description = "ID of the user sending the email", example = "550e8400-e29b-41d4-a716-446655440000")
        @NotBlank
        String userId,

        @Schema(description = "Recipient email address", example = "recipient@example.com")
        @NotBlank @Email
        String to,

        @Schema(description = "Sender email address", example = "sender@example.com")
        @NotBlank @Email
        String from,

        @Schema(description = "Subject line of the email", example = "Welcome to our platform!")
        @NotBlank
        String subject,

        @Schema(description = "Body content of the email", example = "Hello, welcome aboard!")
        @NotBlank
        String body,

        @Schema(description = "Current status of the email", example = "SENT")
        @NotNull
        EmailStatus status
){}
