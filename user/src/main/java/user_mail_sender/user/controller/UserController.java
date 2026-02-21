package user_mail_sender.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user_mail_sender.user.domain.UserModel;
import user_mail_sender.user.dto.UserRequestDto;
import user_mail_sender.user.dto.UserResponseDto;
import user_mail_sender.user.mapper.UserDtoMapper;
import user_mail_sender.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new user",
            description = "Creates a new user with the provided information including name and email"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class)
                    )
            ),
//            @ApiResponse(
//                    responseCode = "400",
//                    description = "Invalid user data provided",
//                    content = @Content
//            ),
//            @ApiResponse(
//                    responseCode = "409",
//                    description = "User with this email already exists",
//                    content = @Content(
//                            mediaType = "application/json",
//                            schema = @Schema(implementation = ErrorResponseDto.class)
//                    )
//            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto userRequestDto){
        UserModel userModel = UserDtoMapper.toDomain(userRequestDto);

        UserResponseDto userResponseDto = UserDtoMapper.toDto(this.userService.register(userModel));

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }
}
