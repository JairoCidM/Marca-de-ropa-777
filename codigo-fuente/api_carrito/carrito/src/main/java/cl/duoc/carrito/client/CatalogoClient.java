package cl.duoc.carrito.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;


@FeignClient(name = "catalogo", url = "http://api_catalogo:8081")
public interface CatalogoClient {

    @GetMapping("/api/catalogo/{id}")
    Map<String, Object> obtenerProductoPorId(@PathVariable("id") long id);
}