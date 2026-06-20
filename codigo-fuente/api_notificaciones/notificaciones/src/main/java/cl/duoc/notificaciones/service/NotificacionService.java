package cl.duoc.notificaciones.service;

import cl.duoc.notificaciones.dto.NotificacionRequestDTO;
import cl.duoc.notificaciones.dto.NotificacionResponseDTO;
import cl.duoc.notificaciones.model.Notificacion;
import cl.duoc.notificaciones.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository repository;

    public NotificacionResponseDTO crearNotificacion(NotificacionRequestDTO dto) {

        Notificacion notificacion = Notificacion.builder()
                .usuarioId(dto.getUsuarioId())
                .mensaje(dto.getMensaje())
                .tipo(dto.getTipo())
                .build();

        return convertirDTO(repository.save(notificacion));
    }

    public List<NotificacionResponseDTO> obtenerTodas() {
        return repository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public NotificacionResponseDTO obtenerPorId(Long id) {

        Notificacion notificacion = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notificación no encontrada"));

        return convertirDTO(notificacion);
    }

    public List<NotificacionResponseDTO> obtenerPorUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public List<NotificacionResponseDTO> obtenerNoLeidas(Long usuarioId) {
        return repository.findByUsuarioIdAndLeida(usuarioId, false)
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public NotificacionResponseDTO marcarComoLeida(Long id) {

        Notificacion notificacion = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notificación no encontrada"));

        notificacion.setLeida(true);

        return convertirDTO(repository.save(notificacion));
    }

    public void eliminarNotificacion(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Notificación no encontrada");
        }

        repository.deleteById(id);
    }

    private NotificacionResponseDTO convertirDTO(Notificacion notificacion) {

        return NotificacionResponseDTO.builder()
                .id(notificacion.getId())
                .usuarioId(notificacion.getUsuarioId())
                .mensaje(notificacion.getMensaje())
                .tipo(notificacion.getTipo())
                .leida(notificacion.isLeida())
                .fechaCreacion(notificacion.getFechaCreacion())
                .build();
    }
}