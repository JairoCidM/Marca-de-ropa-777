package cl.duoc.ordenes.service;

import cl.duoc.ordenes.client.EnvioClient;
import cl.duoc.ordenes.client.PagoClient;
import cl.duoc.ordenes.dto.OrdenRequestDTO;
import cl.duoc.ordenes.dto.OrdenResponseDTO;
import cl.duoc.ordenes.model.Orden;
import cl.duoc.ordenes.repository.OrdenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdenServiceTest {

    @Mock
    private OrdenRepository ordenRepository;

    @Mock
    private PagoClient pagoClient;

    @Mock
    private EnvioClient envioClient;

    @InjectMocks
    private OrdenService ordenService;

    @Test
    void debeCrearOrden() {

        OrdenRequestDTO dto = new OrdenRequestDTO();
        dto.setUsuarioId(1L);
        dto.setTotal(15000.0);
        dto.setDireccionEntrega("San Bernardo");

        Orden orden = Orden.builder()
                .id(1L)
                .usuarioId(1L)
                .total(15000.0)
                .estado("PENDIENTE")
                .direccionEntrega("San Bernardo")
                .build();

        when(ordenRepository.saveAndFlush(any(Orden.class)))
                .thenReturn(orden);

        OrdenResponseDTO resultado = ordenService.crearOrden(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void debeObtenerOrdenPorId() {

        Orden orden = Orden.builder()
                .id(1L)
                .usuarioId(1L)
                .total(15000.0)
                .estado("PENDIENTE")
                .direccionEntrega("San Bernardo")
                .build();

        when(ordenRepository.findById(1L))
                .thenReturn(Optional.of(orden));

        OrdenResponseDTO resultado = ordenService.obtenerPorId(1L);

        assertEquals(1L, resultado.getId());
    }

    @Test
    void debeEliminarOrden() {

        when(ordenRepository.existsById(1L))
                .thenReturn(true);

        ordenService.eliminarOrden(1L);

        verify(ordenRepository).deleteById(1L);
    }
}