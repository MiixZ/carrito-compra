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

    /*
    *
    * @return List of all products
    */
    @GetMapping("/products")
    public List<Producto> getProducts() {
        return productService.getAllProducts();
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
    public void saveProduct(Producto product) {
        productService.saveProduct(product);
    }

    /*
    *
    * @param id
    * @return void
    */
    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        return "redirect:/productos";
    }
}
