package cl.duoc.ventas.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaRequestDTO {

    @NotNull(message = "El ID de usuario es obligatorio")
    @Positive(message = "El ID de usuario debe ser positivo")
    private Long usuarioId;

    @NotNull(message = "El ID de orden es obligatorio")
    @Positive(message = "El ID de orden debe ser positivo")
    private Long ordenId;

    @NotNull(message = "El total es obligatorio")
    @Positive(message = "El total debe ser mayor a 0")
    private Double total;

    @NotNull(message = "La cantidad de productos es obligatoria")
    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer cantidadProductos;
}