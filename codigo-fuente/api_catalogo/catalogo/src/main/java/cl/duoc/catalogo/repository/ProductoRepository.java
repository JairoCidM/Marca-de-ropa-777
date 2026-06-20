package cl.duoc.catalogo.repository;

import cl.duoc.catalogo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByTalla(String talla);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByStockGreaterThan(Integer stock);
}