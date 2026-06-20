package cl.duoc.registro.service;

import cl.duoc.registro.dto.RegistroRequestDTO;
import cl.duoc.registro.dto.RegistroResponseDTO;
import cl.duoc.registro.model.Registro;
import cl.duoc.registro.repository.RegistroRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistroService {

    private static final Logger log = LoggerFactory.getLogger(RegistroService.class);
    private final RegistroRepository registroRepository;

    private RegistroResponseDTO toResponse(Registro registro) {
        return RegistroResponseDTO.builder()
                .id(registro.getId())
                .nombre(registro.getNombre())
                .email(registro.getEmail())
                .rol(registro.getRol())
                .fechaRegistro(registro.getFechaRegistro())
                .build();
    }

    public RegistroResponseDTO crearRegistro(RegistroRequestDTO dto) {
        log.info("Creando registro para email: {}", dto.getEmail());
        if (registroRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está registrado: " + dto.getEmail());
        }
        Registro registro = Registro.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .rol(dto.getRol())
                .build();
        Registro guardado = registroRepository.saveAndFlush(registro);
        log.info("Registro creado con ID: {}", guardado.getId());
        return toResponse(guardado);
    }

    public List<RegistroResponseDTO> obtenerTodos() {
        log.info("Obteniendo todos los registros");
        return registroRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public RegistroResponseDTO obtenerPorId(long id) {
        log.info("Buscando registro con ID: {}", id);
        Registro registro = registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado con ID: " + id));
        return toResponse(registro);
    }

    public RegistroResponseDTO actualizarRegistro(long id, RegistroRequestDTO dto) {
        log.info("Actualizando registro ID: {}", id);
        Registro existente = registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado con ID: " + id));
        existente.setNombre(dto.getNombre());
        existente.setEmail(dto.getEmail());
        existente.setPassword(dto.getPassword());
        existente.setRol(dto.getRol());
        return toResponse(registroRepository.saveAndFlush(existente));
    }

    public void eliminarRegistro(long id) {
        log.info("Eliminando registro con ID: {}", id);
        if (!registroRepository.existsById(id)) {
            throw new RuntimeException("Registro no encontrado con ID: " + id);
        }
        registroRepository.deleteById(id);
    }
}