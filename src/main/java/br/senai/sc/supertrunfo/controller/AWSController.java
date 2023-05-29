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
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Controller
@RequestMapping("/aws")
public class AWSController {
    @Autowired
    ImagemService imagemService;

    @Value("${app.accessKey}")
    private String accessKey;

    @Value("${app.secretKey}")
    private String secretKey;

    @GetMapping("/{bucketName}/{keyName}")
    public ResponseEntity<URL> aws_get(@PathVariable String bucketName, @PathVariable String keyName){
        URL url = null;
        try{

            GetObjectRequest objectRequest = new GetObjectRequest(bucketName, keyName);
            AmazonS3Client amazonS3Client = new AmazonS3Client();

            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

            amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.US_EAST_1)
                    .build();


            if(amazonS3Client.doesBucketExist(bucketName)){
                url = amazonS3Client.generatePresignedUrl(bucketName,keyName, DateTime.now().plusDays(1).toDate());
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }
        return ResponseEntity.status(200).body(url);
    }

    @PostMapping
    public ResponseEntity<URL> aws_post() {
        URL url = null;
        try{
            AmazonS3Client amazonS3Client = new AmazonS3Client();

            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

            String bucketName = "bucket-romario";

            amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.US_EAST_1)
                    .build();

            if(amazonS3Client.doesBucketExist(bucketName)){
                File imagem = new File("C:\\Users\\gustavo_g_abreu\\Desktop\\DetetivePikachu.jpg");
                image_post(bucketName, imagem.getName());
                PutObjectRequest request = new PutObjectRequest(bucketName, imagem.getName(), imagem);
                amazonS3Client.putObject(request);
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }
        return ResponseEntity.status(200).build();
    }

    public ResponseEntity<Imagem> image_post(String bucketName, String keyName) {
        ImagemDTO imagemDTO = new ImagemDTO(keyName, bucketName);
        Imagem imagem = new Imagem();
        BeanUtils.copyProperties(imagemDTO, imagem);
        return ResponseEntity.ok(imagemService.create(imagem));
    }
}
