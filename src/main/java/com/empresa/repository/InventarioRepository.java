package com.empresa.repository;

import com.empresa.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    List<Inventario> findByEmpresaNit(String empresaNit);
    Optional<Inventario> findByProductoIdAndEmpresaNit(Long productoId, String empresaNit);
}