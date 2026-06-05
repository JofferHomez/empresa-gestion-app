package com.empresa.controller;

import com.empresa.dto.ClienteDTO;
import com.empresa.entity.Cliente;
import com.empresa.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodos() {
        return ResponseEntity.ok(clienteService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.obtenerPorId(id);
        return cliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.crear(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.actualizar(id, clienteDTO);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}