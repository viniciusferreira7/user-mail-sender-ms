package user_mail_sender.user.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import user_mail_sender.user.domain.UserModel;
import user_mail_sender.user.dto.UserRequestDto;
import user_mail_sender.user.dto.UserResponseDto;
import user_mail_sender.user.mapper.UserDtoMapper;
import user_mail_sender.user.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserModel register(UserModel userModel){

        return this.userRepository.save(userModel);
    }
}
