package com.empresa.service;

import com.empresa.dto.EmpresaDTO;
import com.empresa.entity.Empresa;
import com.empresa.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public List<Empresa> obtenerTodas() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> obtenerPorNit(String nit) {
        return empresaRepository.findById(nit);
    }

    public Empresa crear(EmpresaDTO empresaDTO) {
        Empresa empresa = new Empresa();
        empresa.setNit(empresaDTO.getNit());
        empresa.setNombre(empresaDTO.getNombre());
        empresa.setDireccion(empresaDTO.getDireccion());
        empresa.setTelefono(empresaDTO.getTelefono());
        return empresaRepository.save(empresa);
    }

    public Empresa actualizar(String nit, EmpresaDTO empresaDTO) {
        Empresa empresa = empresaRepository.findById(nit)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        empresa.setNombre(empresaDTO.getNombre());
        empresa.setDireccion(empresaDTO.getDireccion());
        empresa.setTelefono(empresaDTO.getTelefono());
        return empresaRepository.save(empresa);
    }

    public void eliminar(String nit) {
        empresaRepository.deleteById(nit);
    }
}