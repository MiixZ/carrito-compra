package org.example.carritocompra.models;
import lombok.Data;

@Data
public class ImageProduct {
    private Long id;
    private String name;
    private double price;
    private byte[] image;

    public ImageProduct(Long id, String name, double price, byte[] image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }
}