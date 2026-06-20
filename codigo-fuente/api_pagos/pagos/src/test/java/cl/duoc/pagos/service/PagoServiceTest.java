package cl.duoc.pagos.service;

import cl.duoc.pagos.dto.PagoRequestDTO;
import cl.duoc.pagos.dto.PagoResponseDTO;
import cl.duoc.pagos.model.Pago;
import cl.duoc.pagos.repository.PagoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoService pagoService;

    @Test
    @DisplayName("Debe crear un pago correctamente")
    void debeCrearPagoCorrectamente() {

        // Given
        PagoRequestDTO dto = new PagoRequestDTO();
        dto.setOrdenId(100L);
        dto.setUsuarioId(10L);
        dto.setMonto(15000.0);
        dto.setMetodo("DEBITO");

        Pago pagoGuardado = Pago.builder()
                .id(1L)
                .ordenId(100L)
                .usuarioId(10L)
                .monto(15000.0)
                .metodo("DEBITO")
                .estado("PENDIENTE")
                .fechaPago(LocalDateTime.now())
                .build();

        when(pagoRepository.saveAndFlush(any(Pago.class)))
                .thenReturn(pagoGuardado);

        // When
        PagoResponseDTO resultado = pagoService.crearPago(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("PENDIENTE", resultado.getEstado());

        verify(pagoRepository, times(1))
                .saveAndFlush(any(Pago.class));
    }

    @Test
    @DisplayName("Debe obtener pago por ID")
    void debeObtenerPagoPorId() {

        Pago pago = Pago.builder()
                .id(1L)
                .ordenId(100L)
                .usuarioId(10L)
                .monto(15000.0)
                .metodo("DEBITO")
                .estado("PENDIENTE")
                .fechaPago(LocalDateTime.now())
                .build();

        when(pagoRepository.findById(1L))
                .thenReturn(Optional.of(pago));

        PagoResponseDTO resultado = pagoService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el pago no existe")
    void debeLanzarExcepcionCuandoPagoNoExiste() {

        when(pagoRepository.findById(999L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> pagoService.obtenerPorId(999L)
        );

        assertTrue(ex.getMessage().contains("Pago no encontrado"));
    }

    @Test
    @DisplayName("Debe actualizar estado del pago")
    void debeActualizarEstado() {

        Pago pago = Pago.builder()
                .id(1L)
                .estado("PENDIENTE")
                .build();

        when(pagoRepository.findById(1L))
                .thenReturn(Optional.of(pago));

        when(pagoRepository.saveAndFlush(any(Pago.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PagoResponseDTO resultado =
                pagoService.actualizarEstado(1L, "APROBADO");

        assertEquals("APROBADO", resultado.getEstado());
    }

    @Test
    @DisplayName("Debe eliminar pago existente")
    void debeEliminarPagoExistente() {

        when(pagoRepository.existsById(1L))
                .thenReturn(true);

        pagoService.eliminarPago(1L);

        verify(pagoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar pago inexistente")
    void debeEliminarPagoInexistente() {

        when(pagoRepository.existsById(99L))
                .thenReturn(false);

        assertThrows(
                RuntimeException.class,
                () -> pagoService.eliminarPago(99L)
        );
    }

    @Test
    @DisplayName("Debe obtener pagos por usuario")
    void debeObtenerPagosPorUsuario() {

        when(pagoRepository.findByUsuarioId(10L))
                .thenReturn(List.of(
                        Pago.builder()
                                .id(1L)
                                .usuarioId(10L)
                                .build()
                ));

        List<PagoResponseDTO> resultado =
                pagoService.obtenerPorUsuario(10L);

        assertEquals(1, resultado.size());
    }
}