package com.empresa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaDTO {
    private String nit;
    private String nombre;
    private String direccion;
    private String telefono;
}