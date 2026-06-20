package cl.duoc.envios.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioRequestDTO {

    @NotNull(message = "El ID de orden es obligatorio")
    @Positive(message = "El ID de orden debe ser positivo")
    private Long ordenId;

    @NotNull(message = "El ID de usuario es obligatorio")
    @Positive(message = "El ID de usuario debe ser positivo")
    private Long usuarioId;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, max = 255, message = "La dirección debe tener entre 5 y 255 caracteres")
    private String direccion;
}