package br.com.guilhermevillaca.loja.controller;

import br.com.guilhermevillaca.loja.model.Categoria;
import br.com.guilhermevillaca.loja.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "categoria")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * Endpoint para listar todas as categorias.
     * URL: http://localhost:8080/categoria/listar
     */
    @GetMapping(value = "listar")
    public ResponseEntity<List<Categoria>> listar() {
        return ResponseEntity.ok((List<Categoria>) categoriaRepository.findAll());
    }

    /**
     * Endpoint para buscar uma categoria por ID.
     * Exemplo de URL:  http://localhost:8080/categoria/{id}
     */
    @GetMapping(value = "{id}")
    public ResponseEntity<Categoria> listarPorId(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para criar uma nova categoria.
     * URL: http://localhost:8080/categoria/criar
     */
    @PostMapping(value = "criar")
    public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria) {
        Categoria novaCategoria = categoriaRepository.save(categoria);
        return ResponseEntity.ok(novaCategoria);
    }

    /**
     * Endpoint para editar uma categoria existente.
     * Exemplo de URL: http://localhost:8080/categoria/editar/{id}
     */
    @PutMapping(value = "editar/{id}")
    public ResponseEntity<Categoria> editar(@PathVariable Long id, @RequestBody Categoria categoria) {
        return categoriaRepository.findById(id).map(categoriaExistente -> {
            categoriaExistente.setNome(categoria.getNome());
            categoriaExistente.setDescricao(categoria.getDescricao());
            Categoria categoriaAtualizada = categoriaRepository.save(categoriaExistente);
            return ResponseEntity.ok(categoriaAtualizada);
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para remover uma categoria por ID.
     * Exemplo de URL: http://localhost:8080/categoria/remover/{id}
     */
    @DeleteMapping(value = "remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        return categoriaRepository.findById(id).map(categoria -> {
            categoriaRepository.delete(categoria);
            return ResponseEntity.noContent().<Void>build();  // Definindo explicitamente <Void> após noContent()
        }).orElse(ResponseEntity.notFound().build());
    }
}
