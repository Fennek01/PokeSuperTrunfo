package br.senai.sc.supertrunfo.service;

import br.senai.sc.supertrunfo.model.entity.Imagem;
import br.senai.sc.supertrunfo.repository.ImagemRepository;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ImagemService {

    private final ImagemRepository imagemRepository;
    private static String accessKey;
    private static String secretKey;

    @Value("${access_key}")
    public void setAccessKey(String accessKey) {
        ImagemService.accessKey = accessKey;
    }

    @Value("${secret_key}")
    public void setSecretKey(String secretKey) {
        ImagemService.secretKey = secretKey;
    }

    public Imagem findOne(Long id) {
        return imagemRepository.findById(id).orElseThrow();
    }

    public String findOne(Imagem imagem){
        try {
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder
                    .standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();
            if (amazonS3Client.doesBucketExist("bucket-romario")) {
                return amazonS3Client.generatePresignedUrl("bucket-romario",
                        imagem.getKeyName(), DateTime
                                .now()
                                .plusDays(1)
                                .toDate()
                ).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    public List<String> findAll() {
        List<String> lista = new ArrayList<>();
        for (Imagem imagem : imagemRepository.findAll()) {
            lista.add(findOne(imagem));
        }
        return lista;
    }

    public Imagem save(Imagem imagem, String bucketName, File file) {
        try {
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3Client s3client = (AmazonS3Client) AmazonS3ClientBuilder
                    .standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();

            s3client.putObject(new PutObjectRequest(bucketName, imagem.getKeyName(), file));
            return imagemRepository.save(imagem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Imagem não encontrada");
    }

    public void delete(String bucketName, Long id) {
        try {
            Imagem imagem = imagemRepository.findById(id).orElse(null);
            if (imagem != null) {
                BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
                AmazonS3Client s3client = (AmazonS3Client) AmazonS3ClientBuilder
                        .standard()
                        .withRegion(Regions.US_EAST_1)
                        .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                        .build();
                s3client.deleteObject(new DeleteObjectRequest(bucketName, imagem.getKeyName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        imagemRepository.deleteById(id);
    }

}
