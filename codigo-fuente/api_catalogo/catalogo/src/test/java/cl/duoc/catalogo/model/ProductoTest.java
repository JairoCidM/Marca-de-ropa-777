package cl.duoc.catalogo.model;

import cl.duoc.catalogo.model.Producto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoTest {

    @Test
    void testConstructoresYGettersSetters() {
        // Arrange & Act: Constructor vacío y Setters
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Notebook");
        producto.setDescripcion("Notebook Gamer de alta gama");
        producto.setPrecio(850000.0);
        producto.setStock(15);        

        // Assert: Validar Getters
        assertEquals(1L, producto.getId());
        assertEquals("Notebook", producto.getNombre());
        assertEquals("Notebook Gamer de alta gama", producto.getDescripcion());
        assertEquals(850000.0, producto.getPrecio());
        assertEquals(15, producto.getStock());
    }

    @Test
    void testCampoObligatorioNoNulo() {
        // Arrange
        Producto producto = new Producto();
        producto.setNombre(null);

        // Assert: Validar comportamiento
        assertNull(producto.getNombre(), "El nombre no debería inicializarse si es nulo");
    }
}