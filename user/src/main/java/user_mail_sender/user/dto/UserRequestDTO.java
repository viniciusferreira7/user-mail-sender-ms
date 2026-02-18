package user_mail_sender.user.dto;

import lombok.Builder;

@Builder
public record UserRequestDTO (
    String name,
    String email
){}
