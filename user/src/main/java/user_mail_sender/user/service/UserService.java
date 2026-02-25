package user_mail_sender.user.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import user_mail_sender.user.domain.UserModel;
import user_mail_sender.user.producer.UserProducer;
import user_mail_sender.user.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserProducer userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    @Transactional
    public UserModel register(UserModel userModel){
        UserModel userRegistered = this.userRepository.save(userModel);

        userProducer.sendMessage(userRegistered);

        return userRegistered;
    }
}
