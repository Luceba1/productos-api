package com.utn.productos.exception;

public class ProductoNotFoundException extends RuntimeException {

    public ProductoNotFoundException(String message) {
        super(message);
    }
}
