package org.example.carritocompra.controller;

import lombok.AllArgsConstructor;
import org.example.carritocompra.models.PrecioModel;
import org.example.carritocompra.models.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@AllArgsConstructor
@RequestMapping("/web")
public class UriController {

    private final ProductController productController;

    @GetMapping("/")
    public String getUriHome(Model model) {
        return this.getUriCatalogo(model);
    }

    @GetMapping("/formulario")
    public String getUriFormulario(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null) {
            Producto producto = productController.getProductById(id);
            model.addAttribute("product", producto);
        }

        return "formulario";
    }

    @GetMapping("/carrito")
    public String getUriCarrito(Model model) {
        model.addAttribute("productos", productController.getProductsOnChart());
        return "carrito";
    }

    @GetMapping("/catalogo")
    public String getUriCatalogo(Model model) {
        model.addAttribute("productos", productController.getProducts());
        return "catalogo";
    }

    @GetMapping("/productos")
    public String getUriProductos(Model model) {
        model.addAttribute("productos", productController.getProducts());
        return "productos";
    }

    @GetMapping("/productosPrecio")
    public String getUriProductosPrecio(@RequestParam("minPrice") double minPrecio, @RequestParam("maxPrice") double maxPrecio, Model model) {
        PrecioModel precioModel = new PrecioModel(minPrecio, maxPrecio);

        model.addAttribute("productos", productController.getProductsByPrice(precioModel));
        return "catalogo";
    }

    @GetMapping("/login")
    public String getUriLogin() {
        return "login";
    }
}
