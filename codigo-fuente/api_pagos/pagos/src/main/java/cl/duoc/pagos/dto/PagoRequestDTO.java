package cl.duoc.pagos.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequestDTO {

    @NotNull(message = "El ID de orden es obligatorio")
    @Positive(message = "El ID de orden debe ser positivo")
    private Long ordenId;

    @NotNull(message = "El ID de usuario es obligatorio")
    @Positive(message = "El ID de usuario debe ser positivo")
    private Long usuarioId;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    private Double monto;

    @NotBlank(message = "El método de pago es obligatorio")
    @Pattern(regexp = "DEBITO|CREDITO|TRANSFERENCIA", message = "El método debe ser DEBITO, CREDITO o TRANSFERENCIA")
    private String metodo;
}