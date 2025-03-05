package ru.moscow.ucbazar.restController.systemController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IpController {

    @GetMapping("/client-ip")
    public String getClientIp(HttpServletRequest request) {
        String ipAddress = extractClientIp(request);
        return "Client IP: " + ipAddress;
    }

    private String extractClientIp(HttpServletRequest request) {
        String[] headers = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        };

        for (String header : headers) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip.split(",")[0].trim(); // Agar bir nechta IP bo‘lsa, birinchisi haqiqiy mijozniki
            }
        }

        return request.getRemoteAddr(); // Agar yuqoridagilarning hech biri bo‘lmasa
    }
}
