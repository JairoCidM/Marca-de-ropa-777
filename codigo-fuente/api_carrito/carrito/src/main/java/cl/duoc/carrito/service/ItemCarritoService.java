package cl.duoc.carrito.service;

import cl.duoc.carrito.client.CatalogoClient;
import cl.duoc.carrito.client.RegistroClient;
import cl.duoc.carrito.dto.ItemCarritoRequestDTO;
import cl.duoc.carrito.dto.ItemCarritoResponseDTO;
import cl.duoc.carrito.model.ItemCarrito;
import cl.duoc.carrito.repository.ItemCarritoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemCarritoService {

    private static final Logger log = LoggerFactory.getLogger(ItemCarritoService.class);
    private final ItemCarritoRepository repository;
    
    // Inyectamos nuestros nuevos clientes Feign
    private final CatalogoClient catalogoClient;
    private final RegistroClient registroClient;

    private ItemCarritoResponseDTO toResponse(ItemCarrito item) {
        return ItemCarritoResponseDTO.builder()
                .id(item.getId())
                .usuarioId(item.getUsuarioId())
                .productoId(item.getProductoId())
                .cantidad(item.getCantidad())
                .precioUnitario(item.getPrecioUnitario())
                .subtotal(item.getCantidad() * item.getPrecioUnitario())
                .fechaAgregado(item.getFechaAgregado())
                .build();
    }

    public ItemCarritoResponseDTO agregarItem(ItemCarritoRequestDTO dto) {
        log.info("Iniciando validación para agregar producto ID: {} al carrito del usuario ID: {}", dto.getProductoId(), dto.getUsuarioId());

        // 1. VALIDAR QUE EL USUARIO EXISTA EN EL MICROSERVICIO DE REGISTRO
        try {
            registroClient.obtenerUsuarioPorId(dto.getUsuarioId());
        } catch (Exception e) {
            log.error("Fallo de validación: Usuario {} no existe", dto.getUsuarioId());
            throw new RuntimeException("Error: El usuario no está registrado en el sistema.");
        }

        // 2. VALIDAR QUE EL PRODUCTO EXISTA EN EL CATÁLOGO Y OBTENER SU PRECIO REAL
        Map<String, Object> productoExterno;
        try {
            productoExterno = catalogoClient.obtenerProductoPorId(dto.getProductoId());
        } catch (Exception e) {
            log.error("Fallo de validación: Producto {} no existe en catálogo", dto.getProductoId());
            throw new RuntimeException("Error: El producto indicado no existe en el Catálogo.");
        }

        if (repository.existsByUsuarioIdAndProductoId(dto.getUsuarioId(), dto.getProductoId())) {
            throw new RuntimeException("El producto ya está en el carrito de este usuario");
        }

        // 3. EXTRAER EL PRECIO DE FORMA SEGURA DIRECTO DEL CATALOGO
        Double precioReal = Double.valueOf(productoExterno.get("precio").toString());

        // 4. GUARDAR EN LA BASE DE DATOS DEL CARRITO
        ItemCarrito item = ItemCarrito.builder()
                .usuarioId(dto.getUsuarioId())
                .productoId(dto.getProductoId())
                .cantidad(dto.getCantidad())
                .precioUnitario(precioReal) 
                .build();

        ItemCarrito guardado = repository.save(item);
        log.info("Producto guardado exitosamente en el carrito con precio seguro de: ${}", precioReal);
        return toResponse(guardado);
    }

       
    public List<ItemCarritoResponseDTO> obtenerPorUsuario(long usuarioId) {
        log.info("Obteniendo carrito del usuario ID: {}", usuarioId);
        return repository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ItemCarritoResponseDTO obtenerPorId(long id) {
        log.info("Buscando item con ID: {}", id);
        ItemCarrito item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado con ID: " + id));
        return toResponse(item);
    }

    public ItemCarritoResponseDTO actualizarItem(long id, ItemCarritoRequestDTO dto) {
        log.info("Actualizando item ID: {}", id);
        ItemCarrito existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado con ID: " + id));
        existente.setCantidad(dto.getCantidad());
        existente.setPrecioUnitario(dto.getPrecioUnitario()); 
        return toResponse(repository.saveAndFlush(existente));
    }

    public void eliminarItem(long id) {
        log.info("Eliminando item con ID: {}", id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("Item no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

    public void vaciarCarrito(long usuarioId) {
        log.info("Vaciando carrito del usuario ID: {}", usuarioId);
        repository.deleteByUsuarioId(usuarioId);
    }
    
    public List<ItemCarritoResponseDTO> obtenerTodos() {
        log.info("Obteniendo todos los items del carrito");
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}