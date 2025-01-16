package me.blackwater.tools.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.blackwater.tools.mapper.ShopMapper;
import me.blackwater.tools.model.Shop;
import me.blackwater.tools.service.ShopService;
import me.blackwater.tools.util.HttpResponse;
import me.blackwater.tools.util.TimeUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/shop")
@RequiredArgsConstructor

@Tag(name = "Shop API", description = "Endpointy do zarządzania sklepami")
class ShopController {

    private final ShopService shopService;

    private final ShopMapper shopMapper;

    @GetMapping("/name/{shopName}")
    @Operation(
            summary = "Pobierz sklep po nazwie",
            description = "Pozwala pobrac sklep po jego nazwie",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sklep z podana nazwa zostal znaleziony"),
                    @ApiResponse(responseCode = "404", description = "Sklep nie został znaleziony z daną nazwą")
            }
    )
    public ResponseEntity<HttpResponse> getShopByName(
            @Parameter(description = "Nazwa sklepu" , example = "Sklep z narzędziami") @PathVariable("shopName") String shopName
    ) {
        final Shop shop = shopService.getShopByName(shopName);

        return ResponseEntity.status(OK)
                .body(HttpResponse.builder()
                        .reason("Shop by name request")
                        .message("Shop by name")
                        .timeStamp(TimeUtil.getCurrentTimeStamp())
                        .statusCode(OK.value())
                        .status(OK)
                        .data(Map.of("shop", shopMapper.toDto(shop)))
                        .build());
    }

    @GetMapping("/id/{id}")
    @Operation(
            summary = "Pobierz sklep po id",
            description = "Pozwala pobrac sklep po jego id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sklep z podanym id zostal znaleziony"),
                    @ApiResponse(responseCode = "404", description = "Sklep nie został znaleziony z podanym id")
            }
    )
    public ResponseEntity<HttpResponse> getShopById(
            @Parameter(description = "Id sklepu", example = "1") @PathVariable("id") long id
    ) {
        final Shop shop = shopService.getShopById(id);

        return ResponseEntity.status(OK)
                .body(HttpResponse.builder()
                        .reason("Shop by id request")
                        .message("Shop by id")
                        .timeStamp(TimeUtil.getCurrentTimeStamp())
                        .statusCode(OK.value())
                        .status(OK)
                        .data(Map.of("shop", shopMapper.toDto(shop)))
                        .build());
    }

    @GetMapping("/shops")
    @Operation(
            summary = "Pobiera liste sklepów",
            description = "Pobiera liste sklepów uwzgledniąjac wielkość strony, ilość sklepów na strone oraz sortowanie"
    )
    public ResponseEntity<HttpResponse> getShops(
            @Parameter(description = "Numer strony", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Rozmiar strony", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Pole po którym request ma byc sortowany", example = "id") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Typ sortowania", example = "asc") @RequestParam(defaultValue = "asc") String sortDir
    ){
        final Page<Shop> shops = shopService.getAllShops(page, size, sortBy, sortDir);

        return ResponseEntity.status(OK)
                .body(
                        HttpResponse.builder()
                                .reason("Shops request")
                                .message("Pageable shops")
                                .timeStamp(TimeUtil.getCurrentTimeStamp())
                                .status(OK)
                                .statusCode(OK.value())
                                .data(Map.of("shops", shops.stream().map(shopMapper::toDto)))
                                .build()
                );
    }

}
