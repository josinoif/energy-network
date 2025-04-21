package com.sinapsis.backend.infrastructure;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sinapsis.backend.domain.entity.Usuario;
import com.sinapsis.backend.domain.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario admin = Usuario.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .build();
            usuarioRepository.save(admin);
            System.out.println("Usuário admin criado com sucesso!");
        } else {
            System.out.println("Usuário admin já existe no banco de dados.");
        }
    }
}
