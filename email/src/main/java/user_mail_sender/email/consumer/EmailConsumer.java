package user_mail_sender.email.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import user_mail_sender.email.config.RabbitMqConfig;
import user_mail_sender.email.dto.UserCreatedDto;
import user_mail_sender.email.service.EmailService;

@Slf4j
@Component
public class EmailConsumer {

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = RabbitMqConfig.QUEEUE_NAME)
    public void listenEmailQueue(@Payload UserCreatedDto message) {
        log.info("Received message for user: {}", message.email());
        emailService.sendWelcomeEmail(message);
    }
}
