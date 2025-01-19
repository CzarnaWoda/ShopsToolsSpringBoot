package me.blackwater.tools.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.blackwater.tools.util.HttpResponse;
import me.blackwater.tools.web.requests.ToolCreateRequest;
import me.blackwater.tools.web.requests.ToolUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tool")
@Tag(name = "Tool API", description = "API do pobierania narzedzi")
class ToolController {


    @Operation(
            summary = "Pobierz narzedzie po id",
            description = "Wyswietla narzedzie po przeslanym id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Znaleziono narzedzie i wyswietlono jego dane"),
                    @ApiResponse(responseCode = "404", description = "Narzędzie nie zostało znalezione")
            }
    )

    @GetMapping("/id/{id}")
    public ResponseEntity<HttpResponse> getToolById(@Parameter(description = "Id narzedzia które zostanie pobrane") @PathVariable long id){
        return null;//TODO
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<HttpResponse> getToolByName(@Parameter @PathVariable String name){
        return null;//TODO
    }

    @GetMapping("/shop/{shopId}")
    public ResponseEntity<HttpResponse> getToolsByShopId(@Parameter @PathVariable long shopId){
        return null;//TODO
    }

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createTool(@RequestBody @Valid ToolCreateRequest toolCreateRequest){
        return null;//TODO
    }

    @PutMapping("/update")
    public ResponseEntity<HttpResponse> updateTool(@RequestBody @Valid ToolUpdateRequest toolUpdateRequest){
        return null;//TODO
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> deleteTool(@PathVariable long id){
        return null;//TODO
    }


}
