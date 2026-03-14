package user_mail_sender.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user_mail_sender.user.domain.UserModel;
import user_mail_sender.user.dto.UserRequestDto;
import user_mail_sender.user.dto.UserResponseDto;
import user_mail_sender.user.mapper.UserDtoMapper;
import user_mail_sender.user.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid UserRequestDto userRequestDto) {
        UserModel userModel = UserDtoMapper.toDomain(userRequestDto);
        UserResponseDto userResponseDto = UserDtoMapper.toDto(this.userService.register(userModel));
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @GetMapping
    @Operation(summary = "List all users")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class)))
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<UserResponseDto> users = this.userService.findAll()
                .stream()
                .map(UserDtoMapper::toDto)
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<UserResponseDto> findById(@PathVariable UUID id) {
        UserResponseDto userResponseDto = UserDtoMapper.toDto(this.userService.findById(id));
        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<UserResponseDto> update(@PathVariable UUID id, @RequestBody @Valid UserRequestDto userRequestDto) {
        UserModel updated = UserDtoMapper.toDomain(userRequestDto);
        UserResponseDto userResponseDto = UserDtoMapper.toDto(this.userService.update(id, updated));
        return ResponseEntity.ok(userResponseDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
