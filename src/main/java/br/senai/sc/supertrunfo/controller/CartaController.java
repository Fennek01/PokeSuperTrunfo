package br.senai.sc.supertrunfo.controller;

import br.senai.sc.supertrunfo.model.DTO.CartaDTO;
import br.senai.sc.supertrunfo.model.entity.Carta;
import br.senai.sc.supertrunfo.service.CartaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/carta")
public class CartaController {

    private CartaService cartaService;

    @PostMapping("/create")
    public ResponseEntity<Carta> create(@RequestBody @Valid CartaDTO cartaDTO) {
        Carta carta = new Carta();
        BeanUtils.copyProperties(cartaDTO, carta);
        return ResponseEntity.ok(cartaService.create(carta));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Carta>> buscarTodos() {
        return ResponseEntity.ok(cartaService.buscarTodos());
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        cartaService.delete(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Carta> update(@PathVariable Long id, @RequestBody @Valid CartaDTO cartaDTO) {
        Carta carta = cartaService.findById(id);
        BeanUtils.copyProperties(cartaDTO, carta);
        carta.setNumPokedex(id);
        return ResponseEntity.ok(cartaService.update(carta));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Carta> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cartaService.findById(id));
    }

}
