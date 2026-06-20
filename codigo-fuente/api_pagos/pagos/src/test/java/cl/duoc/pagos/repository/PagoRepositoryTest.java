package cl.duoc.pagos.repository;

import cl.duoc.pagos.model.Pago;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PagoRepositoryTest {

    @Autowired
    private PagoRepository pagoRepository;

    @Test
    @DisplayName("Debe guardar un pago correctamente")
    void debeGuardarPago() {

        Pago pago = Pago.builder()
                .ordenId(100L)
                .usuarioId(10L)
                .monto(15000.0)
                .metodo("DEBITO")
                .estado("PENDIENTE")
                .build();

        Pago guardado = pagoRepository.save(pago);

        assertNotNull(guardado.getId());
    }

    @Test
    @DisplayName("Debe encontrar pago por usuario")
    void debeEncontrarPagoPorUsuario() {

        Pago pago = Pago.builder()
                .ordenId(100L)
                .usuarioId(99L)
                .monto(10000.0)
                .metodo("CREDITO")
                .estado("PENDIENTE")
                .build();

        pagoRepository.save(pago);

        assertFalse(
                pagoRepository.findByUsuarioId(99L).isEmpty()
        );
    }
}