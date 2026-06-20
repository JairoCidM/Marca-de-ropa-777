package cl.duoc.pagos.repository;

import cl.duoc.pagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByUsuarioId(Long usuarioId);
    List<Pago> findByOrdenId(Long ordenId);
    List<Pago> findByEstado(String estado);
}