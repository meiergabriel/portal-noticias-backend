package br.com.guilhermevillaca.loja.repository;

import br.com.guilhermevillaca.loja.model.Comentario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends CrudRepository<Comentario, Long> {

    // Método para listar todos os comentários de uma notícia específica
    List<Comentario> findByNoticiaId(Long noticiaId);
}
