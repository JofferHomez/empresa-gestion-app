package com.empresa.service;

import com.empresa.dto.ClienteDTO;
import com.empresa.entity.Cliente;
import com.empresa.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente crear(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setDireccion(clienteDTO.getDireccion());
        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setNombre(clienteDTO.getNombre());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setDireccion(clienteDTO.getDireccion());
        return clienteRepository.save(cliente);
    }

    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}