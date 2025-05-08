package webrise.test.subscriptions_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webrise.test.subscriptions_service.dto.UserRequestDto;
import webrise.test.subscriptions_service.dto.UserResponseDto;
import webrise.test.subscriptions_service.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable UUID id) {
        return userService.getById(id);
    }
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(dto));
    }
    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable UUID id, @RequestBody @Valid UserRequestDto dto){
        return userService.update(id, dto);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.delete(id);
    }

}
