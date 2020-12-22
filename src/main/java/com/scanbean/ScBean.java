package com.scanbean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ScBean {
    @Value("123")
    private String a;
    @Autowired
    Aut aut;

    public void setA(String a) {
        this.a = a;
    }

    public void setAut(Aut aut) {
        this.aut = aut;
    }
}
