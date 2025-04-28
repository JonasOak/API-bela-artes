package com.belaArtes.demo.util;

import com.belaArtes.demo.model.entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailUtil {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRecoverPassword(String email, Cliente recoverClient) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(emailConfiguration.title);
        message.setText(emailConfiguration.descriptionPassword + emailConfiguration.descriptionRecoverPassword);
        try {
            mailSender.send(message);
        } catch (MailSendException e) {
            e.getMessage();
        }
    }


}

class emailConfiguration{
    public static String  title = "Recuperação de email"; ;
    public static String descriptionPassword = "Sua senha foi recuperada com sucesso! ";
    public static String descriptionRecoverPassword = "Sua senha é. ";


}