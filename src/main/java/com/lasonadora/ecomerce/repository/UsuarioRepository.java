package com.lasonadora.ecomerce.repository;

import com.lasonadora.ecomerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByCorreo(String correo);
}
