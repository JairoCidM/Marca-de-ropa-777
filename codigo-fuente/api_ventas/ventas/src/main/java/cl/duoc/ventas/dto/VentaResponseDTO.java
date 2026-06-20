package cl.duoc.ventas.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaResponseDTO {

    private Long id;
    private Long usuarioId;
    private Long ordenId;
    private Double total;
    private Integer cantidadProductos;
    private String estado;
    private LocalDateTime fechaVenta;
}