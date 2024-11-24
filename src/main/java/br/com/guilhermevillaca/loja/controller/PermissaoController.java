package br.com.guilhermevillaca.loja.controller;

import br.com.guilhermevillaca.loja.model.Permissao;
import br.com.guilhermevillaca.loja.model.TipoUsuario;
import br.com.guilhermevillaca.loja.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/permissao")
public class PermissaoController {

    @Autowired
    private PermissaoRepository permissaoRepository;

    /**
     * Endpoint para listar todas as permissões de um tipo de usuário.
     * Exemplo de URL: http://localhost:8080/permissao/tipo/{tipoUsuario}
     */
    @GetMapping("/tipo/{tipoUsuario}")
    public ResponseEntity<List<Permissao>> listarPorTipoUsuario(@PathVariable TipoUsuario tipoUsuario) {
        List<Permissao> permissoes = permissaoRepository.findByTipoUsuario(tipoUsuario);
        return ResponseEntity.ok(permissoes);
    }

    /**
     * Endpoint para buscar uma permissão por ID.
     * Exemplo de URL: http://localhost:8080/permissao/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Permissao> buscarPorId(@PathVariable Long id) {
        Optional<Permissao> permissao = permissaoRepository.findById(id);
        return permissao.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para criar uma nova permissão.
     * URL: http://localhost:8080/permissao/criar
     */
    @PostMapping("/criar")
    public ResponseEntity<Permissao> criar(@RequestBody Permissao permissao) {
        Permissao novaPermissao = permissaoRepository.save(permissao);
        return ResponseEntity.ok(novaPermissao);
    }

    /**
     * Endpoint para atualizar uma permissão existente.
     * Exemplo de URL: http://localhost:8080/permissao/atualizar/{id}
     */
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Permissao> atualizar(@PathVariable Long id, @RequestBody Permissao permissaoAtualizada) {
        return permissaoRepository.findById(id).map(permissao -> {
            permissao.setTipoUsuario(permissaoAtualizada.getTipoUsuario());
            permissao.setPermissao(permissaoAtualizada.getPermissao());
            Permissao permissaoSalva = permissaoRepository.save(permissao);
            return ResponseEntity.ok(permissaoSalva);
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para remover uma permissão por ID.
     * Exemplo de URL: http://localhost:8080/permissao/remover/{id}
     */
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        return permissaoRepository.findById(id).map(permissao -> {
            permissaoRepository.delete(permissao);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
