package com.empresa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String rol;
}