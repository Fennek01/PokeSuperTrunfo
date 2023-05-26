//package br.senai.sc.supertrunfo.controller;
//
//import br.senai.sc.supertrunfo.model.enums.Tipagem;
//import br.senai.sc.supertrunfo.model.DTO.CartaDTO;
//import br.senai.sc.supertrunfo.model.entity.Carta;
//import br.senai.sc.supertrunfo.service.CartaService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.mockito.Mockito.*;
//import static org.springframework.http.MediaType.APPLICATION_JSON;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class CartaControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private CartaService cartaService;
//
//    @Test
//    void createCardTest() throws Exception {
//        // given
//        Carta carta = new Carta(1L, 1L, "Pikachu", "A", "B",Tipagem.Fogo, Tipagem.Agua, 100, 100, 100, 100, 100, 100, 100);
//        String json = objectMapper.writeValueAsString(carta);
//
//        when(cartaService.create(any())).thenReturn(carta);
//
//        mockMvc.perform(post("/carta/create")
//                        .contentType(APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value(carta));
//    }
//
//    @Test
//    public void getAllCartasTest() throws Exception {
//        CartaDTO cartaDTO = new CartaDTO(Tipagem.Fogo, Tipagem.Fogo, "Pikachu", "A", "B", 100, 100, 100, 100, 100, 100, 100, 1L);
//
//        Carta carta = new Carta(1L, 1L, "Pikachu", "A", "B",Tipagem.Fogo, Tipagem.Agua, 100, 100, 100, 100, 100, 100, 100);
//        BeanUtils.copyProperties(cartaDTO, carta);
//
//        when(cartaService.buscarTodos()).thenReturn(List.of(carta));
//
//        mockMvc.perform(get("/carta/all"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].nome").value("Pikachu"))
//                .andExpect(jsonPath("$[0].numPokedex").value(1))
//                .andExpect(jsonPath("$[0].imagem").value("A"))
//                .andExpect(jsonPath("$[0].descricao").value("B"))
//                .andExpect(jsonPath("$[0].total").value(100));
//    }
//
//    @Test
//    void getOneCardTest() throws Exception {
//        Carta carta= new Carta(1L, 1L, "Pikachu", "A", "B",Tipagem.Fogo, Tipagem.Agua, 100, 100, 100, 100, 100, 100, 100);
//
//        when(cartaService.findById(1L)).thenReturn(carta);
//
//        mockMvc.perform(get("/carta/find/1", carta.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value(carta));
//    }
//
//    @Test
//    void getOneCardNameTest() throws Exception {
//        Carta carta= new Carta(1L, 1L, "Pikachu", "A", "B",Tipagem.Fogo, Tipagem.Agua, 100, 100, 100, 100, 100, 100, 100);
//
//        when(cartaService.findByNome("Pikachu")).thenReturn(carta);
//
//        mockMvc.perform(get("/carta/findByName/Pikachu", carta.getNome()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value(carta));
//    }
//
//    @Test
//    public void updateCardTest() throws Exception {
//        Carta cartaNovo = new Carta(1L, 1L, "Pikachu", "A", "B",Tipagem.Fogo, Tipagem.Agua, 100, 100, 100, 100, 100, 100, 100);
//
//        when(cartaService.findById(1L)).thenReturn(cartaNovo);
//        when(cartaService.update(any())).thenReturn(cartaNovo);
//
//        mockMvc.perform(put("/carta/update/1", cartaNovo.getId())
//                        .contentType(APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(cartaNovo))
//                        .accept(APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value(cartaNovo));
//    }
//
//    @Test
//    public void deleteCardTest() throws Exception {
//        mockMvc.perform(delete("/carta/delete/1"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//}