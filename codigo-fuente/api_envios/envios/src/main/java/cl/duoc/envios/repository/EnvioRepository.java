package cl.duoc.envios.repository;

import cl.duoc.envios.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {
    List<Envio> findByUsuarioId(Long usuarioId);
    List<Envio> findByOrdenId(Long ordenId);
    List<Envio> findByEstado(String estado);
    Optional<Envio> findByCodigoSeguimiento(String codigoSeguimiento);
}