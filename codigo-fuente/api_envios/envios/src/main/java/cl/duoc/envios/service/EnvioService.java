package cl.duoc.envios.service;

import cl.duoc.envios.dto.EnvioRequestDTO;
import cl.duoc.envios.dto.EnvioResponseDTO;
import cl.duoc.envios.model.Envio;
import cl.duoc.envios.repository.EnvioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnvioService {

    private static final Logger log = LoggerFactory.getLogger(EnvioService.class);
    private final EnvioRepository envioRepository;

    private EnvioResponseDTO toResponse(Envio envio) {
        return EnvioResponseDTO.builder()
                .id(envio.getId())
                .ordenId(envio.getOrdenId())
                .usuarioId(envio.getUsuarioId())
                .direccion(envio.getDireccion())
                .estado(envio.getEstado())
                .codigoSeguimiento(envio.getCodigoSeguimiento())
                .fechaEnvio(envio.getFechaEnvio())
                .fechaEntrega(envio.getFechaEntrega())
                .build();
    }

    public EnvioResponseDTO crearEnvio(EnvioRequestDTO dto) {
        log.info("Creando envío para orden ID: {}", dto.getOrdenId());
        Envio envio = Envio.builder()
                .ordenId(dto.getOrdenId())
                .usuarioId(dto.getUsuarioId())
                .direccion(dto.getDireccion())
                .estado("PREPARANDO")
                .build();
        Envio guardado = envioRepository.saveAndFlush(envio);
        log.info("Envío creado con código: {}", guardado.getCodigoSeguimiento());
        return toResponse(guardado);
    }

    public List<EnvioResponseDTO> obtenerTodos() {
        log.info("Obteniendo todos los envíos");
        return envioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public EnvioResponseDTO obtenerPorId(long id) {
        log.info("Buscando envío con ID: {}", id);
        Envio envio = envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado con ID: " + id));
        return toResponse(envio);
    }

    public EnvioResponseDTO obtenerPorCodigo(String codigo) {
        log.info("Buscando envío con código: {}", codigo);
        Envio envio = envioRepository.findByCodigoSeguimiento(codigo)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado con código: " + codigo));
        return toResponse(envio);
    }

    public List<EnvioResponseDTO> obtenerPorUsuario(long usuarioId) {
        log.info("Buscando envíos del usuario ID: {}", usuarioId);
        return envioRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<EnvioResponseDTO> obtenerPorEstado(String estado) {
        log.info("Buscando envíos con estado: {}", estado);
        return envioRepository.findByEstado(estado)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public EnvioResponseDTO actualizarEstado(long id, String estado) {
        log.info("Actualizando estado de envío ID: {} a {}", id, estado);
        Envio existente = envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado con ID: " + id));
        existente.setEstado(estado);
        if (estado.equals("ENTREGADO")) {
            existente.setFechaEntrega(LocalDateTime.now());
            log.info("Envío ID: {} marcado como entregado", id);
        }
        return toResponse(envioRepository.saveAndFlush(existente));
    }

    public void eliminarEnvio(long id) {
        log.info("Eliminando envío con ID: {}", id);
        if (!envioRepository.existsById(id)) {
            throw new RuntimeException("Envío no encontrado con ID: " + id);
        }
        envioRepository.deleteById(id);
    }

    public List<EnvioResponseDTO> obtenerPorOrden(long ordenId) {
        log.info("Buscando envíos de orden ID: {}", ordenId);
        return envioRepository.findByOrdenId(ordenId)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }
}