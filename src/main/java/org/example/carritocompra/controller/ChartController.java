package org.example.carritocompra.controller;

import lombok.AllArgsConstructor;
import org.example.carritocompra.services.ChartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@AllArgsConstructor
@RequestMapping("/chartApi")
public class ChartController {

    private ChartService chartService;
    private final String REDIRECT_PRODUCTS = "redirect:/web/carrito";

    @PostMapping("/addProduct/{id}")
    public String addProduct(@PathVariable Long id) {
        chartService.addProduct(id);

        return this.REDIRECT_PRODUCTS;
    }

    @PostMapping("/removeProduct/{id}")
    public String removeProduct(@PathVariable Long id) {
        chartService.removeProduct(id);

        return this.REDIRECT_PRODUCTS;
    }

    @PostMapping("/generateOrder")
    public void generateOrder(HttpServletResponse response) {
        chartService.generateOrder(response);
    }
}
