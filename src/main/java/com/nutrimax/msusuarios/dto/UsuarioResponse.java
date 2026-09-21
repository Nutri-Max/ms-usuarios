package com.nutrimax.msusuarios.dto;

public class UsuarioResponse {
    private Long id;
    private String nombres;
    private String apellidos;
    private String email;
    private String rol;

    public UsuarioResponse(Long id, String nombres, String apellidos, String email, String rol) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public String getRol() {
        return rol;
    }
}