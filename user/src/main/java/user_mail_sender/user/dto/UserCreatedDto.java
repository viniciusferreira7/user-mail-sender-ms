package user_mail_sender.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserCreatedDto(
        @NotNull
        UUID userId,

        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotNull
        LocalDateTime createdAt
) {}
