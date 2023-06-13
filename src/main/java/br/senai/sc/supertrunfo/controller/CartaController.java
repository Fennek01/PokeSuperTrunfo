package br.senai.sc.supertrunfo.controller;

import br.senai.sc.supertrunfo.model.DTO.CartaDTO;
import br.senai.sc.supertrunfo.model.entity.Carta;
import br.senai.sc.supertrunfo.service.CartaService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;


@Controller
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/carta")
public class CartaController {

    private CartaService cartaService;

    @PostMapping("/create")
    public ResponseEntity<Carta> create(@RequestBody @Valid CartaDTO cartaDTO) {
            Carta carta = new Carta();
            BeanUtils.copyProperties(cartaDTO, carta);
            return ResponseEntity.ok(cartaService.create(carta, carta.getId()));
    }

    @GetMapping()
    public ResponseEntity<Page<Carta>> buscarTodos(@RequestParam(name = "page") int page, @RequestParam int size) {
        return ResponseEntity.ok(cartaService.buscarTodos(page, size));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Carta>> buscarTodos() {
        return ResponseEntity.ok(cartaService.buscarTodos());
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        cartaService.delete(id);
    }

    @PutMapping("/update/{id}")
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

    @GetMapping("/findByName/{nome}/{senha}")
    public ResponseEntity<Carta> findByNome(@PathVariable String nome) {
        Carta carta = cartaService.findByNome(nome);
        if (carta != null) {
            return ResponseEntity.ok(carta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
