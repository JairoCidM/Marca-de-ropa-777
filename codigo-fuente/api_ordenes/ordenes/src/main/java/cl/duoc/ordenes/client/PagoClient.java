package cl.duoc.ordenes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Map;

@FeignClient(name = "pagos", url = "http://api_pagos:8088")
public interface PagoClient {

    @GetMapping("/api/pagos/orden/{ordenId}")
    List<Map<String, Object>> obtenerPagosPorOrden(@PathVariable("ordenId") long ordenId);
}