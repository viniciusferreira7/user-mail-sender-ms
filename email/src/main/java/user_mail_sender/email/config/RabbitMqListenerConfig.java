package user_mail_sender.email.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMqListenerConfig {
}
