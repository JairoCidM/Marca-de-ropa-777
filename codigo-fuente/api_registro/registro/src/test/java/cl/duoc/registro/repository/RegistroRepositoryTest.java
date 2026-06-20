package cl.duoc.registro.repository;

import cl.duoc.registro.model.Registro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RegistroRepositoryTest {

    @Autowired
    private RegistroRepository registroRepository;

    @Test
    void testSaveYFindById() {
        // Arrange
        Registro r = new Registro();
        r.setNombre("Ana Lopez");
        r.setEmail("ana@mail.com");
        r.setPassword("pass456");
        r.setRol("ADMIN");

        // Act
        Registro guardado = registroRepository.save(r);
        Optional<Registro> encontrado = registroRepository.findById(guardado.getId());

        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals("ana@mail.com", encontrado.get().getEmail());
    }

    @Test
    void testFindAll() {
        // Arrange
        Registro r1 = new Registro();
        r1.setNombre("Pedro"); r1.setEmail("pedro@mail.com"); r1.setPassword("111"); r1.setRol("CLIENTE");
        
        Registro r2 = new Registro();
        r2.setNombre("Maria"); r2.setEmail("maria@mail.com"); r2.setPassword("222"); r2.setRol("CLIENTE");
        
        registroRepository.save(r1);
        registroRepository.save(r2);

        // Act
        List<Registro> lista = registroRepository.findAll();

        // Assert
        assertTrue(lista.size() >= 2);
    }
}