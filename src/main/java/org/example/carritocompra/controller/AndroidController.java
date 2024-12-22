package org.example.carritocompra.controller;

import lombok.AllArgsConstructor;
import org.example.carritocompra.models.ImageProduct;
import org.example.carritocompra.models.PrecioModel;
import org.example.carritocompra.models.Producto;
import org.example.carritocompra.models.User;
import org.example.carritocompra.services.AndroidProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
public class AndroidController {

    private final ChartController chartController;
    private final AndroidProductService AndroidProductService;

    @GetMapping("/product/getAllProducts")
    public List<ImageProduct> getAllProducts() {
        return this.AndroidProductService.getAllProducts();
    }

    @GetMapping("/cart/getCartProducts")
    public List<ImageProduct> getCartProducts(@RequestParam String email) {
        if (email == null || email.isEmpty()) {
            return Collections.emptyList();
        }

        return this.AndroidProductService.getCartProducts();
    }

    @GetMapping("/user/login")
    public User login(@RequestParam String email, @RequestParam String password) {
        return this.AndroidProductService.login(email, password);
    }

    @GetMapping("/cart/removeProductFromCart")
    public void removeProductFromCart(@RequestParam String email, @RequestParam Long productId) {
        this.chartController.removeProduct(productId);
    }

    @GetMapping("/cart/addProductToCart")
    public void addProductToCart(@RequestParam String email, @RequestParam Long productId) {
        this.chartController.addProduct(productId);
    }
}
