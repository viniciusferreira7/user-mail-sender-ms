package user_mail_sender.email.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class RabbitMqConfig {
    public static final String QUEEUE_NAME = "email-queue";

    @Bean
    public Queue queue(){
        return new Queue(QUEEUE_NAME, true);
    }

    @Bean
    public JacksonJsonMessageConverter messageConverter(){

        return new JacksonJsonMessageConverter()
    }
}
