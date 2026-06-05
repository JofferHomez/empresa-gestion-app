package com.empresa.controller;

import com.empresa.dto.ProductoDTO;
import com.empresa.entity.Producto;
import com.empresa.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoService.obtenerPorId(id);
        return producto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/empresa/{empresaNit}")
    public ResponseEntity<List<Producto>> obtenerPorEmpresa(@PathVariable String empresaNit) {
        return ResponseEntity.ok(productoService.obtenerPorEmpresa(empresaNit));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Producto> crear(@RequestBody ProductoDTO productoDTO) {
        Producto producto = productoService.crear(productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        Producto producto = productoService.actualizar(id, productoDTO);
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}