package br.senai.sc.supertrunfo.security.repository;

import br.senai.sc.supertrunfo.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsuario_Email(String email);

}
