package cl.duoc.carrito.controller;

import cl.duoc.carrito.dto.ItemCarritoRequestDTO;
import cl.duoc.carrito.dto.ItemCarritoResponseDTO;
import cl.duoc.carrito.service.ItemCarritoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class ItemCarritoController {

    private final ItemCarritoService service;

    @PostMapping
    public ResponseEntity<ItemCarritoResponseDTO> agregar(@Valid @RequestBody ItemCarritoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.agregarItem(dto));
    }

    @GetMapping
    public ResponseEntity<List<ItemCarritoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemCarritoResponseDTO> obtenerPorId(@PathVariable long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ItemCarritoResponseDTO>> obtenerPorUsuario(@PathVariable long usuarioId) {
        return ResponseEntity.ok(service.obtenerPorUsuario(usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemCarritoResponseDTO> actualizar(@PathVariable long id,
                                                              @Valid @RequestBody ItemCarritoRequestDTO dto) {
        return ResponseEntity.ok(service.actualizarItem(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        service.eliminarItem(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/usuario/{usuarioId}/vaciar")
    public ResponseEntity<Void> vaciar(@PathVariable long usuarioId) {
        service.vaciarCarrito(usuarioId);
        return ResponseEntity.noContent().build();
    }
}