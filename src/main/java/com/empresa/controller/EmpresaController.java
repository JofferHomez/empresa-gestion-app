package com.empresa.controller;

import com.empresa.dto.EmpresaDTO;
import com.empresa.entity.Empresa;
import com.empresa.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<List<Empresa>> obtenerTodas() {
        return ResponseEntity.ok(empresaService.obtenerTodas());
    }

    @GetMapping("/{nit}")
    public ResponseEntity<Empresa> obtenerPorNit(@PathVariable String nit) {
        Optional<Empresa> empresa = empresaService.obtenerPorNit(nit);
        return empresa.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Empresa> crear(@RequestBody EmpresaDTO empresaDTO) {
        Empresa empresa = empresaService.crear(empresaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(empresa);
    }

    @PutMapping("/{nit}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Empresa> actualizar(@PathVariable String nit, @RequestBody EmpresaDTO empresaDTO) {
        Empresa empresa = empresaService.actualizar(nit, empresaDTO);
        return ResponseEntity.ok(empresa);
    }

    @DeleteMapping("/{nit}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable String nit) {
        empresaService.eliminar(nit);
        return ResponseEntity.noContent().build();
    }
}