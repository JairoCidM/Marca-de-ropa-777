package cl.duoc.catalogo.repository;

import cl.duoc.catalogo.model.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void testSaveYFindById() {
        // Arrange 
        Producto p = new Producto();
        p.setNombre("Pantalón Jeans");
        p.setDescripcion("Jeans ajustados talla M");
        p.setPrecio(25000.0);
        p.setStock(10);
        p.setTalla("M"); 

        // Act
        Producto guardado = productoRepository.save(p);
        Optional<Producto> encontrado = productoRepository.findById(guardado.getId());

        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals("Pantalón Jeans", encontrado.get().getNombre());
    }

    @Test
    void testFindAll() {
        // Arrange 
        Producto p1 = new Producto(); 
        p1.setNombre("Gorro");
        p1.setDescripcion("Gorro de lana"); 
        p1.setPrecio(5000.0);
        p1.setStock(50);
        p1.setTalla("S"); 
        
        Producto p2 = new Producto(); 
        p2.setNombre("Bufanda");
        p2.setDescripcion("Bufanda gruesa"); 
        p2.setPrecio(8500.0);
        p2.setStock(30);
        p2.setTalla("L"); 
        
        productoRepository.save(p1);
        productoRepository.save(p2);

        // Act
        List<Producto> lista = productoRepository.findAll();

        // Assert
        assertTrue(lista.size() >= 2);
    }
}