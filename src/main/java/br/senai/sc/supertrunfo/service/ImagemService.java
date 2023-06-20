package br.senai.sc.supertrunfo.service;

import br.senai.sc.supertrunfo.model.entity.Carta;
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
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImagemService {

    private final ImagemRepository imagemRepository;
    private static String accessKey;
    private static String secretKey;


    /**
     * Método para encontrar uma imagem no S3, que está presente no banco de dados
     * @param id
     * @return Vai retornar imagem requisitada
     */
    public Imagem findOne(Long id) {
        Optional<Imagem> imagemOptional = imagemRepository.findById(id);
        if (imagemOptional.isPresent()) {
            return imagemOptional.get();
        }
        throw new RuntimeException("Imagem não encontrada");
    }

    /**
     * Método para salvar e econtrar uma imagem no S3.
     * @param imagem
     * @return Vai retornar a url da imagem salva no S3
     */
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

    /**
     * Método que vai gerar a url da imagem salva no S3, para que possa ser acessada no navegador.
     * @param id
     */
    public URL createUrl(Long id){
        try {
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder
                    .standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();
            if (amazonS3Client.doesBucketExist("bucket-romario")) {
                return amazonS3Client.generatePresignedUrl("bucket-romario",
                        findOne(id).getKeyName(), DateTime
                                .now()
                                .plusDays(1)
                                .toDate()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    /**
     * Método para encontrar todas as imagens salvas no S3
     * @param id
     */
    public List<String> findAll() {
        List<String> lista = new ArrayList<>();
        for (Imagem imagem : imagemRepository.findAll()) {
            lista.add(findOne(imagem));
        }
        return lista;
    }

    /**
     * Método para salvar uma imagem do S3.
     * @param imagem
     * @param bucketName
     * @param file
     */
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

}
