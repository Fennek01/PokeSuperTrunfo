package br.senai.sc.supertrunfo.service;

import br.senai.sc.supertrunfo.model.entity.Imagem;
import br.senai.sc.supertrunfo.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagemService {

    @Autowired
    ImagemRepository imagemRepository;

    public Imagem create(Imagem imagem) {
        return imagemRepository.save(imagem);
    }

}
