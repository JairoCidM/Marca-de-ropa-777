package cl.duoc.notificaciones.service;

import cl.duoc.notificaciones.model.Notificacion;
import cl.duoc.notificaciones.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository repository;

    public Notificacion guardarNotificacion(Notificacion notificacion) {
        return repository.save(notificacion);
    }

    public List<Notificacion> obtenerPorUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    public Optional<Notificacion> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public boolean existePorId(Long id) {
        return repository.existsById(id);
    }

    public void eliminarNotificacion(Long id) {
        repository.deleteById(id);
    }
}