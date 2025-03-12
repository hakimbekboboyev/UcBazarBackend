package ru.moscow.ucbazar.restController.systemController

import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
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

    /*@Operation(summary = "isCheckIp", description = "To check Ip server")
    @GetMapping("/client-ip")
    fun getClientIp(request: HttpServletRequest): String {
        var ipAddress = request.getHeader("X-Forwarded-For") // Proxy yoki Load Balancer orqali kirsa
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equals(ipAddress, ignoreCase = true)) {
            ipAddress = request.remoteAddr // To‘g‘ridan-to‘g‘ri kirgan bo‘lsa
        }
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0]; // Birinchi IP haqiqiy mijozniki
        }

        return "Client IP: $ipAddress"
    }*/
}