package cl.duoc.wim100.service;

import cl.duoc.wim100.dto.LoginRequest;
import cl.duoc.wim100.dto.LoginResponse;
import cl.duoc.wim100.model.Rol;
import cl.duoc.wim100.model.Usuario;
import cl.duoc.wim100.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AuthService authService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private Usuario usuarioPrueba;

    @BeforeEach
    void setUp() {
        usuarioPrueba = new Usuario();
        usuarioPrueba.setId(1L);
        usuarioPrueba.setUsername("evaluadora1");
        usuarioPrueba.setPasswordHash(encoder.encode("wim2026"));
        usuarioPrueba.setNombre("Camila Evaluadora");
        usuarioPrueba.setRol(Rol.EVALUADORA);
    }

    @Test
    void loginExitoso() {
        LoginRequest request = new LoginRequest();
        request.setUsername("evaluadora1");
        request.setPassword("wim2026");

        when(usuarioRepository.findByUsername("evaluadora1")).thenReturn(Optional.of(usuarioPrueba));

        LoginResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("evaluadora1", response.getUsername());
        assertEquals(Rol.EVALUADORA, response.getRol());
        verify(usuarioRepository, times(1)).findByUsername("evaluadora1");
    }

    @Test
    void loginUsuarioNoEncontradoLanzaExcepcion() {
        LoginRequest request = new LoginRequest();
        request.setUsername("inexistente");
        request.setPassword("123456");

        when(usuarioRepository.findByUsername("inexistente")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> authService.login(request));
    }

    @Test
    void loginPasswordIncorrectoLanzaExcepcion() {
        LoginRequest request = new LoginRequest();
        request.setUsername("evaluadora1");
        request.setPassword("passwordIncorrecto");

        when(usuarioRepository.findByUsername("evaluadora1")).thenReturn(Optional.of(usuarioPrueba));

        assertThrows(IllegalArgumentException.class, () -> authService.login(request));
    }
}
