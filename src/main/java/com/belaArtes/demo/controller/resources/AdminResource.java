package com.belaArtes.demo.controller.resources;

import com.belaArtes.demo.controller.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminResource {
    @Autowired
    private AdminService service;
    @GetMapping
    public ResponseEntity<?> countService() {
        return new ResponseEntity<>(service.countService(), HttpStatus.OK);
    }
}
