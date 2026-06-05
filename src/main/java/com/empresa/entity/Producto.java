package com.empresa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String caracteristicas;

    @Column(nullable = false)
    private BigDecimal precioUsd;

    @Column(nullable = false)
    private BigDecimal precioEur;

    @Column(nullable = false)
    private BigDecimal precioCop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_nit", nullable = false)
    private Empresa empresa;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "producto_categoria",
        joinColumns = @JoinColumn(name = "producto_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias = new HashSet<>();

    @ManyToMany(mappedBy = "productos", fetch = FetchType.LAZY)
    private Set<Orden> ordenes = new HashSet<>();
}