package cl.duoc.pagos.controller;

import cl.duoc.pagos.dto.PagoRequestDTO;
import cl.duoc.pagos.dto.PagoResponseDTO;
import cl.duoc.pagos.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<PagoResponseDTO> crear(@Valid @RequestBody PagoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.crearPago(dto));
    }

    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> obtenerPorId(@PathVariable long id) {
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PagoResponseDTO>> obtenerPorUsuario(@PathVariable long usuarioId) {
        return ResponseEntity.ok(pagoService.obtenerPorUsuario(usuarioId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PagoResponseDTO>> obtenerPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(pagoService.obtenerPorEstado(estado));
    }

    @GetMapping("/orden/{ordenId}")
    public ResponseEntity<List<PagoResponseDTO>> obtenerPorOrden(@PathVariable long ordenId) {
        return ResponseEntity.ok(pagoService.obtenerPorOrden(ordenId));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<PagoResponseDTO> actualizarEstado(@PathVariable long id,
                                                             @RequestParam String estado) {
        return ResponseEntity.ok(pagoService.actualizarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }
}