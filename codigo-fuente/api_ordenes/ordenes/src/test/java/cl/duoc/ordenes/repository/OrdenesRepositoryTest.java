package cl.duoc.ordenes.repository;

import cl.duoc.ordenes.model.Orden;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrdenRepositoryTest {

    @Autowired
    private OrdenRepository ordenRepository;

    @Test
    @DisplayName("Debe guardar una orden correctamente")
    void debeGuardarOrden() {

        Orden orden = Orden.builder()
                .usuarioId(10L)
                .total(25000.0)
                .estado("PENDIENTE")
                .direccionEntrega("San Bernardo")
                .build();

        Orden guardada = ordenRepository.save(orden);

        assertNotNull(guardada.getId());
        assertEquals(10L, guardada.getUsuarioId());
    }

    @Test
    @DisplayName("Debe encontrar orden por ID")
    void debeEncontrarOrdenPorId() {

        Orden orden = Orden.builder()
                .usuarioId(20L)
                .total(15000.0)
                .estado("PAGADA")
                .direccionEntrega("Puente Alto")
                .build();

        Orden guardada = ordenRepository.save(orden);

        assertTrue(
                ordenRepository.findById(guardada.getId()).isPresent()
        );
    }

    @Test
    @DisplayName("Debe buscar órdenes por usuario")
    void debeBuscarPorUsuario() {

        Orden orden = Orden.builder()
                .usuarioId(99L)
                .total(10000.0)
                .estado("PENDIENTE")
                .direccionEntrega("Santiago")
                .build();

        ordenRepository.save(orden);

        List<Orden> resultado =
                ordenRepository.findByUsuarioId(99L);

        assertFalse(resultado.isEmpty());
        assertEquals(99L, resultado.get(0).getUsuarioId());
    }

    @Test
    @DisplayName("Debe buscar órdenes por estado")
    void debeBuscarPorEstado() {

        Orden orden = Orden.builder()
                .usuarioId(30L)
                .total(5000.0)
                .estado("ENTREGADA")
                .direccionEntrega("Maipú")
                .build();

        ordenRepository.save(orden);

        List<Orden> resultado =
                ordenRepository.findByEstado("ENTREGADA");

        assertFalse(resultado.isEmpty());
        assertEquals("ENTREGADA",
                resultado.get(0).getEstado());
    }
}