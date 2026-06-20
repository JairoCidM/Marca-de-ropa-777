package cl.duoc.descuentos.service;

import cl.duoc.descuentos.dto.DescuentoRequestDTO;
import cl.duoc.descuentos.dto.DescuentoResponseDTO;
import cl.duoc.descuentos.model.Descuento;
import cl.duoc.descuentos.repository.DescuentoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DescuentoService {

    private static final Logger log = LoggerFactory.getLogger(DescuentoService.class);
    private final DescuentoRepository descuentoRepository;

    private DescuentoResponseDTO toResponse(Descuento descuento) {
        return DescuentoResponseDTO.builder()
                .id(descuento.getId())
                .codigo(descuento.getCodigo())
                .descripcion(descuento.getDescripcion())
                .porcentaje(descuento.getPorcentaje())
                .estado(descuento.getEstado())
                .fechaInicio(descuento.getFechaInicio())
                .fechaFin(descuento.getFechaFin())
                .fechaCreacion(descuento.getFechaCreacion())
                .build();
    }

    public DescuentoResponseDTO crearDescuento(DescuentoRequestDTO dto) {
        log.info("Creando descuento con código: {}", dto.getCodigo());
        if (descuentoRepository.existsByCodigo(dto.getCodigo())) {
            throw new RuntimeException("Ya existe un descuento con el código: " + dto.getCodigo());
        }
        Descuento descuento = Descuento.builder()
                .codigo(dto.getCodigo())
                .descripcion(dto.getDescripcion())
                .porcentaje(dto.getPorcentaje())
                .estado("ACTIVO")
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .build();
        Descuento guardado = descuentoRepository.saveAndFlush(descuento);
        log.info("Descuento creado con ID: {}", guardado.getId());
        return toResponse(guardado);
    }

    public List<DescuentoResponseDTO> obtenerTodos() {
        log.info("Obteniendo todos los descuentos");
        return descuentoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public DescuentoResponseDTO obtenerPorId(long id) {
        log.info("Buscando descuento con ID: {}", id);
        Descuento descuento = descuentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Descuento no encontrado con ID: " + id));
        return toResponse(descuento);
    }

    public DescuentoResponseDTO obtenerPorCodigo(String codigo) {
        log.info("Buscando descuento con código: {}", codigo);
        Descuento descuento = descuentoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Descuento no encontrado con código: " + codigo));
        return toResponse(descuento);
    }

    public List<DescuentoResponseDTO> obtenerPorEstado(String estado) {
        log.info("Buscando descuentos con estado: {}", estado);
        return descuentoRepository.findByEstado(estado)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public DescuentoResponseDTO actualizarDescuento(long id, DescuentoRequestDTO dto) {
        log.info("Actualizando descuento ID: {}", id);
        Descuento existente = descuentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Descuento no encontrado con ID: " + id));
        existente.setCodigo(dto.getCodigo());
        existente.setDescripcion(dto.getDescripcion());
        existente.setPorcentaje(dto.getPorcentaje());
        existente.setFechaInicio(dto.getFechaInicio());
        existente.setFechaFin(dto.getFechaFin());
        return toResponse(descuentoRepository.saveAndFlush(existente));
    }

    public void eliminarDescuento(long id) {
        log.info("Eliminando descuento con ID: {}", id);
        if (!descuentoRepository.existsById(id)) {
            throw new RuntimeException("Descuento no encontrado con ID: " + id);
        }
        descuentoRepository.deleteById(id);
    }
}