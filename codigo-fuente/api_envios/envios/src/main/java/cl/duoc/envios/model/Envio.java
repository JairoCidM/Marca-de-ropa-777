package cl.duoc.envios.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "envios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orden_id", nullable = false)
    private Long ordenId;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String estado;

    @Column(name = "codigo_seguimiento", unique = true)
    private String codigoSeguimiento;

    @Column(name = "fecha_envio", updatable = false)
    private LocalDateTime fechaEnvio;

    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    @PrePersist
    public void prePersist() {
        this.fechaEnvio = LocalDateTime.now();
        this.codigoSeguimiento = "ENV-" + System.currentTimeMillis();
    }
}