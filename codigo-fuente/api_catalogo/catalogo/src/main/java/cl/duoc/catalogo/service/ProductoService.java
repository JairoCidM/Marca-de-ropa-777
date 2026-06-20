package cl.duoc.catalogo.service;

import cl.duoc.catalogo.dto.ProductoRequestDTO;
import cl.duoc.catalogo.dto.ProductoResponseDTO;
import cl.duoc.catalogo.model.Producto;
import cl.duoc.catalogo.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private static final Logger log = LoggerFactory.getLogger(ProductoService.class);
    private final ProductoRepository repository;

    private ProductoResponseDTO toResponse(Producto producto) {
        return ProductoResponseDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .talla(producto.getTalla())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .build();
    }

    public ProductoResponseDTO crearProducto(ProductoRequestDTO dto) {
        log.info("Creando producto: {}", dto.getNombre());
        Producto producto = Producto.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .talla(dto.getTalla())
                .precio(dto.getPrecio())
                .stock(dto.getStock())
                .build();
        Producto guardado = repository.saveAndFlush(producto);
        log.info("Producto creado con ID: {}", guardado.getId());
        return toResponse(guardado);
    }

    public List<ProductoResponseDTO> obtenerTodos() {
        log.info("Obteniendo todos los productos");
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProductoResponseDTO obtenerPorId(long id) {
        log.info("Buscando producto con ID: {}", id);
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        return toResponse(producto);
    }

    public List<ProductoResponseDTO> obtenerPorTalla(String talla) {
        log.info("Buscando productos con talla: {}", talla);
        return repository.findByTalla(talla)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ProductoResponseDTO> buscarPorNombre(String nombre) {
        log.info("Buscando productos con nombre: {}", nombre);
        return repository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProductoResponseDTO actualizarProducto(long id, ProductoRequestDTO dto) {
        log.info("Actualizando producto ID: {}", id);
        Producto existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setTalla(dto.getTalla());
        existente.setPrecio(dto.getPrecio());
        existente.setStock(dto.getStock());
        return toResponse(repository.saveAndFlush(existente));
    }

    public void eliminarProducto(long id) {
        log.info("Eliminando producto con ID: {}", id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }
}