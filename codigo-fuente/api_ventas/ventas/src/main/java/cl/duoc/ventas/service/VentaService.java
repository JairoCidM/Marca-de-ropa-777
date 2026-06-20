package cl.duoc.ventas.service;

import cl.duoc.ventas.dto.VentaRequestDTO;
import cl.duoc.ventas.dto.VentaResponseDTO;
import cl.duoc.ventas.model.Venta;
import cl.duoc.ventas.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VentaService {

    private static final Logger log = LoggerFactory.getLogger(VentaService.class);
    private final VentaRepository ventaRepository;

    private VentaResponseDTO toResponse(Venta venta) {
        return VentaResponseDTO.builder()
                .id(venta.getId())
                .usuarioId(venta.getUsuarioId())
                .ordenId(venta.getOrdenId())
                .total(venta.getTotal())
                .cantidadProductos(venta.getCantidadProductos())
                .estado(venta.getEstado())
                .fechaVenta(venta.getFechaVenta())
                .build();
    }

    public VentaResponseDTO crearVenta(VentaRequestDTO dto) {
        log.info("Creando venta para usuario ID: {}", dto.getUsuarioId());
        Venta venta = Venta.builder()
                .usuarioId(dto.getUsuarioId())
                .ordenId(dto.getOrdenId())
                .total(dto.getTotal())
                .cantidadProductos(dto.getCantidadProductos())
                .estado("COMPLETADA")
                .build();
        Venta guardada = ventaRepository.saveAndFlush(venta);
        log.info("Venta creada con ID: {}", guardada.getId());
        return toResponse(guardada);
    }

    public List<VentaResponseDTO> obtenerTodas() {
        log.info("Obteniendo todas las ventas");
        return ventaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public VentaResponseDTO obtenerPorId(long id) {
        log.info("Buscando venta con ID: {}", id);
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
        return toResponse(venta);
    }

    public List<VentaResponseDTO> obtenerPorUsuario(long usuarioId) {
        log.info("Buscando ventas del usuario ID: {}", usuarioId);
        return ventaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<VentaResponseDTO> obtenerPorEstado(String estado) {
        log.info("Buscando ventas con estado: {}", estado);
        return ventaRepository.findByEstado(estado)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public VentaResponseDTO actualizarVenta(long id, VentaRequestDTO dto) {
        log.info("Actualizando venta ID: {}", id);
        Venta existente = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
        existente.setTotal(dto.getTotal());
        existente.setCantidadProductos(dto.getCantidadProductos());
        existente.setEstado("COMPLETADA");
        return toResponse(ventaRepository.saveAndFlush(existente));
    }

    public void eliminarVenta(long id) {
        log.info("Eliminando venta con ID: {}", id);
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("Venta no encontrada con ID: " + id);
        }
        ventaRepository.deleteById(id);
    }
}