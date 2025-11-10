package com.utn.productos.repository;

import com.utn.productos.model.Categoria;
import com.utn.productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoria(Categoria categoria);
}
