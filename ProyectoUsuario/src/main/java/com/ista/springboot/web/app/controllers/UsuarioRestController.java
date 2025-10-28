package com.ista.springboot.web.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ista.springboot.web.app.models.entity.Usuario;
import com.ista.springboot.web.app.models.services.IUsuarioService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class UsuarioRestController {

    @Autowired
    private IUsuarioService usuarioService;

    // ✅ Listar todos los usuarios
    @GetMapping("/usuarios")
    public List<Usuario> index() {
        return usuarioService.findAll();
    }

    // ✅ Buscar usuario por ID
    @GetMapping("/usuarios/{id}")
    public Usuario show(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        return usuario;
    }

    // ✅ Crear usuario
    @PostMapping("/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario create(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    // ✅ Actualizar usuario completo
    @PutMapping("/usuarios/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Usuario update(@RequestBody Usuario usuario, @PathVariable Long id) {

        Usuario usuarioActual = usuarioService.findById(id);

        if (usuarioActual == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }

        usuarioActual.setNombre(usuario.getNombre());
        usuarioActual.setClave(usuario.getClave());
        usuarioActual.setEmail(usuario.getEmail());
        usuarioActual.setEstado(usuario.isEstado());

        return usuarioService.save(usuarioActual);
    }

    // ✅ Cambiar estado a desactivado (false)
    @PutMapping("/usuarios/{id}/desactivar")
    @ResponseStatus(HttpStatus.OK)
    public Usuario desactivarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        usuario.setEstado(false);
        return usuarioService.save(usuario);
    }

    // ✅ Eliminar usuario
    @DeleteMapping("/usuarios/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        usuarioService.delete(id);
    }
}
