package cl.duoc.favoritos.service;

import cl.duoc.favoritos.dto.FavoritoRequestDTO;
import cl.duoc.favoritos.dto.FavoritoResponseDTO;
import cl.duoc.favoritos.model.Favorito;
import cl.duoc.favoritos.repository.FavoritoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoritoService {

    private static final Logger log = LoggerFactory.getLogger(FavoritoService.class);
    private final FavoritoRepository repository;

    private FavoritoResponseDTO toResponse(Favorito favorito) {
        return FavoritoResponseDTO.builder()
                .id(favorito.getId())
                .usuarioId(favorito.getUsuarioId())
                .productoId(favorito.getProductoId())
                .fechaAgregado(favorito.getFechaAgregado())
                .build();
    }

    public FavoritoResponseDTO agregarFavorito(FavoritoRequestDTO dto) {
        log.info("Agregando favorito para usuario ID: {} producto ID: {}", dto.getUsuarioId(), dto.getProductoId());
        if (repository.existsByUsuarioIdAndProductoId(dto.getUsuarioId(), dto.getProductoId())) {
            throw new RuntimeException("El producto ya está en favoritos de este usuario");
        }
        Favorito favorito = Favorito.builder()
                .usuarioId(dto.getUsuarioId())
                .productoId(dto.getProductoId())
                .build();
        Favorito guardado = repository.saveAndFlush(favorito);
        log.info("Favorito creado con ID: {}", guardado.getId());
        return toResponse(guardado);
    }

    public List<FavoritoResponseDTO> obtenerTodos() {
        log.info("Obteniendo todos los favoritos");
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<FavoritoResponseDTO> obtenerPorUsuario(long usuarioId) {
        log.info("Obteniendo favoritos del usuario ID: {}", usuarioId);
        return repository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public FavoritoResponseDTO obtenerPorId(long id) {
        log.info("Buscando favorito con ID: {}", id);
        Favorito favorito = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorito no encontrado con ID: " + id));
        return toResponse(favorito);
    }

    public void eliminarFavorito(long id) {
        log.info("Eliminando favorito con ID: {}", id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("Favorito no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

    public void eliminarPorUsuarioYProducto(long usuarioId, long productoId) {
        log.info("Eliminando favorito usuario ID: {} producto ID: {}", usuarioId, productoId);
        if (!repository.existsByUsuarioIdAndProductoId(usuarioId, productoId)) {
            throw new RuntimeException("El producto no está en favoritos de este usuario");
        }
        repository.deleteByUsuarioIdAndProductoId(usuarioId, productoId);
    }
}