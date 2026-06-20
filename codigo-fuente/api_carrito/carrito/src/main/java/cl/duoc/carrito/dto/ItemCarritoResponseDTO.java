package cl.duoc.carrito.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemCarritoResponseDTO {

    private Long id;
    private Long usuarioId;
    private Long productoId;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
    private LocalDateTime fechaAgregado;
}