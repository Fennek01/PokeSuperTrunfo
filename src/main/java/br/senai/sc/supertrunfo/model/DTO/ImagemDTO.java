package br.senai.sc.supertrunfo.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagemDTO {

    private String keyName;

    private String bucketName;

}
