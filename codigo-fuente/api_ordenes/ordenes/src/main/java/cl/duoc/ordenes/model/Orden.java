package cl.duoc.ordenes.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String direccionEntrega;

    @Column(name = "fecha_orden", updatable = false)
    private LocalDateTime fechaOrden;

    @PrePersist
    public void prePersist() {
        this.fechaOrden = LocalDateTime.now();
    }
}