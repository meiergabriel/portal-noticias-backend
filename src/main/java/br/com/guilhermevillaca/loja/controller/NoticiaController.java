package br.com.guilhermevillaca.loja.controller;

import br.com.guilhermevillaca.loja.model.Noticia;
import br.com.guilhermevillaca.loja.repository.NoticiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/noticia")
public class NoticiaController {

    @Autowired
    private NoticiaRepository noticiaRepository;

    /**
     * Endpoint para listar todas as notícias.
     * URL: http://localhost:8080/noticia/listar
     */
    @GetMapping("/listar")
    public ResponseEntity<List<Noticia>> listarTodas() {
        return ResponseEntity.ok((List<Noticia>) noticiaRepository.findAll());
    }

    /**
     * Endpoint para listar notícias por categoria.
     * Exemplo de URL: http://localhost:8080/noticia/categoria/{categoriaId}
     */
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Noticia>> listarPorCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(noticiaRepository.findByCategoriaId(categoriaId));
    }

    /**
     * Endpoint para listar notícias por editor.
     * Exemplo de URL: http://localhost:8080/noticia/editor/{editorId}
     */
    @GetMapping("/editor/{editorId}")
    public ResponseEntity<List<Noticia>> listarPorEditor(@PathVariable Long editorId) {
        return ResponseEntity.ok(noticiaRepository.findByEditorId(editorId));
    }

    /**
     * Endpoint para buscar uma notícia por ID.
     * Exemplo de URL: http://localhost:8080/noticia/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Noticia> buscarPorId(@PathVariable Long id) {
        Optional<Noticia> noticia = noticiaRepository.findById(id);
        return noticia.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para criar uma nova notícia.
     * URL: http://localhost:8080/noticia/criar
     */
    @PostMapping("/criar")
    public ResponseEntity<Noticia> criar(@RequestBody Noticia noticia) {
        Noticia novaNoticia = noticiaRepository.save(noticia);
        return ResponseEntity.ok(novaNoticia);
    }

    /**
     * Endpoint para atualizar uma notícia existente.
     * Exemplo de URL: http://localhost:8080/noticia/atualizar/{id}
     */
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Noticia> atualizar(@PathVariable Long id, @RequestBody Noticia noticiaAtualizada) {
        return noticiaRepository.findById(id).map(noticia -> {
            noticia.setTitulo(noticiaAtualizada.getTitulo());
            noticia.setCorpo(noticiaAtualizada.getCorpo());
            noticia.setImagemUrl(noticiaAtualizada.getImagemUrl());
            noticia.setCategoria(noticiaAtualizada.getCategoria());
            noticia.setEditor(noticiaAtualizada.getEditor());
            Noticia noticiaSalva = noticiaRepository.save(noticia);
            return ResponseEntity.ok(noticiaSalva);
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para remover uma notícia por ID.
     * Exemplo de URL: http://localhost:8080/noticia/remover/{id}
     */
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        return noticiaRepository.findById(id).map(noticia -> {
            noticiaRepository.delete(noticia);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
