package cl.duoc.carrito.controller;

import cl.duoc.carrito.dto.ItemCarritoResponseDTO;
import cl.duoc.carrito.service.ItemCarritoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ItemCarritoController.class)
class ItemCarritoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemCarritoService service;

    @Test
    void testObtenerPorIdEndpoint() throws Exception {
        // Arrange
        ItemCarritoResponseDTO mockResponse = new ItemCarritoResponseDTO();
        mockResponse.setId(1L);
        mockResponse.setUsuarioId(10L);
        mockResponse.setSubtotal(15000.0);

        when(service.obtenerPorId(1L)).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/api/carrito/1"))
                .andExpect(status().isOk()) //200
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.usuarioId").value(10L))
                .andExpect(jsonPath("$.subtotal").value(15000.0));
    }
}