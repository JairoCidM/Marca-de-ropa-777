package cl.duoc.ordenes.controller;

import cl.duoc.ordenes.dto.OrdenRequestDTO;
import cl.duoc.ordenes.dto.OrdenResponseDTO;
import cl.duoc.ordenes.service.OrdenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class OrdenController {

    private final OrdenService ordenService;

    @PostMapping
    public ResponseEntity<OrdenResponseDTO> crear(@Valid @RequestBody OrdenRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenService.crearOrden(dto));
    }

    @GetMapping
    public ResponseEntity<List<OrdenResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(ordenService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenResponseDTO> obtenerPorId(@PathVariable long id) {
        return ResponseEntity.ok(ordenService.obtenerPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<OrdenResponseDTO>> obtenerPorUsuario(@PathVariable long usuarioId) {
        return ResponseEntity.ok(ordenService.obtenerPorUsuario(usuarioId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<OrdenResponseDTO>> obtenerPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(ordenService.obtenerPorEstado(estado));
    }

    @GetMapping("/{id}/completa")
    public ResponseEntity<Map<String, Object>> obtenerCompleta(@PathVariable long id) {
    return ResponseEntity.ok(ordenService.obtenerOrdenCompleta(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenResponseDTO> actualizar(@PathVariable long id,
                                                        @Valid @RequestBody OrdenRequestDTO dto) {
        return ResponseEntity.ok(ordenService.actualizarOrden(id, dto));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<OrdenResponseDTO> actualizarEstado(@PathVariable long id,
                                                              @RequestParam String estado) {
        return ResponseEntity.ok(ordenService.actualizarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        ordenService.eliminarOrden(id);
        return ResponseEntity.noContent().build();
    }
}