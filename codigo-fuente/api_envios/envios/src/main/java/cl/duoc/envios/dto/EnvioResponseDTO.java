package cl.duoc.envios.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnvioResponseDTO {

    private Long id;
    private Long ordenId;
    private Long usuarioId;
    private String direccion;
    private String estado;
    private String codigoSeguimiento;
    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaEntrega;
}