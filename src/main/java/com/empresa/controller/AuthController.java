package com.empresa.controller;

import com.empresa.dto.LoginDTO;
import com.empresa.dto.RegistroDTO;
import com.empresa.dto.JwtResponseDTO;
import com.empresa.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        JwtResponseDTO response = authService.login(loginDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registro(@RequestBody RegistroDTO registroDTO) {
        authService.registro(registroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente");
    }
}