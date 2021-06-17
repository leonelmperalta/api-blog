package com.leonelmperalta.api.blog.service.util;

import lombok.Data;

@Data
public class JwtDTO {
    private String jwt;

    public JwtDTO(String jwt) {
        this.jwt = jwt;
    }
}
