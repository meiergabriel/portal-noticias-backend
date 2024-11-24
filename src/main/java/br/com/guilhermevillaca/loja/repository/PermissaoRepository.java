package br.com.guilhermevillaca.loja.repository;

import br.com.guilhermevillaca.loja.model.Permissao;
import br.com.guilhermevillaca.loja.model.TipoUsuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissaoRepository extends CrudRepository<Permissao, Long> {

    // Método para listar todas as permissões de um tipo de usuário específico
    List<Permissao> findByTipoUsuario(TipoUsuario tipoUsuario);
}
