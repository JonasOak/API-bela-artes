package com.belaArtes.demo.model.util;

import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.util.text.AES256TextEncryptor;
import org.jasypt.util.text.TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.StandardEnvironment;

@Converter(autoApply = true)
public class JasyptCrypto implements AttributeConverter<String, String> {

    private final StringEncryptor encryptor;

    public JasyptCrypto() {
        this.encryptor = new DefaultLazyEncryptor(new StandardEnvironment());
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute != null ? encryptor.encrypt(attribute) : null;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData != null ? encryptor.decrypt(dbData) : null;
    }
}