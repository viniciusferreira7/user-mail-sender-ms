package user_mail_sender.email.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMq {
    private final String QUEEUE_NAME = "email-queue";

    public Queue queue(){
        return new Queue(this.QUEEUE_NAME, true);
    }
}
