package org.example.carritocompra.services;

import lombok.AllArgsConstructor;
import org.example.carritocompra.models.ImageProduct;
import org.example.carritocompra.models.Producto;
import org.example.carritocompra.models.User;
import org.example.carritocompra.repositories.ProductRepo;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AndroidProductService {
    private final ProductRepo productRepo;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final String hashedPassword = passwordEncoder.encode("@Admin112");
    private static final String hashedUserPassword = passwordEncoder.encode("@User112");

    private static final User admin = new User(1L, "admin", "admin@gmail.com", hashedPassword, "ADMIN");
    private static final User user = new User(2L, "user", "user@gmail.com", hashedUserPassword, "USER");
    private final ProductService productService;

    public List<ImageProduct> getAllProducts() {
        List<Producto> productos = productRepo.findAll();
        List<ImageProduct> imageProducts = new ArrayList<>();

        for (Producto producto: productos) {
            byte[] image = null;

            try {
                ClassPathResource imgFile = new ClassPathResource("images/" + producto.getId() + ".jpg");
                image = Files.readAllBytes(imgFile.getFile().toPath());
            } catch (IOException e) {
                try {
                    Path imagePath = Paths.get("target/classes/images/" + producto.getId() + ".jpg");
                    image = Files.readAllBytes(imagePath);
                } catch (IOException ex) {
                    try {
                        ClassPathResource defaultImgFile = new ClassPathResource("images/default-product.png");
                        image = Files.readAllBytes(defaultImgFile.getFile().toPath());
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }

            imageProducts.add(new ImageProduct(producto.getId(), producto.getNombre(), producto.getPrecio(), image));
        }

        return imageProducts;
    }

    public List<ImageProduct> getCartProducts() {
        List<Producto> productos = productRepo.findAllByOnChart(true);
        List<ImageProduct> imageProducts = new ArrayList<>();

        for (Producto producto: productos) {
            byte[] image = null;

            try {
                ClassPathResource imgFile = new ClassPathResource("images/" + producto.getId() + ".jpg");
                image = Files.readAllBytes(imgFile.getFile().toPath());
            } catch (IOException e) {
                try {
                    Path imagePath = Paths.get("target/classes/images/" + producto.getId() + ".jpg");
                    image = Files.readAllBytes(imagePath);
                } catch (IOException ex) {
                    try {
                        ClassPathResource defaultImgFile = new ClassPathResource("images/default-product.png");
                        image = Files.readAllBytes(defaultImgFile.getFile().toPath());
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }

            imageProducts.add(new ImageProduct(producto.getId(), producto.getNombre(), producto.getPrecio(), image));
        }

        return imageProducts;
    }

    public User login(String email, String password) {
        if (admin.getEmail().equals(email) && passwordEncoder.matches(password, hashedPassword)) {
            productRepo.setAllProductsNotInChart();
            return admin;
        }

        if (user.getEmail().equals(email) && passwordEncoder.matches(password, hashedUserPassword)) {
            productRepo.setAllProductsNotInChart();
            return user;
        }

        return null;
    }
}
