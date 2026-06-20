package cl.duoc.registro.controller;

import cl.duoc.registro.dto.RegistroRequestDTO;
import cl.duoc.registro.dto.RegistroResponseDTO;
import cl.duoc.registro.service.RegistroService;
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
@RequestMapping("/api/registros")
@RequiredArgsConstructor
@Tag(name = "Registro", description = "API para la gestión de usuarios y registros")
public class RegistroController {

    private final RegistroService registroService;

    @Operation(summary = "Crear un nuevo registro (usuario)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Registro creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o email ya existe")
    })
    @PostMapping
    public ResponseEntity<RegistroResponseDTO> crear(@Valid @RequestBody RegistroRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registroService.crearRegistro(dto));
    }

    @Operation(summary = "Obtener todos los registros")
    @ApiResponse(responseCode = "200", description = "Lista de registros obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<RegistroResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(registroService.obtenerTodos());
    }

    @Operation(summary = "Obtener un registro por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro encontrado"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RegistroResponseDTO> obtenerPorId(@PathVariable("id") long id) {
        return ResponseEntity.ok(registroService.obtenerPorId(id));
    }

    @Operation(summary = "Actualizar un registro existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado para actualizar")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RegistroResponseDTO> actualizar(@PathVariable("id") long id,
                                                           @Valid @RequestBody RegistroRequestDTO dto) {
        return ResponseEntity.ok(registroService.actualizarRegistro(id, dto));
    }

    @Operation(summary = "Eliminar un registro por su ID")
    @ApiResponse(responseCode = "204", description = "Registro eliminado exitosamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") long id) {
        registroService.eliminarRegistro(id);
        return ResponseEntity.noContent().build();
    }
}