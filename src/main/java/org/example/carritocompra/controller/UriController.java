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

    @GetMapping("/productos")
    public String getUriProductos(Model model) {
        Producto producto = new Producto();

        model.addAttribute("productos", productController.getProducts());
        return "productos";
    }
}
