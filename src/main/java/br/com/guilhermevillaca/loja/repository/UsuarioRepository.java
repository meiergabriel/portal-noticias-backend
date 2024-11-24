package br.com.guilhermevillaca.loja.repository;

import br.com.guilhermevillaca.loja.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    
    // Consulta para encontrar um usuário por e-mail (útil para login e verificação de existência)
    Optional<Usuario> findByEmail(String email);
}
