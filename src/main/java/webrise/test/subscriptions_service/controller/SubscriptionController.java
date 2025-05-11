package webrise.test.subscriptions_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import webrise.test.subscriptions_service.dto.*;
import webrise.test.subscriptions_service.service.SubscriptionService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Subscriptions", description = "Операции с подписками пользователей")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Operation(
            summary = "Получить подписки пользователя",
            description = "Возвращает список подписок по ID пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный ответ",
                            content = @Content(schema = @Schema(implementation = UserSubscriptionSetResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content)
            }
    )
    @GetMapping("users/{id}/subscriptions")
    public UserSubscriptionSetResponseDto getUserSubscriptions(@PathVariable UUID id) {
        Set<UserSubscriptionResponseDto> userSubscriptionsDto = subscriptionService.getSubscriptionsByUserId(id);
        return new UserSubscriptionSetResponseDto(userSubscriptionsDto);
    }

    @Operation(
            summary = "Добавить подписку пользователю",
            description = "Добавляет подписку по ID пользователя",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные подписки",
                    required = true,
                    content = @Content(schema = @Schema(implementation = SubscriptionRequestDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Подписка добавлена",
                            content = @Content(schema = @Schema(implementation = UserSubscriptionResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content),
                    @ApiResponse(responseCode = "409", description = "Подписка уже существует", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Ошибка валидации данных", content = @Content)
            }
    )
    @PostMapping("users/{id}/subscriptions")
    public UserSubscriptionResponseDto addSubscriptionToUser(@PathVariable UUID id, @RequestBody @Valid SubscriptionRequestDto dto) {
        return subscriptionService.addSubscriptionToUser(id, dto);
    }

    @Operation(
            summary = "Удалить подписку у пользователя",
            description = "Удаляет подписку по ID пользователя и ID подписки",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Подписка удалена", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Пользователь или подписка не найдены", content = @Content)
            }
    )
    @DeleteMapping("users/{id}/subscriptions/{subId}")
    public void deleteSubscription(@PathVariable UUID id, @PathVariable(name = "subId") UUID subId) {
        subscriptionService.deleteSubscriptionFromUser(id, subId);
    }

    @Operation(
            summary = "Получить топ-3 подписки",
            description = "Возвращает список из трёх самых популярных подписок",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный ответ",
                            content = @Content(schema = @Schema(implementation = TopSubscriptionListResponseDto.class)))
            }
    )
    @GetMapping("/subscriptions/top")
    public TopSubscriptionListResponseDto getTopSubscriptions() {
        Set <TopSubscriptionResponseDto> topSubscriptionResponseDtos = subscriptionService.getTopSubscriptions();
        return new TopSubscriptionListResponseDto(topSubscriptionResponseDtos);
    }
}
