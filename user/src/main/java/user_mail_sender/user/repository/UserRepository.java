package user_mail_sender.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import user_mail_sender.user.domain.UserModel;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
}
