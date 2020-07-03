package com.codezero.fashiop.products.exception;

public class ProductException extends RuntimeException{

    private int code;
    private String message;

    public ProductException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
