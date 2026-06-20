package cl.duoc.registro.service;

import cl.duoc.registro.dto.RegistroRequestDTO;
import cl.duoc.registro.dto.RegistroResponseDTO;
import cl.duoc.registro.model.Registro;
import cl.duoc.registro.repository.RegistroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistroServiceTest {

    @Mock
    private RegistroRepository registroRepository;

    @InjectMocks
    private RegistroService registroService;

    @Test
    void crearRegistro_AtrapaBugDelCodigo() {
        // Arrange
        RegistroRequestDTO dto = new RegistroRequestDTO();
        dto.setNombre("Carlos");
        dto.setEmail("carlos@mail.com");
        dto.setPassword("123");
        dto.setRol("CLIENTE");

        // Act & Assert
        assertThrows(Exception.class, () -> {
            registroService.crearRegistro(dto);
        });
    }

    @Test
    void obtenerPorId_Exitoso() {
        // Arrange
        Registro r = new Registro();
        r.setId(1L);
        r.setNombre("Admin");
        r.setEmail("admin@duoc.cl");
        
        when(registroRepository.findById(1L)).thenReturn(Optional.of(r));

        // Act
        RegistroResponseDTO response = registroService.obtenerPorId(1L);
        // Assert
        assertNotNull(response);
        assertEquals("admin@duoc.cl", response.getEmail());
    }

    @Test
    void crearRegistro_FallaCuandoEmailYaExiste() {
        // Arrange: Regla de negocio
        when(registroRepository.existsByEmail("admin@duoc.cl")).thenReturn(true);
        
        RegistroRequestDTO dto = new RegistroRequestDTO();
        dto.setEmail("admin@duoc.cl");

        // Act & Assert
        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            registroService.crearRegistro(dto);
        });
        
        assertNotNull(excepcion);
    }
}