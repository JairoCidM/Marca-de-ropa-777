package cl.duoc.notificaciones.controller;

import cl.duoc.notificaciones.dto.NotificacionRequestDTO;
import cl.duoc.notificaciones.dto.NotificacionResponseDTO;
import cl.duoc.notificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService service;

    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> crear(@Valid @RequestBody NotificacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearNotificacion(dto));
    }

    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> obtenerPorId(@PathVariable long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacionResponseDTO>> obtenerPorUsuario(@PathVariable long usuarioId) {
        return ResponseEntity.ok(service.obtenerPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public ResponseEntity<List<NotificacionResponseDTO>> obtenerNoLeidas(@PathVariable long usuarioId) {
        return ResponseEntity.ok(service.obtenerNoLeidas(usuarioId));
    }

    @PutMapping("/{id}/marcar-leida")
    public ResponseEntity<NotificacionResponseDTO> marcarComoLeida(@PathVariable long id) {
        return ResponseEntity.ok(service.marcarComoLeida(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        service.eliminarNotificacion(id);
        return ResponseEntity.noContent().build();
    }
}