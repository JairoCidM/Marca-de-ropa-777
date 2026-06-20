package cl.duoc.ordenes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Map;

@FeignClient(name = "envios", url = "http://api_envios:8090")
public interface EnvioClient {

    @GetMapping("/api/envios/orden/{ordenId}")
    List<Map<String, Object>> obtenerEnviosPorOrden(@PathVariable("ordenId") long ordenId);
}