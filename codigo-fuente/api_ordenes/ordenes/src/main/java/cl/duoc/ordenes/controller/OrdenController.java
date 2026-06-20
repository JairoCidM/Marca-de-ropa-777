package cl.duoc.ordenes.controller;

import cl.duoc.ordenes.dto.OrdenRequestDTO;
import cl.duoc.ordenes.dto.OrdenResponseDTO;
import cl.duoc.ordenes.service.OrdenService;
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
import java.util.Map;

@Tag(
        name = "Órdenes",
        description = "Operaciones de gestión de órdenes"
)
@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class OrdenController {

    private final OrdenService ordenService;

    @Operation(
            summary = "Crear orden",
            description = "Registra una nueva orden en el sistema"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Orden creada correctamente"
    )
    @PostMapping
    public ResponseEntity<OrdenResponseDTO> crear(
            @Valid @RequestBody OrdenRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ordenService.crearOrden(dto));
    }

    @Operation(
            summary = "Listar órdenes",
            description = "Obtiene todas las órdenes registradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado obtenido correctamente"
    )
    @GetMapping
    public ResponseEntity<List<OrdenResponseDTO>> obtenerTodas() {

        return ResponseEntity.ok(ordenService.obtenerTodas());
    }

    @Operation(
            summary = "Buscar orden por ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orden encontrada"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrdenResponseDTO> obtenerPorId(

            @Parameter(description = "ID de la orden", required = true)
            @PathVariable long id) {

        return ResponseEntity.ok(ordenService.obtenerPorId(id));
    }

    @Operation(
            summary = "Buscar órdenes por usuario"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Órdenes encontradas"
    )
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<OrdenResponseDTO>> obtenerPorUsuario(

            @Parameter(description = "ID del usuario")
            @PathVariable long usuarioId) {

        return ResponseEntity.ok(
                ordenService.obtenerPorUsuario(usuarioId)
        );
    }

    @Operation(
            summary = "Buscar órdenes por estado"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Órdenes encontradas"
    )
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<OrdenResponseDTO>> obtenerPorEstado(

            @Parameter(description = "Estado de la orden")
            @PathVariable String estado) {

        return ResponseEntity.ok(
                ordenService.obtenerPorEstado(estado)
        );
    }

    @Operation(
            summary = "Obtener orden completa",
            description = "Obtiene la orden junto con información relacionada de otros microservicios"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orden completa obtenida"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @GetMapping("/{id}/completa")
    public ResponseEntity<Map<String, Object>> obtenerCompleta(

            @Parameter(description = "ID de la orden")
            @PathVariable long id) {

        return ResponseEntity.ok(
                ordenService.obtenerOrdenCompleta(id)
        );
    }

    @Operation(
            summary = "Actualizar orden"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orden actualizada"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrdenResponseDTO> actualizar(

            @Parameter(description = "ID de la orden")
            @PathVariable long id,

            @Valid @RequestBody OrdenRequestDTO dto) {

        return ResponseEntity.ok(
                ordenService.actualizarOrden(id, dto)
        );
    }

    @Operation(
            summary = "Actualizar estado de una orden"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @PutMapping("/{id}/estado")
    public ResponseEntity<OrdenResponseDTO> actualizarEstado(

            @Parameter(description = "ID de la orden")
            @PathVariable long id,

            @Parameter(description = "Nuevo estado de la orden")
            @RequestParam String estado) {

        return ResponseEntity.ok(
                ordenService.actualizarEstado(id, estado)
        );
    }

    @Operation(
            summary = "Eliminar orden"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Orden eliminada"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(

            @Parameter(description = "ID de la orden")
            @PathVariable long id) {

        ordenService.eliminarOrden(id);
        return ResponseEntity.noContent().build();
    }
}