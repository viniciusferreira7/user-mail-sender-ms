package user_mail_sender.user.mapper;

import user_mail_sender.user.domain.UserModel;
import user_mail_sender.user.dto.UserRequestDto;
import user_mail_sender.user.dto.UserResponseDto;

public class UserDtoMapper {
    public static UserModel toDomain(UserRequestDto userRequestDto){
        return new UserModel(
                null,
                userRequestDto.name(),
                userRequestDto.email(),
                null,
                null
        );
    }
    public static UserResponseDto toDto(UserModel userModel){
        return UserResponseDto.builder()
                .id(userModel.getId())
                .name(userModel.getName())
                .email(userModel.getEmail())
                .createdAt(userModel.getCreatedAt())
                .updatedAt(userModel.getUpdatedAt())
                .build();
    }

}
