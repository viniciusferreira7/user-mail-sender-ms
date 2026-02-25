package user_mail_sender.email.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserCreatedDto(
        UUID userId,
        String name,
        String email,
        LocalDateTime createdAt
) {}
