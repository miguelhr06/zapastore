package com.lasonadora.ecomerce.controller;

import com.lasonadora.ecomerce.model.Usuario;
import com.lasonadora.ecomerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 👇 Muestra el formulario de registro (GET)
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro"; // plantilla registro.html
    }

    // 👇 Procesa el registro (POST)
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        try {
            usuarioRepository.save(usuario);
            return "redirect:/login"; // ✅ redirige al login
        } catch (Exception e) {
            System.out.println("⚠️ Error al registrar usuario: " + e.getMessage());
            return "redirect:/registro?error=correo_existente";
        }
    }
}
