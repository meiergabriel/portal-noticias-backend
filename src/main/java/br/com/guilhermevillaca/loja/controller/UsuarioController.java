package br.com.guilhermevillaca.loja.controller;

import br.com.guilhermevillaca.loja.model.Usuario;
import br.com.guilhermevillaca.loja.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Endpoint para listar todos os usuários.
     * URL: http://localhost:8080/usuario/listar
     */
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok((List<Usuario>) usuarioRepository.findAll());
    }

    /**
     * Endpoint para buscar um usuário por ID.
     * Exemplo de URL: http://localhost:8080/usuario/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para criar um novo usuário.
     * URL: http://localhost:8080/usuario/criar
     */
    @PostMapping("/criar")
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

    /**
     * Endpoint para atualizar um usuário existente.
     * Exemplo de URL: http://localhost:8080/usuario/atualizar/{id}
     */
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioRepository.findById(id).map(usuarioExistente -> {
            usuarioExistente.setNome(usuario.getNome());
            usuarioExistente.setEmail(usuario.getEmail());
            usuarioExistente.setSenha(usuario.getSenha()); // Em uma aplicação real, deve ser criptografada
            usuarioExistente.setTipo(usuario.getTipo());
            Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
            return ResponseEntity.ok(usuarioAtualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para remover um usuário por ID.
     * Exemplo de URL: http://localhost:8080/usuario/remover/{id}
     */
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuarioRepository.delete(usuario);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
