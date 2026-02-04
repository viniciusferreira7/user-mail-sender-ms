package user_mail_sender.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import user_mail_sender.email.domain.EmailModel;

import java.util.UUID;

public interface EmailRepository extends JpaRepository<EmailModel, UUID> {
}
