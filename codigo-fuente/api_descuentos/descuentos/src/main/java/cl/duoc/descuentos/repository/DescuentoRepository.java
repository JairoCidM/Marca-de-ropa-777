package cl.duoc.descuentos.repository;

import cl.duoc.descuentos.model.Descuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DescuentoRepository extends JpaRepository<Descuento, Long> {
    Optional<Descuento> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    List<Descuento> findByEstado(String estado);
}