package org.example.carritocompra.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrecioModel {

    private double minPrecio;
    private double maxPrecio;

    public PrecioModel(double minPrecio, double maxPrecio) {
        this.minPrecio = minPrecio;
        this.maxPrecio = maxPrecio;
    }
}
