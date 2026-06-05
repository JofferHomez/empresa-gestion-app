package com.empresa.service;

import com.empresa.dto.ProductoDTO;
import com.empresa.entity.Categoria;
import com.empresa.entity.Empresa;
import com.empresa.entity.Producto;
import com.empresa.repository.CategoriaRepository;
import com.empresa.repository.EmpresaRepository;
import com.empresa.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final EmpresaRepository empresaRepository;
    private final CategoriaRepository categoriaRepository;

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public List<Producto> obtenerPorEmpresa(String empresaNit) {
        return productoRepository.findByEmpresaNit(empresaNit);
    }

    public Producto crear(ProductoDTO productoDTO) {
        Empresa empresa = empresaRepository.findById(productoDTO.getEmpresaNit())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        Producto producto = new Producto();
        producto.setCodigo(productoDTO.getCodigo());
        producto.setNombre(productoDTO.getNombre());
        producto.setCaracteristicas(productoDTO.getCaracteristicas());
        producto.setPrecioUsd(productoDTO.getPrecioUsd());
        producto.setPrecioEur(productoDTO.getPrecioEur());
        producto.setPrecioCop(productoDTO.getPrecioCop());
        producto.setEmpresa(empresa);

        if (productoDTO.getCategoriaIds() != null && !productoDTO.getCategoriaIds().isEmpty()) {
            Set<Categoria> categorias = new HashSet<>();
            for (Long categoriaId : productoDTO.getCategoriaIds()) {
                categoriaRepository.findById(categoriaId).ifPresent(categorias::add);
            }
            producto.setCategorias(categorias);
        }

        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(productoDTO.getNombre());
        producto.setCaracteristicas(productoDTO.getCaracteristicas());
        producto.setPrecioUsd(productoDTO.getPrecioUsd());
        producto.setPrecioEur(productoDTO.getPrecioEur());
        producto.setPrecioCop(productoDTO.getPrecioCop());

        if (productoDTO.getCategoriaIds() != null && !productoDTO.getCategoriaIds().isEmpty()) {
            Set<Categoria> categorias = new HashSet<>();
            for (Long categoriaId : productoDTO.getCategoriaIds()) {
                categoriaRepository.findById(categoriaId).ifPresent(categorias::add);
            }
            producto.setCategorias(categorias);
        }

        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}