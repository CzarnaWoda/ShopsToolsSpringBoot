package me.blackwater.tools.web.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Schema(description = "Request tworzący nowy sklep")
public record ShopCreateRequest(

        @Schema(description = "Nazwa sklepu od 6 do 50 znaków")
        @Size(min = 6,max = 50)
        String name,
        @Schema(description = "Email kontaktowy sklepu")
        @Email
        String email


) {
}
