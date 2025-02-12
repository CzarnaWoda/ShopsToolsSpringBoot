package me.blackwater.tools.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.blackwater.tools.mapper.ShopMapper;
import me.blackwater.tools.model.Shop;
import me.blackwater.tools.service.ShopService;
import me.blackwater.tools.util.HttpResponse;
import me.blackwater.tools.util.TimeUtil;
import me.blackwater.tools.web.requests.ShopCreateRequest;
import me.blackwater.tools.web.requests.ShopUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/shop")
@RequiredArgsConstructor

@Tag(name = "Shop API", description = "Endpointy do zarządzania sklepami")
public class ShopController {

    private final ShopService shopService;

    private final ShopMapper shopMapper;


    @Operation(
            summary = "Pobierz sklep po nazwie",
            description = "Pozwala pobrac sklep po jego nazwie",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sklep z podana nazwa zostal znaleziony"),
                    @ApiResponse(responseCode = "404", description = "Sklep nie został znaleziony z daną nazwą")
            }
    )
    @GetMapping("/name/{shopName}")
    public ResponseEntity<HttpResponse> getShopByName(
            @Parameter(description = "Nazwa sklepu" , example = "Sklep z narzędziami")
            @PathVariable("shopName")
            String shopName
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

    @Operation(
            summary = "Pobierz sklep po id",
            description = "Pozwala pobrac sklep po jego id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sklep z podanym id zostal znaleziony"),
                    @ApiResponse(responseCode = "404", description = "Sklep nie został znaleziony z podanym id")
            }
    )
    @GetMapping("/id/{id}")
    public ResponseEntity<HttpResponse> getShopById(
            @Parameter(description = "Id sklepu", example = "1")
            @PathVariable("id")
            long id
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

    @Operation(
            summary = "Pobiera liste sklepów",
            description = "Pobiera liste sklepów uwzgledniąjac wielkość strony, ilość sklepów na strone oraz sortowanie",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Wyslano liste sklepow. Lista moze byc pusta!"),
                    @ApiResponse(responseCode = "400", description = "Argument pageable jest nieprawidłowy (np. size = 0)")
            }
    )
    @GetMapping("/shops")
    public ResponseEntity<HttpResponse> getShops(

            @Parameter(description = "Numer strony", example = "0")
            @RequestParam(defaultValue = "0")
            int page,

            @Parameter(description = "Rozmiar strony", example = "10")
            @RequestParam(defaultValue = "10")
            int size,

            @Parameter(description = "Pole po którym request ma byc sortowany", example = "id")
            @RequestParam(defaultValue = "id")
            String sortBy,

            @Parameter(description = "Typ sortowania", example = "asc")
            @RequestParam(defaultValue = "asc")
            String sortDir
    ){
        final Page<Shop> shops = shopService.getAllShops(page, size, sortBy, sortDir);

        return ResponseEntity.status(OK)
                .body(HttpResponse.builder()
                                .reason("Shops request")
                                .message("Pageable shops")
                                .timeStamp(TimeUtil.getCurrentTimeStamp())
                                .status(OK)
                                .statusCode(OK.value())
                                .data(Map.of("shops", shops.stream().map(shopMapper::toDto)))
                                .build()
                );
    }
    @Operation(
            summary = "Tworzy nowy sklep",
            description = "Tworzny nowy sklep przesłany w requescie",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Sklep zostal utworzony!"),
                    @ApiResponse(responseCode = "400", description = "Sklep o danych parametrach juz istnieje!")
            }
    )
    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createShop(
            @Parameter(description = "Request, który zawiera informacje na temat tworzonego sklepu")
            @Valid @RequestBody
            ShopCreateRequest shopCreateRequest
    ){
        final Shop shop = shopService.createShop(shopCreateRequest);

        return ResponseEntity.status(CREATED)
                .body(HttpResponse.builder()
                                .reason("Shop create request")
                                .message("Shop has been created")
                                .timeStamp(TimeUtil.getCurrentTimeStamp())
                                .status(CREATED)
                                .statusCode(CREATED.value())
                                .data(Map.of("shop", shopMapper.toDto(shop))).build()
                );
    }
    @Operation(
            summary = "Aktualizuje dane sklepu",
            description = "Aktualizuje sklep zgodnie z danymi w requescie",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sklep zostal zaktualizowany"),
                    @ApiResponse(responseCode = "400", description = "Sklep z taka nazwa juz istnieje"),
                    @ApiResponse(responseCode = "404", description = "Sklep z podanym id nie istnieje")
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpResponse> updateShop(
            @Parameter(description = "Id sklepu ktory zostanie zaktualizowany") @PathVariable long id,
            @Valid @Parameter(description = "Request, który ma nowe dane dla sklepu") @RequestBody ShopUpdateRequest shopUpdateRequest
    ){
        final Shop oldShop = shopService.getShopById(id);

        final int updated = shopService.updateShop(oldShop,shopUpdateRequest);

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                .timeStamp(TimeUtil.getCurrentTimeStamp())
                .reason("Update shop request")
                .message("Shop has been updated")
                .statusCode(OK.value())
                .status(OK)
                .data(Map.of("updated",updated)).build()
        );
    }
    @Operation(
            summary = "Usuwa sklep z systemu",
            description = "Usuwa sklep pod id w systemie",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sklep został pomyślnie usunięty"),
                    @ApiResponse(responseCode = "404", description = "Sklep nie został znaleziony")
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> deleteShop(@Parameter(description = "Id sklepu, który zostanie usunięty") @PathVariable long id){

        shopService.deleteShop(id);

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                .timeStamp(TimeUtil.getCurrentTimeStamp())
                .reason("Delete shop request")
                .message("Shop has been removed")
                .statusCode(OK.value())
                .status(OK).build());
    }

}
