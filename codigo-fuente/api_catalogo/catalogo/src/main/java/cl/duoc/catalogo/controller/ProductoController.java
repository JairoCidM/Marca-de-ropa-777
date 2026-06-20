package cl.duoc.catalogo.controller;

import cl.duoc.catalogo.dto.ProductoRequestDTO;
import cl.duoc.catalogo.dto.ProductoResponseDTO;
import cl.duoc.catalogo.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/catalogo")
@RequiredArgsConstructor
@Tag(name = "Catálogo de Productos", description = "Endpoints para la gestión del inventario y catálogo de prendas de la marca 777")
public class ProductoController {

    private final ProductoService service;

    @PostMapping
    @Operation(summary = "Registrar un nuevo producto", description = "Crea un nuevo producto en el catálogo validando sus atributos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o producto duplicado")
    })
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearProducto(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todos los productos", description = "Devuelve una lista completa de todos los productos.")
    @ApiResponse(responseCode = "200", description = "Lista recuperada con éxito")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar producto por ID", description = "Obtiene los detalles de un producto específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/talla/{talla}")
    @Operation(summary = "Filtrar productos por talla")
    @ApiResponse(responseCode = "200", description = "Búsqueda completada")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerPorTalla(@PathVariable("talla") String talla) {
        return ResponseEntity.ok(service.obtenerPorTalla(talla));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar productos por nombre")
    @ApiResponse(responseCode = "200", description = "Búsqueda completada")
    public ResponseEntity<List<ProductoResponseDTO>> buscarPorNombre(@RequestParam("nombre") String nombre) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable("id") long id,
                                                           @Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.ok(service.actualizarProducto(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable("id") long id) {
        service.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}