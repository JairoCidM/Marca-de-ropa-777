package cl.duoc.notificaciones.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionResponseDTO {

    private Long id;
    private Long usuarioId;
    private String mensaje;
    private String tipo;
    private boolean leida;
    private LocalDateTime fechaCreacion;
}