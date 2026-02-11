package user_mail_sender.email.dto;

import lombok.Builder;
import user_mail_sender.email.domain.enums.EmailStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record EmailResponseDto (
        UUID id,
        String userId,
        String to,
        String from,
        String subject,
        String body,
        EmailStatus status,
        LocalDateTime sendAt

){}