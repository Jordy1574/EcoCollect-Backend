package com.upn.ecocollect.dto;

import com.upn.ecocollect.model.Usuario;

public class AuthResponse {
    private Usuario user;
    private String token;
    private String refreshToken;

    public AuthResponse() {}

    public AuthResponse(Usuario user, String token, String refreshToken) {
        this.user = user;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
