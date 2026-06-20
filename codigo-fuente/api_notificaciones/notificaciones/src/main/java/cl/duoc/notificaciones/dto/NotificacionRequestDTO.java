package cl.duoc.notificaciones.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionRequestDTO {

    @NotNull(message = "El ID de usuario es obligatorio")
    @Positive(message = "El ID de usuario debe ser positivo")
    private Long usuarioId;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 5, max = 255, message = "El mensaje debe tener entre 5 y 255 caracteres")
    private String mensaje;

    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "INFO|ALERTA|PROMOCION|SISTEMA", message = "El tipo debe ser INFO, ALERTA, PROMOCION o SISTEMA")
    private String tipo;
}