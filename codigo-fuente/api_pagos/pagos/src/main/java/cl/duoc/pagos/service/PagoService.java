package cl.duoc.pagos.service;

import cl.duoc.pagos.dto.PagoRequestDTO;
import cl.duoc.pagos.dto.PagoResponseDTO;
import cl.duoc.pagos.model.Pago;
import cl.duoc.pagos.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoService {

    private static final Logger log = LoggerFactory.getLogger(PagoService.class);
    private final PagoRepository pagoRepository;

    private PagoResponseDTO toResponse(Pago pago) {
        return PagoResponseDTO.builder()
                .id(pago.getId())
                .ordenId(pago.getOrdenId())
                .usuarioId(pago.getUsuarioId())
                .monto(pago.getMonto())
                .metodo(pago.getMetodo())
                .estado(pago.getEstado())
                .fechaPago(pago.getFechaPago())
                .build();
    }

    public PagoResponseDTO crearPago(PagoRequestDTO dto) {
        log.info("Creando pago para orden ID: {}", dto.getOrdenId());
        Pago pago = Pago.builder()
                .ordenId(dto.getOrdenId())
                .usuarioId(dto.getUsuarioId())
                .monto(dto.getMonto())
                .metodo(dto.getMetodo())
                .estado("PENDIENTE")
                .build();
        Pago guardado = pagoRepository.saveAndFlush(pago);
        log.info("Pago creado con ID: {}", guardado.getId());
        return toResponse(guardado);
    }

    public List<PagoResponseDTO> obtenerTodos() {
        log.info("Obteniendo todos los pagos");
        return pagoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PagoResponseDTO obtenerPorId(long id) {
        log.info("Buscando pago con ID: {}", id);
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));
        return toResponse(pago);
    }

    public List<PagoResponseDTO> obtenerPorUsuario(long usuarioId) {
        log.info("Buscando pagos del usuario ID: {}", usuarioId);
        return pagoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<PagoResponseDTO> obtenerPorEstado(String estado) {
        log.info("Buscando pagos con estado: {}", estado);
        return pagoRepository.findByEstado(estado)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PagoResponseDTO actualizarEstado(long id, String estado) {
        log.info("Actualizando estado del pago ID: {} a {}", id, estado);
        Pago existente = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));
        existente.setEstado(estado);
        return toResponse(pagoRepository.saveAndFlush(existente));
    }

    public void eliminarPago(long id) {
        log.info("Eliminando pago con ID: {}", id);
        if (!pagoRepository.existsById(id)) {
            throw new RuntimeException("Pago no encontrado con ID: " + id);
        }
        pagoRepository.deleteById(id);
    }

    public List<PagoResponseDTO> obtenerPorOrden(long ordenId) {
        log.info("Buscando pagos de orden ID: {}", ordenId);
        return pagoRepository.findByOrdenId(ordenId)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }
}