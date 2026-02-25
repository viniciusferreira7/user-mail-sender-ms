package user_mail_sender.email.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import user_mail_sender.email.config.RabbitMqConfig;
import user_mail_sender.email.dto.UserCreatedDto;

@Component
public class EmailConsumer {
    @RabbitListener(queues = RabbitMqConfig.QUEEUE_NAME)
    public void listenEmailQueue(@Payload UserCreatedDto message){
        System.out.println("Consuming email message: " + message);
    }
}
