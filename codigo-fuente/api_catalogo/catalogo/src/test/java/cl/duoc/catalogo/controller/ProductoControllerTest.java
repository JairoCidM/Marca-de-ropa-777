package cl.duoc.catalogo.controller;

import cl.duoc.catalogo.dto.ProductoResponseDTO;
import cl.duoc.catalogo.service.ProductoService;
import cl.duoc.catalogo.exception.GlobalExceptionHandler; 
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
public class ProductoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    void setUp() {
        // Simula peticiones HTTP
        mockMvc = MockMvcBuilders.standaloneSetup(productoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void obtenerPorId_Retorna200Ok() throws Exception {
        // Arrange
        ProductoResponseDTO mockResponse = new ProductoResponseDTO();
        mockResponse.setId(1L);
        mockResponse.setNombre("Test Product");

        when(productoService.obtenerPorId(1L)).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/api/catalogo/1"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerPorId_Retorna404NotFound() throws Exception {
        // Arrange
        when(productoService.obtenerPorId(99L)).thenThrow(new RuntimeException("Producto no encontrado"));

        // Act & Assert
        mockMvc.perform(get("/api/catalogo/99"))
                .andExpect(status().isNotFound()); // HTTP 404
    }
}