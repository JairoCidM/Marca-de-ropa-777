package cl.duoc.ventas.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "orden_id", nullable = false)
    private Long ordenId;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private Integer cantidadProductos;

    @Column(nullable = false)
    private String estado;

    @Column(name = "fecha_venta", updatable = false)
    private LocalDateTime fechaVenta;

    @PrePersist
    public void prePersist() {
        this.fechaVenta = LocalDateTime.now();
    }
}