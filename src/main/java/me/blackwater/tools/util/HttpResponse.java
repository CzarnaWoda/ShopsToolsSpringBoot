package me.blackwater.tools.util;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;

@SuperBuilder
@Data
@Schema(description = "Standard response from endpoints to normalize all requests")
public class HttpResponse {

    @Schema(description = "Aktualny czas odpowiedzi")
    protected String timeStamp;
    @Schema(description = "Status odpowiedz")
    protected HttpStatus status;
    @Schema(description = "Kod statusu odpowiedzi")
    protected int statusCode;
    @Schema(description = "Powod odpowiedz")
    protected String reason;
    @Schema(description = "Wiadomosc odpowiedzi")
    protected String message;
    @Schema(description = "Dane wyslane w opowiedzi")
    private Map<?,?> data;

}
