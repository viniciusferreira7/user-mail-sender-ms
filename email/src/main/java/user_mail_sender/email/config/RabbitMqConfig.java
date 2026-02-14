package user_mail_sender.email.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String QUEEUE_NAME = "email-queue";

    @Bean
    public Queue queue(){
        return new Queue(QUEEUE_NAME, true);
    }
}
