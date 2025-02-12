package me.blackwater.tools.web.requests;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ToolCreateRequest(
        @Size(min = 4,max = 48)
        String name,
        @Positive
        int price,
        @PositiveOrZero
        long shopId
) {
}
