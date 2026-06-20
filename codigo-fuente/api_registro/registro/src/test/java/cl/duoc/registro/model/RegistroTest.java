package cl.duoc.registro.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegistroTest {

    @Test
    void testConstructoresYGettersSetters() {
        // Arrange & Act
        Registro registro = new Registro();
        registro.setId(1L);
        registro.setNombre("Juan Perez");
        registro.setEmail("juan@mail.com");
        registro.setPassword("secreta123");
        registro.setRol("CLIENTE");

        // Assert
        assertEquals(1L, registro.getId());
        assertEquals("Juan Perez", registro.getNombre());
        assertEquals("juan@mail.com", registro.getEmail());
        assertEquals("secreta123", registro.getPassword());
        assertEquals("CLIENTE", registro.getRol());
    }

    @Test
    void testPrePersist() {
        // Arrange
        Registro registro = new Registro();
        
        // Act
        registro.prePersist();

        // Assert
        assertNotNull(registro.getFechaRegistro(), "La fecha de registro debe generarse automáticamente");
    }
}