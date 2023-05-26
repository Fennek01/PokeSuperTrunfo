package br.senai.sc.supertrunfo.repository;

import br.senai.sc.supertrunfo.model.entity.Carta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CartaRepository extends JpaRepository<Carta, Long> {

    Carta findByNome(String nome);
    Page<Carta> findAll(Pageable pageable);

}
