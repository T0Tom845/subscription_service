package webrise.test.subscriptions_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webrise.test.subscriptions_service.dto.UserRequestDto;
import webrise.test.subscriptions_service.dto.UserResponseDto;
import webrise.test.subscriptions_service.dto.UserResponseListDto;
import webrise.test.subscriptions_service.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Получить всех пользователей",
            responses = {
                    @ApiResponse(responseCode = "200",
                    description = "Возвращен список пользователей",
                    content = @Content(schema = @Schema(implementation = UserResponseListDto.class))),
            }
    )
    @GetMapping
    public UserResponseListDto getUsers() {
        List<UserResponseDto> userResponseDtos = userService.getAllUsers();
        return new UserResponseListDto(userResponseDtos);
    }

    @Operation(
            summary = "Получить пользователя по id",
            parameters = {
                    @Parameter(description = "UUID пользователя")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь найден",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден",content = @Content),
            }
    )
    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable UUID id) {
        return userService.getById(id);
    }

    @Operation(
            summary = "Создание пользователя",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO нового пользователя",
                    required = true,
                    useParameterTypeSchema = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Пользователь успешно создан",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "409", description = "Конфликт (имя или email пересекаются)",content = @Content),
                    @ApiResponse(responseCode = "400", description = "Ошибка валидации данных",content = @Content),
            }
    )
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(dto));
    }

    @Operation(
            summary = "Изменить пользователя",
            parameters = {
                    @Parameter(description = "UUID обновляемого пользователя")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO с измененными данными пользователя",
                    required = true,
                    useParameterTypeSchema = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь успешно обновлен",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "409", description = "Конфликт (имя или email пересекаются)",content = @Content),
                    @ApiResponse(responseCode = "400", description = "Ошибка валидации данных", content = @Content),
            }
    )
    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable  UUID id, @RequestBody @Valid UserRequestDto dto) {
        return userService.update(id, dto);
    }


    @Operation(
            summary = "Удалить пользователя по id",
            parameters = {
                    @Parameter(description = "UUID удаляемого пользователя")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Пользователь удален успешно"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.delete(id);
    }

}
