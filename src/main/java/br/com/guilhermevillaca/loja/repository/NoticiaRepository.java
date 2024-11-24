package br.com.guilhermevillaca.loja.repository;

import br.com.guilhermevillaca.loja.model.Noticia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticiaRepository extends CrudRepository<Noticia, Long> {

    // Método para listar todas as notícias de uma determinada categoria
    List<Noticia> findByCategoriaId(Long categoriaId);

    // Método para listar todas as notícias publicadas por um editor específico
    List<Noticia> findByEditorId(Long editorId);
}