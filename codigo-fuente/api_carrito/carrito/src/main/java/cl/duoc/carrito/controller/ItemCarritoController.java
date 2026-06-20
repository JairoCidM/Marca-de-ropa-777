package cl.duoc.carrito.controller;

import cl.duoc.carrito.dto.ItemCarritoRequestDTO;
import cl.duoc.carrito.dto.ItemCarritoResponseDTO;
import cl.duoc.carrito.service.ItemCarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
@Tag(name = "Carrito", description = "API para la gestión avanzada del carrito de compras")
public class ItemCarritoController {

    private final ItemCarritoService service;

    @Operation(summary = "Agregar un producto al carrito (Valida con Catálogo y Registro)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ítem agregado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o producto ya en carrito"),
        @ApiResponse(responseCode = "500", description = "Error de comunicación con otros microservicios")
    })
    @PostMapping
    public ResponseEntity<ItemCarritoResponseDTO> agregar(@Valid @RequestBody ItemCarritoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.agregarItem(dto));
    }

    @Operation(summary = "Obtener el carrito completo de un usuario")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ItemCarritoResponseDTO>> obtenerPorUsuario(@PathVariable("usuarioId") long usuarioId) {
        return ResponseEntity.ok(service.obtenerPorUsuario(usuarioId));
    }

    @Operation(summary = "Obtener un ítem específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ítem encontrado"),
        @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ItemCarritoResponseDTO> obtenerPorId(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(summary = "Obtener todos los ítems de todos los carritos (Admin)")
    @ApiResponse(responseCode = "200", description = "Lista global obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<ItemCarritoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @Operation(summary = "Actualizar la cantidad de un ítem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ítem actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ItemCarritoResponseDTO> actualizar(@PathVariable("id") long id, 
                                                             @Valid @RequestBody ItemCarritoRequestDTO dto) {
        return ResponseEntity.ok(service.actualizarItem(id, dto));
    }

    @Operation(summary = "Eliminar un ítem del carrito por su ID")
    @ApiResponse(responseCode = "204", description = "Ítem eliminado exitosamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") long id) {
        service.eliminarItem(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Vaciar todo el carrito de un usuario")
    @ApiResponse(responseCode = "204", description = "Carrito vaciado exitosamente")
    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable("usuarioId") long usuarioId) {
        service.vaciarCarrito(usuarioId);
        return ResponseEntity.noContent().build();
    }
}