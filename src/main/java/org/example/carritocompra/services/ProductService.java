package org.example.carritocompra.services;

import lombok.AllArgsConstructor;
import org.example.carritocompra.models.PrecioModel;
import org.example.carritocompra.repositories.ProductRepo;
import org.example.carritocompra.models.Producto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    public List<Producto> getAllProducts() {
        return productRepo.findAll();
    }

    public List<Producto> getAllProductsOnChart() {
        return productRepo.findAllByOnChart(true);
    }

    public Producto getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    public void saveProduct(Producto product) {
        productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

    public List<Producto> getProductsByPrice(PrecioModel precioModel) {
        return productRepo.findByPrecioBetween(precioModel.getMinPrecio(), precioModel.getMaxPrecio());
    }
}
