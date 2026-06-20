package cl.duoc.pagos.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PagoModelTest {

    @Test
    @DisplayName("Debe crear objeto Pago correctamente")
    void debeCrearPagoCorrectamente() {

        Pago pago = Pago.builder()
                .id(1L)
                .ordenId(100L)
                .usuarioId(10L)
                .monto(15000.0)
                .metodo("DEBITO")
                .estado("PENDIENTE")
                .build();

        assertEquals(1L, pago.getId());
        assertEquals(100L, pago.getOrdenId());
        assertEquals(10L, pago.getUsuarioId());
        assertEquals(15000.0, pago.getMonto());
        assertEquals("DEBITO", pago.getMetodo());
        assertEquals("PENDIENTE", pago.getEstado());
    }

    @Test
    @DisplayName("Debe asignar fecha al ejecutar prePersist")
    void debeAsignarFechaAlEjecutarPrePersist() {

        Pago pago = new Pago();

        pago.prePersist();

        assertNotNull(pago.getFechaPago());
    }
}