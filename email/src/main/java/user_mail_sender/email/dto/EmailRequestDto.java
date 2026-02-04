package user_mail_sender.email.dto;

import lombok.Builder;
import user_mail_sender.email.domain.enums.EmailStatus;

@Builder
public record EmailRequestDto (
     String userId,
     String to,
     String from,
     String subject,
     String body,
     EmailStatus status

){}
