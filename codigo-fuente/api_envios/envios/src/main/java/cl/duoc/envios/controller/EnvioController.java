package cl.duoc.envios.controller;

import cl.duoc.envios.dto.EnvioRequestDTO;
import cl.duoc.envios.dto.EnvioResponseDTO;
import cl.duoc.envios.service.EnvioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/envios")
@RequiredArgsConstructor
public class EnvioController {

    private final EnvioService envioService;

    @PostMapping
    public ResponseEntity<EnvioResponseDTO> crear(@Valid @RequestBody EnvioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(envioService.crearEnvio(dto));
    }

    @GetMapping
    public ResponseEntity<List<EnvioResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(envioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvioResponseDTO> obtenerPorId(@PathVariable long id) {
        return ResponseEntity.ok(envioService.obtenerPorId(id));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<EnvioResponseDTO> obtenerPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(envioService.obtenerPorCodigo(codigo));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<EnvioResponseDTO>> obtenerPorUsuario(@PathVariable long usuarioId) {
        return ResponseEntity.ok(envioService.obtenerPorUsuario(usuarioId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EnvioResponseDTO>> obtenerPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(envioService.obtenerPorEstado(estado));
    }

    @GetMapping("/orden/{ordenId}")
    public ResponseEntity<List<EnvioResponseDTO>> obtenerPorOrden(@PathVariable long ordenId) {
        return ResponseEntity.ok(envioService.obtenerPorOrden(ordenId));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<EnvioResponseDTO> actualizarEstado(@PathVariable long id,
                                                              @RequestParam String estado) {
        return ResponseEntity.ok(envioService.actualizarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        envioService.eliminarEnvio(id);
        return ResponseEntity.noContent().build();
    }
}