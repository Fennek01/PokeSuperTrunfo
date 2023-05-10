package br.senai.sc.supertrunfo.repository;

import br.senai.sc.supertrunfo.model.entity.Carta;
import br.senai.sc.supertrunfo.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT Usuario FROM Usuario WHERE nome = :nome AND senha = :senha")
    Usuario findByNome(String nome, String senha);

}
