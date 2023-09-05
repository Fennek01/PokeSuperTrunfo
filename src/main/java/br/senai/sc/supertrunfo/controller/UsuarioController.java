package br.senai.sc.supertrunfo.controller;


import br.senai.sc.supertrunfo.model.DTO.UsuarioDTO;
import br.senai.sc.supertrunfo.model.entity.Usuario;
import br.senai.sc.supertrunfo.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/create")
    public ResponseEntity<Usuario> create(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDTO, usuario);
        return ResponseEntity.ok(usuarioService.create(usuario));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Usuario>> buscarTodos() {
        return ResponseEntity.ok(usuarioService.buscarTodos());
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody @Valid UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDTO, usuario);
        usuario.setId(id);
        return ResponseEntity.ok(usuarioService.update(usuario));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        usuarioService.delete(id);
    }

}

