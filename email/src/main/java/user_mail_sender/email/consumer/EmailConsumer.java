package user_mail_sender.email.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import user_mail_sender.email.config.RabbitMqConfig;

@Component
public class EmailConsumer {
    @RabbitListener(queues = RabbitMqConfig.QUEEUE_NAME)
    public void listenEmailQueue(@Payload String string){
        System.out.println("Consuming email message: " + string);
    }
}
