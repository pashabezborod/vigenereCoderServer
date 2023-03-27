package ru.pashabezborod.vigenereCoderServer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import ru.pashabezborod.vigenereCoderServer.model.Password.PasswordRq;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.StandardResponse;
import ru.pashabezborod.vigenereCoderServer.service.PasswordService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/password")
@Tag(name = "Password controller", description = "CRUD for passwords")
public class PasswordController {
    private final PasswordService passwordService;

    @GetMapping
    @Operation(summary = "Get password")
    public StandardResponse get(@RequestParam String cookie, @RequestParam String passwordName, HttpServletRequest request) {
        return passwordService.getPassword(cookie, passwordName, request.getRemoteAddr());
    }

    @GetMapping("/all")
    @Operation(summary = "Get all passwords")
    public StandardResponse getAll(String cookie, HttpServletRequest request) {
        return passwordService.getAllPasswords(cookie, request.getRemoteAddr());
    }

    @PutMapping()
    @Operation(summary = "Add new password")
    public StandardResponse savePassword(@ParameterObject PasswordRq rq, HttpServletRequest request) {
        return passwordService.savePassword(rq, request.getRemoteAddr());
    }

    @PatchMapping
    @Operation(summary = "Edit exists password")
    public StandardResponse edit(@ParameterObject PasswordRq rq, HttpServletRequest request) {
        return passwordService.editPassword(rq, request.getRemoteAddr());
    }

    @DeleteMapping
    @Operation(summary = "Delete password")
    public StandardResponse delete(@ParameterObject PasswordRq rq, HttpServletRequest request) {
        return passwordService.deletePassword(rq, request.getRemoteAddr());
    }
}
