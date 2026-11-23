package cl.duoc.wim100.controller;

import cl.duoc.wim100.dto.EstadoRequest;
import cl.duoc.wim100.model.EstadoPostulante;
import cl.duoc.wim100.model.Postulante;
import cl.duoc.wim100.service.PostulanteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostulanteController.class)
class PostulanteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostulanteService postulanteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Postulante postulante;

    @BeforeEach
    void setUp() {
        postulante = new Postulante();
        postulante.setId(1L);
        postulante.setNombre("Camila Rojas");
        postulante.setCorreo("camila@test.com");
        postulante.setTelefono("+56912345678");
        postulante.setArea("Ciberseguridad");
        postulante.setAniosExperiencia(5);
        postulante.setResumenExperiencia("Experiencia en pentesting");
        postulante.setLinkedinUrl("https://linkedin.com/in/camila");
        postulante.setEstado(EstadoPostulante.POSTULADA);
    }

    @Test
    void listarRetornaStatusOk() throws Exception {
        when(postulanteService.listarTodos()).thenReturn(Collections.singletonList(postulante));

        mockMvc.perform(get("/api/postulantes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Camila Rojas"));
    }

    @Test
    void obtenerPorIdRetornaStatusOk() throws Exception {
        when(postulanteService.obtenerPorId(1L)).thenReturn(postulante);

        mockMvc.perform(get("/api/postulantes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Camila Rojas"));
    }

    @Test
    void registrarRetornaCreatedStatus() throws Exception {
        when(postulanteService.registrar(any(Postulante.class))).thenReturn(postulante);

        mockMvc.perform(post("/api/postulantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postulante)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Camila Rojas"));
    }

    @Test
    void cambiarEstadoRetornaOk() throws Exception {
        EstadoRequest request = new EstadoRequest();
        request.setEstado(EstadoPostulante.SELECCIONADA);

        postulante.setEstado(EstadoPostulante.SELECCIONADA);
        when(postulanteService.cambiarEstado(eq(1L), any(EstadoPostulante.class))).thenReturn(postulante);

        mockMvc.perform(patch("/api/postulantes/1/estado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("SELECCIONADA"));
    }

    @Test
    void eliminarRetornaNoContent() throws Exception {
        doNothing().when(postulanteService).eliminar(1L);

        mockMvc.perform(delete("/api/postulantes/1"))
                .andExpect(status().isNoContent());
    }
}
