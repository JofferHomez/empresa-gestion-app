package com.empresa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Long id;
    private String codigo;
    private String nombre;
    private String caracteristicas;
    private BigDecimal precioUsd;
    private BigDecimal precioEur;
    private BigDecimal precioCop;
    private String empresaNit;
    private Set<Long> categoriaIds;
}