package Controllers;

import Services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/productApi")
public class ProductController {

    private ProductService productService;

    /*
    *
    * @return List of all products
    */
    @GetMapping("/products")
    public String getProducts() {
        return productService.getAllProducts().toString();
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
    public void saveProduct(@RequestBody Models.Producto product) {
        productService.saveProduct(product);
    }

    /*
    *
    * @param id
    * @return void
    */
    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProduct(@RequestParam Long id) {
        productService.deleteProduct(id);
    }
}
