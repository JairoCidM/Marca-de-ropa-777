package cl.duoc.ventas.repository;

import cl.duoc.ventas.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByUsuarioId(Long usuarioId);
    List<Venta> findByOrdenId(Long ordenId);
    List<Venta> findByEstado(String estado);
}