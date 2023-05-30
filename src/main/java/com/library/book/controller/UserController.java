package com.library.book.controller;

import com.library.book.model.dto.request.LoginRequest;
import com.library.book.model.dto.request.UserRequest;
import com.library.book.model.dto.response.JSONResponse;
import com.library.book.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRequest request) {
        JSONResponse response = userService.saveUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("login")
    public ResponseEntity<JSONResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

}
