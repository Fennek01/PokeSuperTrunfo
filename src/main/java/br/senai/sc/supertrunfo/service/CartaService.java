package br.senai.sc.supertrunfo.service;

import br.senai.sc.supertrunfo.model.entity.Carta;
import br.senai.sc.supertrunfo.repository.CartaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartaService {

    @Autowired
    private CartaRepository cartaRepository;

    public Carta create(Carta carta) {
        return cartaRepository.save(carta);
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
        List<Carta> cartas = buscarTodos();
        for (Carta carta : cartas) {
            if (carta.getNome().equalsIgnoreCase(nome)) {
                return carta;
            }
        }
        throw new RuntimeException("Carta não encontrada");
    }

}
