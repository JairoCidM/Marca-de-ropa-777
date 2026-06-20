package cl.duoc.ordenes.controller;

import cl.duoc.ordenes.dto.OrdenResponseDTO;
import cl.duoc.ordenes.service.OrdenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrdenController.class)
class OrdenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdenService ordenService;

    @Test
    void debeListarOrdenes() throws Exception {

        when(ordenService.obtenerTodas())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/ordenes"))
                .andExpect(status().isOk());
    }

    @Test
    void debeObtenerOrdenPorId() throws Exception {

        OrdenResponseDTO dto = OrdenResponseDTO.builder()
                .id(1L)
                .build();

        when(ordenService.obtenerPorId(1L))
                .thenReturn(dto);

        mockMvc.perform(get("/api/ordenes/1"))
                .andExpect(status().isOk());
    }

    @Test
    void debeEliminarOrden() throws Exception {

        doNothing().when(ordenService).eliminarOrden(1L);

        mockMvc.perform(delete("/api/ordenes/1"))
                .andExpect(status().isNoContent());
    }
}