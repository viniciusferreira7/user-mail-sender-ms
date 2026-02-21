package user_mail_sender.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Schema(description = "Payload for registering a new user")
@Builder
public record UserRequestDto(
    @Schema(description = "Full name of the user", example = "John Doe")
    @NotBlank String name,

    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    @NotBlank @Email String email
){}
