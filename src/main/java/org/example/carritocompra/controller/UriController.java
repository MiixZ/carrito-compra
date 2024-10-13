package org.example.carritocompra.controller;

import lombok.AllArgsConstructor;
import org.example.carritocompra.models.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class UriController {

    private final ProductController productController;

    @GetMapping("/")
    public String getUriHome() {
        return "productos";
    }

    @GetMapping("/formulario")
    public String getUriFormulario() {
        return "formulario";
    }

    @GetMapping("/carrito")
    public String getUriCarrito(Model model) {
        model.addAttribute("productos", productController.getProductsOnChart());
        return "carrito";
    }

    @GetMapping("/productos")
    public String getUriProductos(Model model) {
        model.addAttribute("productos", productController.getProducts());
        return "productos";
    }

    @GetMapping("/login")
    public String getUriLogin() {
        return "login";
    }
}
