package org.example.carritocompra.services;

import lombok.AllArgsConstructor;
import org.example.carritocompra.models.Producto;
import org.example.carritocompra.repositories.ProductRepo;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.io.FileWriter;
import java.util.List;

@Service
@AllArgsConstructor
public class ChartService {

    private ProductRepo productRepo;

    public void addProduct(Long id) {
        productRepo.findById(id).ifPresent(product -> {
            product.setOnChart(true);
            productRepo.save(product);
        });
    }

    public void removeProduct(Long id) {
        productRepo.findById(id).ifPresent(product -> {
            product.setOnChart(false);
            productRepo.save(product);
        });
    }


}
