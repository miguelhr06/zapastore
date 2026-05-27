package com.lasonadora.ecomerce.controller;

import com.lasonadora.ecomerce.model.Usuario;
import com.lasonadora.ecomerce.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ➤ Mostrar login
    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    // ➤ Procesar login
    @PostMapping("/login")
    public String procesarLogin(@ModelAttribute Usuario usuario,
                                Model model,
                                HttpSession session) {

        Usuario usuarioEncontrado =
                usuarioRepository.findByCorreo(usuario.getCorreo().trim().toLowerCase());

        if (usuarioEncontrado != null &&
                usuarioEncontrado.getContrasena().equals(usuario.getContrasena())) {

            // 🔥 GUARDAR SESIÓN
            session.setAttribute("usuario", usuarioEncontrado);

            return "redirect:/perfil";
        }

        model.addAttribute("error", "Correo o contraseña incorrectos");
        return "login";
    }

    // ➤ Cerrar Sesión
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
