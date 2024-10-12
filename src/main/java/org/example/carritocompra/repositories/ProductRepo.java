package org.example.carritocompra.repositories;

import org.example.carritocompra.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Producto, Long> {
}
