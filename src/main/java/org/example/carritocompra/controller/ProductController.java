package org.example.carritocompra.controller;


import lombok.AllArgsConstructor;
import org.example.carritocompra.models.Producto;
import org.example.carritocompra.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/productApi")
public class ProductController {

    private ProductService productService;
    private final String REDIRECT_PRODUCTS = "redirect:/productos";

    /*
    *
    * @return List of all products
    */
    @GetMapping("/products")
    public List<Producto> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/productsOnChart")
    public List<Producto> getProductsOnChart() {
        return productService.getAllProductsOnChart();
    }

    /*
    *
    * @param id
    * @return Product by id
    */
    @GetMapping("/product")
    public String getProductById(@RequestParam Long id) {
        return productService.getProductById(id).toString();
    }

    /*
    *
    * @param product
    * @return Product saved
    */
    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute Producto product) {
        product.setOnChart(false);
        productService.saveProduct(product);

        return this.REDIRECT_PRODUCTS;
    }

    /*
    *
    * @param id
    * @return void
    */
    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        return this.REDIRECT_PRODUCTS;
    }
}