package org.example.carritocompra.repositories;

import jakarta.transaction.Transactional;
import org.example.carritocompra.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p WHERE p.onChart = ?1")
    List<Producto> findAllByOnChart(boolean onChart);

    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN ?1 AND ?2")
    List<Producto> findByPrecioBetween(double minPrecio, double maxPrecio);

    @Modifying
    @Transactional
    @Query("UPDATE Producto p SET p.onChart = false")
    void setAllProductsNotInChart();
}
