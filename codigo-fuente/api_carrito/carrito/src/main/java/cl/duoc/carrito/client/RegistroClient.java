package cl.duoc.carrito.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

@FeignClient(name = "registro", url = "http://api_registro:8082")
public interface RegistroClient {

    @GetMapping("/api/registros/{id}")
    Map<String, Object> obtenerUsuarioPorId(@PathVariable("id") long id);
}