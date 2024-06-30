package com.project.recommend.exception;

public class recommendNotFoundException extends RuntimeException{
    public recommendNotFoundException(Long id) {
        super("Could not find recommendation based off id: " + id);
    }
}



