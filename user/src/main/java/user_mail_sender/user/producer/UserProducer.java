package user_mail_sender.user.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import user_mail_sender.user.config.RabbitMqConfig;
import user_mail_sender.user.domain.UserModel;
import user_mail_sender.user.dto.UserCreatedDto;

@Component
public class UserProducer {
    private final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(UserModel userModel){
        UserCreatedDto userCreatedDto = UserCreatedDto.builder()
                .userId(userModel.getId())
                .name(userModel.getName())
                .email(userModel.getEmail())
                .createdAt(userModel.getCreatedAt())
                .build();

        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE_NAME,
                RabbitMqConfig.ROUTING_KEY,
                userCreatedDto
        );
    }
}
