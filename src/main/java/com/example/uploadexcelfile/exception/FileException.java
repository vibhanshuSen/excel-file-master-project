package com.example.uploadexcelfile.exception;


public class FileException extends RuntimeException {

    protected int status;

    public FileException(int status, String message) {
        super(message);
        this.status = status;
    }

}
