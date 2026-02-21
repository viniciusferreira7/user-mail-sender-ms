package user_mail_sender.email.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import user_mail_sender.email.domain.enums.EmailStatus;

@Schema(description = "Payload for sending an email")
@Builder
public record EmailRequestDto(
        @Schema(description = "ID of the user sending the email", example = "550e8400-e29b-41d4-a716-446655440000")
        String userId,

        @Schema(description = "Recipient email address", example = "recipient@example.com")
        String to,

        @Schema(description = "Sender email address", example = "sender@example.com")
        String from,

        @Schema(description = "Subject line of the email", example = "Welcome to our platform!")
        String subject,

        @Schema(description = "Body content of the email", example = "Hello, welcome aboard!")
        String body,

        @Schema(description = "Current status of the email", example = "SENT")
        EmailStatus status
){}
