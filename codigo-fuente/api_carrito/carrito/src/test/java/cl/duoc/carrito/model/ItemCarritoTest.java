package cl.duoc.carrito.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemCarritoTest {

    @Test
    void testPrePersistAsignaFecha() {
        // Arrange
        ItemCarrito item = new ItemCarrito();
        item.setUsuarioId(1L);
        item.setProductoId(10L);
        
        assertNull(item.getFechaAgregado());

        // Act
        item.prePersist();

        // Assert
        assertNotNull(item.getFechaAgregado(), "La fecha no debe ser nula después del prePersist");
    }
}