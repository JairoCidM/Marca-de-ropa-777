package cl.duoc.registro.controller;

import cl.duoc.registro.dto.RegistroRequestDTO;
import cl.duoc.registro.dto.RegistroResponseDTO;
import cl.duoc.registro.service.RegistroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/registros")
@RequiredArgsConstructor
public class RegistroController {

    private final RegistroService registroService;

    @PostMapping
    public ResponseEntity<RegistroResponseDTO> crear(@Valid @RequestBody RegistroRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registroService.crearRegistro(dto));
    }

    @GetMapping
    public ResponseEntity<List<RegistroResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(registroService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroResponseDTO> obtenerPorId(@PathVariable long id) {
        return ResponseEntity.ok(registroService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroResponseDTO> actualizar(@PathVariable long id,
                                                           @Valid @RequestBody RegistroRequestDTO dto) {
        return ResponseEntity.ok(registroService.actualizarRegistro(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        registroService.eliminarRegistro(id);
        return ResponseEntity.noContent().build();
    }
}