package com.johndobie.springboot.testing.cheatsheet.controller.swagger;

import com.johndobie.springboot.testing.cheatsheet.model.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Echo Microservice", description = "This Microservice will echo the model passed in the response.")
public interface EchoControllerSwagger {
    
    @Operation(operationId = "EchoController", description = "This method will echo its request body", summary = "This method will echo its request body")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class)))
    public Message echo(@Valid @RequestBody Message message);
}
