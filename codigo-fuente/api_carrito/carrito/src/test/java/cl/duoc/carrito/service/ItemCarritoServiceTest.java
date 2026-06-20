package cl.duoc.carrito.service;

import cl.duoc.carrito.dto.ItemCarritoResponseDTO;
import cl.duoc.carrito.model.ItemCarrito;
import cl.duoc.carrito.repository.ItemCarritoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemCarritoServiceTest {

    @Mock
    private ItemCarritoRepository repository;

    @InjectMocks
    private ItemCarritoService service;

    @Test
    void testObtenerPorId() {
        // Arrange
        ItemCarrito mockItem = ItemCarrito.builder()
                .id(1L)
                .usuarioId(2L)
                .productoId(50L)
                .cantidad(1)
                .precioUnitario(5000.0)
                .build();
        
        when(repository.findById(1L)).thenReturn(Optional.of(mockItem));

        // Act
        ItemCarritoResponseDTO response = service.obtenerPorId(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(5000.0, response.getSubtotal()); // 1 * 5000
        verify(repository, times(1)).findById(1L);
    }
}