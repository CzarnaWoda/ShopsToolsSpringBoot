package me.blackwater.tools.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.blackwater.tools.mapper.ToolMapper;
import me.blackwater.tools.model.Tool;
import me.blackwater.tools.service.ToolService;
import me.blackwater.tools.util.HttpResponse;
import me.blackwater.tools.util.TimeUtil;
import me.blackwater.tools.web.requests.ToolCreateRequest;
import me.blackwater.tools.web.requests.ToolUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tool")
@Tag(name = "Tool API", description = "API do pobierania narzedzi")
class ToolController {

    private final ToolService toolService;

    private final ToolMapper toolMapper;
    @Operation(
            summary = "Pobierz narzedzie po id",
            description = "Pobiera narzedzie po jego id oraz wyswietla jego szczegoly",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Znaleziono narzedzie i wyswietlono jego dane"),
                    @ApiResponse(responseCode = "404", description = "Narzędzie nie zostało znalezione")
            }
    )

    @GetMapping("/id/{id}")
    public ResponseEntity<HttpResponse> getToolById(@Parameter(description = "Id narzedzia które zostanie pobrane") @PathVariable long id){

        final Tool tool = toolService.getToolById(id);

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                .timeStamp(TimeUtil.getCurrentTimeStamp())
                .message("Tool has been found")
                .reason("Tool request by id sent")
                .statusCode(OK.value())
                .status(OK)
                .data(Map.of("tool", toolMapper.toDto(tool))).build()
        );
    }
    @Operation(
            summary = "Pobierz narzedzie po nazwie",
            description = "Pobiera narzedzie po jego nazwie oraz wyswietla jego szczegoly",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Znaleziono narzedzie i wyswietlono jego dane"),
                    @ApiResponse(responseCode = "404", description = "Narzędzie nie zostalo znalezione")
            }
    )
    @GetMapping("/name/{name}")
    public ResponseEntity<HttpResponse> getToolByName(@Parameter(description = "Nazwa narzedzia ktore zostanie pobrane") @PathVariable String name) {
        final Tool tool = toolService.getToolByName(name);

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                .timeStamp(TimeUtil.getCurrentTimeStamp())
                .message("Tool has been found")
                .reason("Tool request by name sent")
                .statusCode(OK.value())
                .status(OK)
                .data(Map.of("tool", toolMapper.toDto(tool))).build()
        );
    }

    @Operation(
            summary = "Pobiera narzędzia po id sklepu",
            description = "Pobiera narzedzia po ich id sklepu oraz wyswietla szczegoly",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Wyświetla narzędzia po ich id sklepu")
            }
    )
    @GetMapping("/shop/{shopId}")
    public ResponseEntity<HttpResponse> getToolsByShopId(@Parameter(description = "Id sklepu po ktorym zostana pobrane narzędzia") @PathVariable long shopId){
        return ResponseEntity.status(OK).body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeStamp())
                        .message("Tools by ShopId")
                        .reason("Tools by ShopId request")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(Map.of("tools", toolService.getToolsByShopId(shopId).stream().map(toolMapper::toDto).toList()))
                .build());

    }

    @Operation(
            summary = "Tworzny nowe narzedzie",
            description = "Tworzy nowe narzedzie z parametrami przeslanymi w toolCreateRequest",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Narzędzie zostało poprawnie utworzone"),
                    @ApiResponse(responseCode = "400", description = "Narzędzie o podanych parametrach już istnieje"),
                    @ApiResponse(responseCode = "404", description = "Sklep o podanym id nie istnieje")
            }
    )
    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createTool(
            @Parameter(description = "Parametry nowego narzędzia które zostanie utworzone")
            @RequestBody @Valid ToolCreateRequest toolCreateRequest
    ){
        final Tool tool = toolService.createTool(toolCreateRequest);

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeStamp())
                        .message("Tool has been created")
                        .reason("Create tool request")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(Map.of("tool", toolMapper.toDto(tool)))
                .build());
    }

    @Operation(
            summary = "Aktualizuje narzedzie o podanym id",
            description = "Aktualizuje istniejące narzedzie po id podanym w requescie",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Narzedzie zostało poprawnie zaaktualizowane"),
                    @ApiResponse(responseCode = "400", description = "Narzędzie z taką nazwą już istnieje"),
                    @ApiResponse(responseCode = "404", description = "Narzędzie z podanym id nie istnieje")
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpResponse> updateTool(
            @Parameter(description = "Id narzędzia które zostanie zaaktualizowane") @PathVariable long id,
            @Parameter(description = "Parametry narzędzia które zostaną zaaktualizowane") @RequestBody @Valid ToolUpdateRequest toolUpdateRequest
    ){
        final Tool tool = toolService.getToolById(id);

        final int updated = toolService.updateTool(tool,toolUpdateRequest);

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                    .timeStamp(TimeUtil.getCurrentTimeStamp())
                    .status(OK)
                    .statusCode(OK.value())
                    .message("Tool has been updated")
                    .reason("Tool update request")
                    .data(Map.of("updated", updated))
                .build());
    }
    @Operation(
            summary = "Usuwa narzedzie po jego id",
            description = "Usuwa istniejace narzedzie po podanym id w requescie",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Narzędzie zostało poprawnie usunięte"),
                    @ApiResponse(responseCode = "404", description = "Narzędzie o podanym id nie zostało znalezione")
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> deleteTool(
            @Parameter(description = "Id narzędzia do usunięcia") @PathVariable long id
    ){
        final Tool tool = toolService.deleteTool(id);

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeStamp())
                        .statusCode(OK.value())
                        .status(OK)
                        .message("Tool has been deleted")
                        .reason("Tool delete request")
                        .data(Map.of("tool", toolMapper.toDto(tool)))
                .build());
    }


}
