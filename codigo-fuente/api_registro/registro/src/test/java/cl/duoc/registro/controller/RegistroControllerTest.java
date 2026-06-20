package cl.duoc.registro.controller;

import cl.duoc.registro.dto.RegistroResponseDTO;
import cl.duoc.registro.service.RegistroService;
import cl.duoc.registro.exception.GlobalExceptionHandler; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RegistroControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RegistroService registroService;

    @InjectMocks
    private RegistroController registroController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(registroController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void obtenerPorId_Retorna200Ok() throws Exception {
        // Arrange
        RegistroResponseDTO mockResponse = new RegistroResponseDTO();
        mockResponse.setId(1L);
        mockResponse.setNombre("Test User");
        mockResponse.setEmail("test@mail.com");

        when(registroService.obtenerPorId(1L)).thenReturn(mockResponse);

        // Act & Assert 
        mockMvc.perform(get("/api/registros/1"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerPorId_Retorna404NotFound() throws Exception {
        // Arrange
        when(registroService.obtenerPorId(99L)).thenThrow(new RuntimeException("Usuario no encontrado"));

        // Act & Assert 
        mockMvc.perform(get("/api/registros/99"))
                .andExpect(status().isNotFound());
    }
}