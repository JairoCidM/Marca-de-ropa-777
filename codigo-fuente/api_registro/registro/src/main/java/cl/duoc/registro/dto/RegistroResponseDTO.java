package cl.duoc.registro.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroResponseDTO {

    private Long id;
    private String nombre;
    private String email;
    private String rol;
    private LocalDateTime fechaRegistro;
    // password nunca se devuelve
}