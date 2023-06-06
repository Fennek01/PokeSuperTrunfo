package br.senai.sc.supertrunfo.controller;

import br.senai.sc.supertrunfo.model.DTO.CartaDTO;
import br.senai.sc.supertrunfo.model.entity.Carta;
import br.senai.sc.supertrunfo.service.CartaService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static scala.Console.print;


@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/carta")
public class CartaController {

    private CartaService cartaService;

    @Value("${app.accessKey}")
    private String accessKey;

    @Value("${app.secretKey}")
    private String secretKey;

    @PostMapping("/create")
    public ResponseEntity<Carta> create(@RequestParam("imagem") MultipartFile imagemFile, @RequestBody @Valid CartaDTO cartaDTO) {
        Carta carta = new Carta();
        BeanUtils.copyProperties(cartaDTO, carta);
        URL url = null;
        try {
            AmazonS3Client amazonS3Client = new AmazonS3Client();

            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

            String bucketName = "bucket-romario";

            amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.US_EAST_1)
                    .build();
            if (!imagemFile.isEmpty()) {
                String nomeArquivo = imagemFile.getOriginalFilename();
            }
            return ResponseEntity.ok(cartaService.create(carta));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
