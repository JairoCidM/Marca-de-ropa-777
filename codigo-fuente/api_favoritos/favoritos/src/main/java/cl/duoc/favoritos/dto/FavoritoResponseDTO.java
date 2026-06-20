package cl.duoc.favoritos.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoritoResponseDTO {

    private Long id;
    private Long usuarioId;
    private Long productoId;
    private LocalDateTime fechaAgregado;
}