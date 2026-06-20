package cl.duoc.ordenes.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrdenModelTest {

    @Test
    @DisplayName("Debe crear una orden correctamente")
    void debeCrearOrden() {

        Orden orden = Orden.builder()
                .id(1L)
                .usuarioId(10L)
                .total(25000.0)
                .estado("PENDIENTE")
                .direccionEntrega("San Bernardo")
                .build();

        assertEquals(1L, orden.getId());
        assertEquals(10L, orden.getUsuarioId());
        assertEquals(25000.0, orden.getTotal());
        assertEquals("PENDIENTE", orden.getEstado());
        assertEquals("San Bernardo", orden.getDireccionEntrega());
    }

    @Test
    void debeAsignarFecha() {

        Orden orden = new Orden();

        orden.prePersist();

        assertNotNull(orden.getFechaOrden());
    }
}