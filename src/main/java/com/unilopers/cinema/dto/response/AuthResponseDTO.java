package com.unilopers.cinema.dto.response;

public class AuthResponseDTO {
    private String token;
    private String tipo = "Bearer";
    private String username;
    private String message;

    public AuthResponseDTO(String token, String username, String message) {
        this.token = token;
        this.username = username;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}