package cl.duoc.wim100.controller;

import cl.duoc.wim100.dto.LoginRequest;
import cl.duoc.wim100.dto.LoginResponse;
import cl.duoc.wim100.model.Rol;
import cl.duoc.wim100.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void loginEndpointRetornaOk() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("evaluadora1");
        request.setPassword("wim2026");

        LoginResponse response = new LoginResponse(1L, "evaluadora1", "Camila Evaluadora", Rol.EVALUADORA);

        when(authService.login(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("evaluadora1"))
                .andExpect(jsonPath("$.nombre").value("Camila Evaluadora"))
                .andExpect(jsonPath("$.rol").value("EVALUADORA"));
    }
}
