package cl.duoc.wim100.service;

import cl.duoc.wim100.exception.DuplicateEmailException;
import cl.duoc.wim100.exception.ResourceNotFoundException;
import cl.duoc.wim100.model.EstadoPostulante;
import cl.duoc.wim100.model.Postulante;
import cl.duoc.wim100.repository.PostulanteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostulanteServiceTest {

    @Mock
    private PostulanteRepository postulanteRepository;

    @InjectMocks
    private PostulanteService postulanteService;

    private Postulante postulante1;

    @BeforeEach
    void setUp() {
        postulante1 = new Postulante();
        postulante1.setId(1L);
        postulante1.setNombre("Camila Rojas");
        postulante1.setCorreo("camila@test.com");
        postulante1.setTelefono("+56912345678");
        postulante1.setArea("Ciberseguridad");
        postulante1.setAniosExperiencia(5);
        postulante1.setEstado(EstadoPostulante.POSTULADA);
        postulante1.setRegistradaPor("Sistema");
    }

    @Test
    void listarTodosDevuelveLista() {
        when(postulanteRepository.findAll()).thenReturn(Arrays.asList(postulante1));

        List<Postulante> resultado = postulanteService.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals("Camila Rojas", resultado.get(0).getNombre());
    }

    @Test
    void obtenerPorIdExitoso() {
        when(postulanteRepository.findById(1L)).thenReturn(Optional.of(postulante1));

        Postulante resultado = postulanteService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void obtenerPorIdNoEncontradoLanzaExcepcion() {
        when(postulanteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postulanteService.obtenerPorId(99L));
    }

    @Test
    void registrarExitoso() {
        Postulante nuevo = new Postulante();
        nuevo.setNombre("Paula Araya");
        nuevo.setCorreo("paula@test.com");

        when(postulanteRepository.findByCorreo("paula@test.com")).thenReturn(Optional.empty());
        when(postulanteRepository.save(any(Postulante.class))).thenReturn(nuevo);

        Postulante registrado = postulanteService.registrar(nuevo);

        assertNotNull(registrado);
        assertEquals(EstadoPostulante.POSTULADA, registrado.getEstado());
        verify(postulanteRepository, times(1)).save(nuevo);
    }

    @Test
    void registrarCorreoDuplicadoLanzaExcepcion() {
        Postulante duplicado = new Postulante();
        duplicado.setCorreo("camila@test.com");

        when(postulanteRepository.findByCorreo("camila@test.com")).thenReturn(Optional.of(postulante1));

        assertThrows(DuplicateEmailException.class, () -> postulanteService.registrar(duplicado));
    }

    @Test
    void cambiarEstadoExitoso() {
        when(postulanteRepository.findById(1L)).thenReturn(Optional.of(postulante1));
        when(postulanteRepository.save(any(Postulante.class))).thenReturn(postulante1);

        Postulante actualizado = postulanteService.cambiarEstado(1L, EstadoPostulante.SELECCIONADA);

        assertEquals(EstadoPostulante.SELECCIONADA, actualizado.getEstado());
    }

    @Test
    void eliminarExitoso() {
        when(postulanteRepository.findById(1L)).thenReturn(Optional.of(postulante1));
        doNothing().when(postulanteRepository).delete(postulante1);

        postulanteService.eliminar(1L);

        verify(postulanteRepository, times(1)).delete(postulante1);
    }
}
