package br.senai.sc.supertrunfo.controller;

import br.senai.sc.supertrunfo.model.DTO.ImagemDTO;
import br.senai.sc.supertrunfo.model.entity.Imagem;
import br.senai.sc.supertrunfo.service.ImagemService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/aws")
public class AWSController {

    private final ImagemService imagemService;

    @GetMapping()
    public ResponseEntity<List<String>> aws_get(){
        imagemService.findAll();
        return ResponseEntity.ok(imagemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<URL> aws_get(@PathVariable Long id) {
        return ResponseEntity.ok(imagemService.createUrl(id));
    }

    @PostMapping
    public ResponseEntity<Imagem> image_post(String bucketName, @RequestParam(name = "img") MultipartFile multipartFile) throws IOException {
        Imagem imagem = new Imagem();
        ImagemDTO imagemDTO = new ImagemDTO(multipartFile.getOriginalFilename());
        BeanUtils.copyProperties(imagemDTO, imagem);
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        System.out.println(Arrays.toString(multipartFile.getBytes()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(200).body(imagemService.save(imagem, "bucket-romario", file));
    }
}
