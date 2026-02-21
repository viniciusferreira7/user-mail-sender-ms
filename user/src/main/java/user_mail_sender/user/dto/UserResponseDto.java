package user_mail_sender.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Representation of a registered user")
@Builder
public record UserResponseDto(
        @Schema(description = "Unique identifier of the user", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,

        @Schema(description = "Full name of the user", example = "John Doe")
        String name,

        @Schema(description = "Email address of the user", example = "john.doe@example.com")
        String email,

        @Schema(description = "Timestamp when the user was created")
        LocalDateTime createdAt,

        @Schema(description = "Timestamp of the last update to the user")
        LocalDateTime updatedAt
) {}
