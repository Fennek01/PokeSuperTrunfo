package br.senai.sc.supertrunfo.service;

import br.senai.sc.supertrunfo.model.entity.Carta;
import br.senai.sc.supertrunfo.repository.CartaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartaService {

    private CartaRepository cartaRepository;

    public Carta create(Carta carta) {
        return cartaRepository.save(carta);
    }

    public Page<Carta> buscarTodos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cartaRepository.findAll(pageable);
    }

    public List<Carta> buscarTodos() {
        return cartaRepository.findAll();
    }

    public void delete(Long numPokedex) {
        cartaRepository.deleteById(numPokedex);
    }

    public Carta update(Carta carta) {
        return cartaRepository.save(carta);
    }

    public Carta findById(Long numPokedex) {
        Optional<Carta> cartaOptional = cartaRepository.findById(numPokedex);
        if (cartaOptional.isPresent()) {
            return cartaOptional.get();
        }
        throw new RuntimeException("Carta não encontrada");
    }

    public Carta findByNome(String nome) {
        Optional<Carta> cartaOptional = Optional.ofNullable(cartaRepository.findByNome(nome));
        if (cartaOptional.isPresent()) {
            return cartaOptional.get();
        }
        throw new RuntimeException("Carta não encontrada");
    }

}
