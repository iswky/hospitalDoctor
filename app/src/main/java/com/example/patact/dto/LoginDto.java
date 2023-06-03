package com.example.patact.dto;

public class LoginDto {
    public Integer docDepId;
    public String password;

    public LoginDto(Integer docDepId, String password) {
        this.docDepId = docDepId;
        this.password = password;
    }
}
