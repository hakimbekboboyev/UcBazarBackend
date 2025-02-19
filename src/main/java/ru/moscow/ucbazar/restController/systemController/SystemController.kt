package ru.moscow.ucbazar.restController.systemController

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1")
class SystemController {

    @Operation(summary = "isRunning", description = "To check whether service is running or not")
    @GetMapping(value = ["/isRunning"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun test(): ResponseEntity<String> {
        return ResponseEntity("Service is running.", HttpStatus.OK)
    }
}