package cl.duoc.pagos.controller;

import cl.duoc.pagos.dto.PagoRequestDTO;
import cl.duoc.pagos.dto.PagoResponseDTO;
import cl.duoc.pagos.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Pagos",
        description = "Operaciones de gestión de pagos"
)
@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @Operation(
            summary = "Registrar pago",
            description = "Crea un nuevo pago asociado a una orden"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Pago registrado correctamente"
    )
    @PostMapping
    public ResponseEntity<PagoResponseDTO> crear(
            @Valid @RequestBody PagoRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pagoService.crearPago(dto));
    }

    @Operation(
            summary = "Listar pagos",
            description = "Obtiene todos los pagos registrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado obtenido correctamente"
    )
    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }

    @Operation(
            summary = "Buscar pago por ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> obtenerPorId(
            @Parameter(description = "ID del pago", required = true)
            @PathVariable long id) {

        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @Operation(
            summary = "Buscar pagos por usuario"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pagos encontrados"
    )
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PagoResponseDTO>> obtenerPorUsuario(
            @Parameter(description = "ID del usuario")
            @PathVariable long usuarioId) {

        return ResponseEntity.ok(pagoService.obtenerPorUsuario(usuarioId));
    }

    @Operation(
            summary = "Buscar pagos por estado"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pagos encontrados"
    )
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PagoResponseDTO>> obtenerPorEstado(
            @Parameter(description = "Estado del pago")
            @PathVariable String estado) {

        return ResponseEntity.ok(pagoService.obtenerPorEstado(estado));
    }

    @Operation(
            summary = "Buscar pagos por orden"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pagos encontrados"
    )
    @GetMapping("/orden/{ordenId}")
    public ResponseEntity<List<PagoResponseDTO>> obtenerPorOrden(
            @Parameter(description = "ID de la orden")
            @PathVariable long ordenId) {

        return ResponseEntity.ok(pagoService.obtenerPorOrden(ordenId));
    }

    @Operation(
            summary = "Actualizar estado del pago"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @PutMapping("/{id}/estado")
    public ResponseEntity<PagoResponseDTO> actualizarEstado(
            @Parameter(description = "ID del pago")
            @PathVariable long id,

            @Parameter(description = "Nuevo estado del pago")
            @RequestParam String estado) {

        return ResponseEntity.ok(
                pagoService.actualizarEstado(id, estado)
        );
    }

    @Operation(
            summary = "Eliminar pago"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pago eliminado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del pago")
            @PathVariable long id) {

        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }
}