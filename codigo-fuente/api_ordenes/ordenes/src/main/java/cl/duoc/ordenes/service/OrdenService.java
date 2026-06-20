package cl.duoc.ordenes.service;

import cl.duoc.ordenes.client.EnvioClient;
import cl.duoc.ordenes.client.PagoClient;
import cl.duoc.ordenes.dto.OrdenRequestDTO;
import cl.duoc.ordenes.dto.OrdenResponseDTO;
import cl.duoc.ordenes.model.Orden;
import cl.duoc.ordenes.repository.OrdenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdenService {

    private static final Logger log = LoggerFactory.getLogger(OrdenService.class);
    private final OrdenRepository ordenRepository;
    private final PagoClient pagoClient;
    private final EnvioClient envioClient;

    private OrdenResponseDTO toResponse(Orden orden) {
        return OrdenResponseDTO.builder()
                .id(orden.getId())
                .usuarioId(orden.getUsuarioId())
                .total(orden.getTotal())
                .estado(orden.getEstado())
                .direccionEntrega(orden.getDireccionEntrega())
                .fechaOrden(orden.getFechaOrden())
                .build();
    }

    public OrdenResponseDTO crearOrden(OrdenRequestDTO dto) {
        log.info("Creando orden para usuario ID: {}", dto.getUsuarioId());
        Orden orden = Orden.builder()
                .usuarioId(dto.getUsuarioId())
                .total(dto.getTotal())
                .estado("PENDIENTE")
                .direccionEntrega(dto.getDireccionEntrega())
                .build();
        Orden guardada = ordenRepository.saveAndFlush(orden);
        log.info("Orden creada con ID: {}", guardada.getId());
        return toResponse(guardada);
    }

    public List<OrdenResponseDTO> obtenerTodas() {
        log.info("Obteniendo todas las órdenes");
        return ordenRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public OrdenResponseDTO obtenerPorId(long id) {
        log.info("Buscando orden con ID: {}", id);
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));
        return toResponse(orden);
    }

    public List<OrdenResponseDTO> obtenerPorUsuario(long usuarioId) {
        log.info("Buscando órdenes del usuario ID: {}", usuarioId);
        return ordenRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<OrdenResponseDTO> obtenerPorEstado(String estado) {
        log.info("Buscando órdenes con estado: {}", estado);
        return ordenRepository.findByEstado(estado)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public OrdenResponseDTO actualizarEstado(long id, String estado) {
        log.info("Actualizando estado de orden ID: {} a {}", id, estado);
        Orden existente = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));
        existente.setEstado(estado);
        return toResponse(ordenRepository.saveAndFlush(existente));
    }

    public OrdenResponseDTO actualizarOrden(long id, OrdenRequestDTO dto) {
        log.info("Actualizando orden ID: {}", id);
        Orden existente = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));
        existente.setTotal(dto.getTotal());
        existente.setDireccionEntrega(dto.getDireccionEntrega());
        return toResponse(ordenRepository.saveAndFlush(existente));
    }

    public Map<String, Object> obtenerOrdenCompleta(long id) {
        log.info("Obteniendo orden completa con pagos y envíos para ID: {}", id);
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));
        List<Map<String, Object>> pagos = pagoClient.obtenerPagosPorOrden(id);
        List<Map<String, Object>> envios = envioClient.obtenerEnviosPorOrden(id);
        log.info("Orden {} tiene {} pagos y {} envíos", id, pagos.size(), envios.size());
        return Map.of(
            "orden", toResponse(orden),
            "pagos", pagos,
            "envios", envios
        );
    }

    public void eliminarOrden(long id) {
        log.info("Eliminando orden con ID: {}", id);
        if (!ordenRepository.existsById(id)) {
            throw new RuntimeException("Orden no encontrada con ID: " + id);
        }
        ordenRepository.deleteById(id);
    }
}