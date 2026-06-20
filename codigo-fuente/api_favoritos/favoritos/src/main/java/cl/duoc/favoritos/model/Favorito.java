package cl.duoc.favoritos.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favoritos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @Column(name = "fecha_agregado", updatable = false)
    private LocalDateTime fechaAgregado;

    @PrePersist
    public void prePersist() {
        this.fechaAgregado = LocalDateTime.now();
    }
}