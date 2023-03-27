package ru.pashabezborod.vigenereCoderServer.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.StandardResponse;
import ru.pashabezborod.vigenereCoderServer.model.User.UserRequest;
import ru.pashabezborod.vigenereCoderServer.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User controller", description = "CRUD for users")
public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Add new user")
    public StandardResponse addUser(@ParameterObject UserRequest userRequest) {
        return userService.addUser(userRequest);
    }

    @PatchMapping
    @Operation(summary = "Edit user")
    public StandardResponse editUser(@RequestParam String cookie, @RequestParam String password, HttpServletRequest request) {
        return userService.editUser(cookie, password, request.getRemoteAddr());
    }

    @DeleteMapping
    @Operation(summary = "Delete user")
    public StandardResponse deleteUser(@RequestParam String cookie, HttpServletRequest request) {
        return userService.deleteUser(cookie, request.getRemoteAddr());
    }
}
