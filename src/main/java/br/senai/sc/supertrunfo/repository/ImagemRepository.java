package br.senai.sc.supertrunfo.repository;

import br.senai.sc.supertrunfo.model.entity.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long> {



}
