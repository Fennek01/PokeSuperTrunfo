package br.senai.sc.supertrunfo.controller;

import br.senai.sc.supertrunfo.model.DTO.UsuarioDTO;
import br.senai.sc.supertrunfo.model.entity.Usuario;
import br.senai.sc.supertrunfo.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void createUserTest() throws Exception {
        Usuario usuario = new Usuario(1L, "U1", "123", "gustavo", "gustavo@gmail", null);

        String json = objectMapper.writeValueAsString(usuario);

        when(usuarioService.create(any())).thenReturn(usuario);

        mockMvc.perform(post("/usuario/create")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("gustavo"))
                .andExpect(jsonPath("$.senha").value("123"))
                .andExpect(jsonPath("$.email").value("gustavo@gmail"));
    }

    @Test
    public void getAllUsuariosTest() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO("gustavo", "abreu", "123", "gustavo@gmail", null);

        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDTO, usuario);

        when(usuarioService.buscarTodos()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/usuario/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome").value("gustavo"))
                .andExpect(jsonPath("$[0].senha").value("123"))
                .andExpect(jsonPath("$[0].email").value("gustavo@gmail"));
    }

    @Test
    void getOneUserTest() throws Exception {
        Usuario usuario = new Usuario(1L, "U1", "123", "gustavo", "gustavo@gmail", null);

        when(usuarioService.findById(1L)).thenReturn(usuario);

        mockMvc.perform(get("/usuario/find/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("gustavo"))
                .andExpect(jsonPath("$.senha").value("123"))
                .andExpect(jsonPath("$.email").value("gustavo@gmail"));
    }

    @Test
    public void updateUserTest() throws Exception {
        Usuario usuarioNovo = new Usuario(1L, "U1", "123", "gustavo", "gustavo@gmail", null);

        when(usuarioService.findById(1L)).thenReturn(usuarioNovo);
        when(usuarioService.update(any())).thenReturn(usuarioNovo);

        mockMvc.perform(patch("/usuario/update/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioNovo))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("gustavo"))
                .andExpect(jsonPath("$.senha").value("123"))
                .andExpect(jsonPath("$.email").value("gustavo@gmail"));
    }

    @Test
    public void deleteUserTest() throws Exception {
        mockMvc.perform(delete("/usuario/delete/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}