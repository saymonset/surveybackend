package com.repository.mongo;

import javax.validation.constraints.NotBlank;

/**
 * Created by simon on 6/4/2019.
 */
public class LoginUsuario {
   // @NotBlank
    private String nombreUsuario;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
