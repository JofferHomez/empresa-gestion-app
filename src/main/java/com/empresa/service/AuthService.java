package com.empresa.service;

import com.empresa.dto.LoginDTO;
import com.empresa.dto.RegistroDTO;
import com.empresa.dto.JwtResponseDTO;
import com.empresa.entity.Usuario;
import com.empresa.repository.UsuarioRepository;
import com.empresa.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public JwtResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        String token = jwtProvider.generateToken(authentication);
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new JwtResponseDTO(token, "Bearer", usuario.getId(), usuario.getEmail(), usuario.getRol().name());
    }

    public void registro(RegistroDTO registroDTO) {
        if (usuarioRepository.existsByEmail(registroDTO.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(registroDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
        usuario.setRol(Usuario.Role.valueOf(registroDTO.getRol().toUpperCase()));
        usuario.setActivo(true);

        usuarioRepository.save(usuario);
    }
}