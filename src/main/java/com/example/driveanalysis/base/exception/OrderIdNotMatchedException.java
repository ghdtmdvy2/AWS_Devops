package com.example.driveanalysis.base.exception;

public class OrderIdNotMatchedException extends RuntimeException {
    public OrderIdNotMatchedException(String message){
        super(message);
    }
}
