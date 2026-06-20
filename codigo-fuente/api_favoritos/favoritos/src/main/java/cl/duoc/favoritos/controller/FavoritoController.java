package cl.duoc.favoritos.controller;

import cl.duoc.favoritos.dto.FavoritoRequestDTO;
import cl.duoc.favoritos.dto.FavoritoResponseDTO;
import cl.duoc.favoritos.service.FavoritoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService service;

    @PostMapping
    public ResponseEntity<FavoritoResponseDTO> agregar(@Valid @RequestBody FavoritoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.agregarFavorito(dto));
    }

    @GetMapping
    public ResponseEntity<List<FavoritoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoritoResponseDTO> obtenerPorId(@PathVariable long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<FavoritoResponseDTO>> obtenerPorUsuario(@PathVariable long usuarioId) {
        return ResponseEntity.ok(service.obtenerPorUsuario(usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        service.eliminarFavorito(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/usuario/{usuarioId}/producto/{productoId}")
    public ResponseEntity<Void> eliminarPorUsuarioYProducto(@PathVariable long usuarioId,
                                                             @PathVariable long productoId) {
        service.eliminarPorUsuarioYProducto(usuarioId, productoId);
        return ResponseEntity.noContent().build();
    }
}