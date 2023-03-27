package ru.pashabezborod.vigenereCoderServer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.StandardException;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.StandardResponse;
import ru.pashabezborod.vigenereCoderServer.model.User.UserRequest;
import ru.pashabezborod.vigenereCoderServer.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Get or release token")
public class AuthController {
    private final AuthService authService;

    @GetMapping()
    @Operation(summary = "Get token by username and password")
    public StandardResponse login(@RequestParam String name, @RequestParam String password, HttpServletRequest request) {
        return authService.login(new UserRequest(name, password), request.getRemoteAddr());
    }

    @DeleteMapping()
    @Operation(summary = "Release token")
    public StandardResponse logout(@RequestParam String cookie, HttpServletRequest request) {
        return authService.logout(cookie, request.getRemoteAddr());
    }
}
