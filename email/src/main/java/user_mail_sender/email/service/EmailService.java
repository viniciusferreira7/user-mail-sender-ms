package user_mail_sender.email.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import user_mail_sender.email.domain.EmailModel;
import user_mail_sender.email.domain.enums.EmailStatus;
import user_mail_sender.email.dto.UserCreatedDto;
import user_mail_sender.email.repository.EmailRepository;

@Slf4j
@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    public EmailService(EmailRepository emailRepository, JavaMailSender mailSender) {
        this.emailRepository = emailRepository;
        this.mailSender = mailSender;
    }

    public void sendWelcomeEmail(UserCreatedDto userCreatedDto) {
        EmailModel emailModel = new EmailModel();
        emailModel.setUserId(userCreatedDto.userId());
        emailModel.setEmailTo(userCreatedDto.email());
        emailModel.setEmailFrom(emailFrom);
        emailModel.setSubject("Welcome, " + userCreatedDto.name() + "!");
        emailModel.setBody(buildWelcomeBody(userCreatedDto.name()));
        emailModel.setStatus(EmailStatus.SENDING);

        emailRepository.save(emailModel);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getBody());

            mailSender.send(message);

            emailModel.setStatus(EmailStatus.SENT);
            log.info("Email sent successfully to {}", emailModel.getEmailTo());
        } catch (MailException e) {
            emailModel.setStatus(EmailStatus.FAILED);
            log.error("Failed to send email to {}: {}", emailModel.getEmailTo(), e.getMessage());
        } finally {
            emailRepository.save(emailModel);
        }
    }

    private String buildWelcomeBody(String name) {
        return """
                Hello, %s!

                Welcome to our platform. We're glad to have you on board.

                If you have any questions, feel free to reach out to our support team.

                Best regards,
                The Team
                """.formatted(name);
    }
}
