package user_mail_sender.user.service;

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

    public UserResponseDto createUser(UserRequestDto userRequestDTO){
        UserModel userModel = UserDtoMapper.toDomain(userRequestDTO);

        return UserDtoMapper.toDto(this.userRepository.save(userModel));
    }
}
