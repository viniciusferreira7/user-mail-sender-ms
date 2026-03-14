package user_mail_sender.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import user_mail_sender.user.domain.UserModel;
import user_mail_sender.user.producer.UserProducer;
import user_mail_sender.user.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserProducer userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    @Transactional(rollbackFor = Exception.class)
    public UserModel register(UserModel userModel){
        UserModel userRegistered = this.userRepository.save(userModel);

        userProducer.sendMessageWhenUserIsCreated(userRegistered);

        return userRegistered;
    }

    public List<UserModel> findAll() {
        return this.userRepository.findAll();
    }

    public UserModel findById(UUID id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Transactional(rollbackFor = Exception.class)
    public UserModel update(UUID id, UserModel updated) {
        UserModel existing = findById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        return this.userRepository.save(existing);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(UUID id) {
        findById(id);
        this.userRepository.deleteById(id);
    }
}
