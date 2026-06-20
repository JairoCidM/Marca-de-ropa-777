package cl.duoc.carrito.repository;

import cl.duoc.carrito.model.ItemCarrito;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
    "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class ItemCarritoRepositoryTest {

    @Autowired
    private ItemCarritoRepository repository;

    @Test
    void testFindByUsuarioId() {
        // Arrange
        ItemCarrito item = ItemCarrito.builder()
                .usuarioId(5L)
                .productoId(100L)
                .cantidad(2)
                .precioUnitario(1000.0)
                .build();
        repository.save(item);

        // Act
        List<ItemCarrito> resultados = repository.findByUsuarioId(5L);

        // Assert
        assertFalse(resultados.isEmpty());
        assertEquals(5L, resultados.get(0).getUsuarioId());
        assertEquals(100L, resultados.get(0).getProductoId());
    }
}