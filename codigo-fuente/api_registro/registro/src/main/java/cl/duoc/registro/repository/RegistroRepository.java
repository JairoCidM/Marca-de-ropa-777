package cl.duoc.registro.repository;

import cl.duoc.registro.model.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {
    Optional<Registro> findByEmail(String email);
    boolean existsByEmail(String email);
}