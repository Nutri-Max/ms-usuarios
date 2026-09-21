package com.nutrimax.msusuarios.config;

import com.nutrimax.msusuarios.model.Rol;
import com.nutrimax.msusuarios.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;

    public DataInitializer(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public void run(String... args) {
        crearRolSiNoExiste("CLIENTE");
        crearRolSiNoExiste("AGENTE_SOPORTE");
        crearRolSiNoExiste("ADMINISTRADOR");
    }

    private void crearRolSiNoExiste(String nombre) {
        if (rolRepository.findByNombre(nombre).isEmpty()) {
            rolRepository.save(new Rol(nombre));
        }
    }
}