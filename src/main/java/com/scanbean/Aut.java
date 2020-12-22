package com.scanbean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Aut {
    @Value("dsad")
    private String cc;

    public void setCc(String cc) {
        this.cc = cc;
    }
}
