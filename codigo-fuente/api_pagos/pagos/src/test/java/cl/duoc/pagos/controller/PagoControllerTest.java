package cl.duoc.pagos.controller;

import cl.duoc.pagos.dto.PagoResponseDTO;
import cl.duoc.pagos.service.PagoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagoController.class)
class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    @Test
    @DisplayName("GET /api/pagos debe retornar 200")
    void debeRetornarListaDePagos() throws Exception {

        when(pagoService.obtenerTodos())
                .thenReturn(List.of());

        mockMvc.perform(
                get("/api/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/pagos/{id} debe retornar 200")
    void debeRetornarPagoPorId() throws Exception {

        PagoResponseDTO dto = PagoResponseDTO.builder()
                .id(1L)
                .build();

        when(pagoService.obtenerPorId(1L))
                .thenReturn(dto);

        mockMvc.perform(
                get("/api/pagos/1")
        )
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/pagos/{id} debe retornar 204")
    void debeEliminarPago() throws Exception {

        doNothing().when(pagoService).eliminarPago(1L);

        mockMvc.perform(
                delete("/api/pagos/1")
        )
        .andExpect(status().isNoContent());
    }
}