package cl.duoc.descuentos.controller;

import cl.duoc.descuentos.dto.DescuentoRequestDTO;
import cl.duoc.descuentos.dto.DescuentoResponseDTO;
import cl.duoc.descuentos.service.DescuentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/descuentos")
@RequiredArgsConstructor
public class DescuentoController {

    private final DescuentoService descuentoService;

    @PostMapping
    public ResponseEntity<DescuentoResponseDTO> crear(@Valid @RequestBody DescuentoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(descuentoService.crearDescuento(dto));
    }

    @GetMapping
    public ResponseEntity<List<DescuentoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(descuentoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DescuentoResponseDTO> obtenerPorId(@PathVariable long id) {
        return ResponseEntity.ok(descuentoService.obtenerPorId(id));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<DescuentoResponseDTO> obtenerPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(descuentoService.obtenerPorCodigo(codigo));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<DescuentoResponseDTO>> obtenerPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(descuentoService.obtenerPorEstado(estado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DescuentoResponseDTO> actualizar(@PathVariable long id,
                                                            @Valid @RequestBody DescuentoRequestDTO dto) {
        return ResponseEntity.ok(descuentoService.actualizarDescuento(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        descuentoService.eliminarDescuento(id);
        return ResponseEntity.noContent().build();
    }
}