package user_mail_sender.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserRequestDTO (
    @NotBlank String name,
    @NotBlank @Email String email
){}
