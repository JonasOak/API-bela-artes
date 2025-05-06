package com.belaArtes.demo.controller.resources;

import com.belaArtes.demo.controller.resources.exceptions.ClientException;
import com.belaArtes.demo.controller.services.ClienteService;
import com.belaArtes.demo.model.entities.Cliente;
import com.belaArtes.demo.util.MailUtil;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/recover")
@RestController
public class MailResource extends MailUtil {
    @Autowired
    private ClienteService serviceClient;

    @PostMapping
    public ResponseEntity<?> recoverPassword(@PathParam("email") String email) {
        try{
            Cliente searchClientMail = serviceClient.findByEmail(email);
            if (searchClientMail != null) {
                sendRecoverPassword(email, searchClientMail);
                return ResponseEntity.ok().build();
            }
        } catch (ClientException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return ResponseEntity.notFound().build();
    }
}
