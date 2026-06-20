package cl.duoc.ordenes.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenResponseDTO {

    private Long id;
    private Long usuarioId;
    private Double total;
    private String estado;
    private String direccionEntrega;
    private LocalDateTime fechaOrden;
}