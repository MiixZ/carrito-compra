package Services;

import Repositories.ProductRepo;

import java.util.List;

public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Models.Producto> getAllProducts() {
        return productRepo.findAll();
    }

    public Models.Producto getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    public void saveProduct(Models.Producto product) {
        productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }
}
