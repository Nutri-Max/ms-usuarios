package com.nutrimax.msusuarios.controller;

import com.nutrimax.msusuarios.dto.*;
import com.nutrimax.msusuarios.model.Usuario;
import com.nutrimax.msusuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody RegistroRequest request) {
        try {
            Usuario usuario = usuarioService.registrar(
                    request.getNombres(),
                    request.getApellidos(),
                    request.getEmail(),
                    request.getContrasena());

            UsuarioResponse response = new UsuarioResponse(
                    usuario.getId(),
                    usuario.getNombres(),
                    usuario.getApellidos(),
                    usuario.getEmail(),
                    usuario.getRol().getNombre());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = usuarioService.login(request.getEmail(), request.getContrasena());
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}