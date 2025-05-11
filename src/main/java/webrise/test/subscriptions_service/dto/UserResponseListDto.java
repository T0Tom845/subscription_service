package webrise.test.subscriptions_service.dto;

import java.util.List;

public record UserResponseListDto (
        List<UserResponseDto> userResponseDtos
){
}
