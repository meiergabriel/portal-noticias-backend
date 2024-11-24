package br.com.guilhermevillaca.loja.controller;

import br.com.guilhermevillaca.loja.model.Comentario;
import br.com.guilhermevillaca.loja.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    /**
     * Endpoint para listar todos os comentários de uma notícia.
     * Exemplo de URL: http://localhost:8080/comentario/noticia/{noticiaId}
     */
    @GetMapping("/noticia/{noticiaId}")
    public ResponseEntity<List<Comentario>> listarPorNoticia(@PathVariable Long noticiaId) {
        List<Comentario> comentarios = comentarioRepository.findByNoticiaId(noticiaId);
        return ResponseEntity.ok(comentarios);
    }

    /**
     * Endpoint para buscar um comentário por ID.
     * Exemplo de URL: http://localhost:8080/comentario/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Comentario> buscarPorId(@PathVariable Long id) {
        Optional<Comentario> comentario = comentarioRepository.findById(id);
        return comentario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para criar um novo comentário.
     * URL: http://localhost:8080/comentario/criar
     */
    @PostMapping("/criar")
    public ResponseEntity<Comentario> criar(@RequestBody Comentario comentario) {
        Comentario novoComentario = comentarioRepository.save(comentario);
        return ResponseEntity.ok(novoComentario);
    }

    /**
     * Endpoint para atualizar um comentário existente.
     * Exemplo de URL: http://localhost:8080/comentario/atualizar/{id}
     */
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Comentario> atualizar(@PathVariable Long id, @RequestBody Comentario comentarioAtualizado) {
        return comentarioRepository.findById(id).map(comentario -> {
            comentario.setTexto(comentarioAtualizado.getTexto());
            comentario.setDataComentario(comentarioAtualizado.getDataComentario());
            comentario.setNoticia(comentarioAtualizado.getNoticia());
            comentario.setUsuario(comentarioAtualizado.getUsuario());
            Comentario comentarioSalvo = comentarioRepository.save(comentario);
            return ResponseEntity.ok(comentarioSalvo);
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para remover um comentário por ID.
     * Exemplo de URL: http://localhost:8080/comentario/remover/{id}
     */
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        return comentarioRepository.findById(id).map(comentario -> {
            comentarioRepository.delete(comentario);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
