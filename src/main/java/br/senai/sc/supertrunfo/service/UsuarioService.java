package br.senai.sc.supertrunfo.service;

import br.senai.sc.supertrunfo.model.entity.Usuario;
import br.senai.sc.supertrunfo.repository.UsuarioRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioRepository Usuariorepository;

    public List<Usuario> buscarTodos() {
        return Usuariorepository.findAll();
    }

    public Usuario create(Usuario usuario) {
        return Usuariorepository.save(usuario);
    }

    public Usuario findById(Long id) {
        return Usuariorepository.findById(id).get();
    }

    public Usuario update(Usuario usuario) {
        return Usuariorepository.save(usuario);
    }

    public void delete(Long id) {
        Usuariorepository.deleteById(id);
    }


}
