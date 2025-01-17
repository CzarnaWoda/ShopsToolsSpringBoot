package me.blackwater.tools.web.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Schema(description = "Request to update shop name and email")
public record ShopUpdateRequest(
        @Schema(description = "Nazwa sklepu do zaaktualizowania")
        @Size(min = 6, max = 50)
        String shopName,
        @Schema(description = "Email sklepu do zaaktualizowania")
        @Email
        String email
) {
}
