package br.com.sensedia.controller;

import br.com.sensedia.service.notification.SQSService;
import com.amazonaws.services.sqs.model.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "consumer")
@Tag(name = "AWS SQS Consumer", description = "Endpoint for consuming messages")
public class SQSConsumerController {

    @Operation(summary = "Get messages", description = "Get message from SQS AWS", tags = { "SQS" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message Received"),
    })
    @GetMapping(produces = { "application/json"})
    public ResponseEntity<?> consumeMessage(){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
