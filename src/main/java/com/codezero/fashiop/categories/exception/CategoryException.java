package com.codezero.fashiop.categories.exception;

public class CategoryException extends RuntimeException{

    private int code;
    private String message;

    public CategoryException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
