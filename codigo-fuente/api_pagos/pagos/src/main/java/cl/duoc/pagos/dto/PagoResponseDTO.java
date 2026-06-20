package cl.duoc.pagos.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoResponseDTO {

    private Long id;
    private Long ordenId;
    private Long usuarioId;
    private Double monto;
    private String metodo;
    private String estado;
    private LocalDateTime fechaPago;
}