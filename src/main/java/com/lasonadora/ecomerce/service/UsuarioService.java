package com.lasonadora.ecomerce.service;

import com.lasonadora.ecomerce.model.Usuario;
import com.lasonadora.ecomerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }
}
