package cl.duoc.catalogo.service;

import cl.duoc.catalogo.dto.ProductoRequestDTO;
import cl.duoc.catalogo.dto.ProductoResponseDTO;
import cl.duoc.catalogo.model.Producto;
import cl.duoc.catalogo.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void crearProducto_AtrapaBugDelCodigo() {
        // Arrange
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Chaqueta");
        dto.setDescripcion("Chaqueta de cuero");
        dto.setPrecio(45000.0);
        dto.setStock(5);
        dto.setTalla("L");

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            productoService.crearProducto(dto);
        });
    }

    @Test
    void obtenerPorId_Exitoso() {
        // Arrange
        Producto p = new Producto();
        p.setId(5L);
        p.setNombre("Zapatillas");
        p.setDescripcion("Deportivas");
        p.setPrecio(30000.0);
        p.setStock(12);
        p.setTalla("42");
        
        when(productoRepository.findById(5L)).thenReturn(Optional.of(p));

        // Act
        ProductoResponseDTO response = productoService.obtenerPorId(5L);
        
        // Assert
        assertNotNull(response);
        assertEquals("Zapatillas", response.getNombre());
    }

    @Test
    void obtenerPorId_FallaCuandoNoExiste() {
        // Arrange
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            productoService.obtenerPorId(99L);
        });
        assertTrue(ex.getMessage().toLowerCase().contains("no encontrad"));
    }
}