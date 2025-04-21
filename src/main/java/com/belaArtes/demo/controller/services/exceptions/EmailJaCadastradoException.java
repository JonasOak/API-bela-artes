package com.belaArtes.demo.controller.services.exceptions;

public class EmailJaCadastradoException extends RuntimeException{
    public EmailJaCadastradoException(String email) {
        super("E-mail já cadastrado.");
    }
}
