package com.nutrimax.msusuarios.service;

import com.nutrimax.msusuarios.model.Rol;
import com.nutrimax.msusuarios.model.Usuario;
import com.nutrimax.msusuarios.repository.RolRepository;
import com.nutrimax.msusuarios.repository.UsuarioRepository;
import com.nutrimax.msusuarios.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public Usuario registrar(String nombres, String apellidos, String email, String contrasenaPlano) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("El email ya está registrado");
        }

        Rol rolCliente = rolRepository.findByNombre("CLIENTE")
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no existe, créalo primero"));

        Usuario usuario = new Usuario();
        usuario.setNombres(nombres);
        usuario.setApellidos(apellidos);
        usuario.setEmail(email);
        usuario.setContrasena(passwordEncoder.encode(contrasenaPlano)); // bcrypt
        usuario.setRol(rolCliente);

        return usuarioRepository.save(usuario);
    }

    public String login(String email, String contrasenaPlano) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!passwordEncoder.matches(contrasenaPlano, usuario.getContrasena())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().getNombre());
    }
}