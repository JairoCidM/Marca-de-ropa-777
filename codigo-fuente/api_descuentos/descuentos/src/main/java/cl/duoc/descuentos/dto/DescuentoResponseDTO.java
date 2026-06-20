package cl.duoc.descuentos.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DescuentoResponseDTO {

    private Long id;
    private String codigo;
    private String descripcion;
    private Integer porcentaje;
    private String estado;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private LocalDateTime fechaCreacion;
}