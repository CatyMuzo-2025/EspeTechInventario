package com.espe.espetech.controller;

import com.espe.espetech.ai.InventarioAIService;
import com.espe.espetech.dto.CategoriaReporteDTO;
import com.espe.espetech.service.HardwareFuncionalService;
import com.espe.espetech.service.HardwareImperativoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hardware")
public class HardwareController {

    private final HardwareImperativoService imperativoService;
    private final HardwareFuncionalService  funcionalService;
    private final InventarioAIService       aiService;

    public HardwareController(HardwareImperativoService imperativoService,
                              HardwareFuncionalService funcionalService,
                              InventarioAIService aiService) {
        this.imperativoService = imperativoService;
        this.funcionalService  = funcionalService;
        this.aiService         = aiService;
    }

    @GetMapping("/reporte/imperativo")
    public ResponseEntity<List<CategoriaReporteDTO>> reporteImperativo() {
        return ResponseEntity.ok(imperativoService.generarReporte());
    }

    @GetMapping("/reporte/funcional")
    public ResponseEntity<List<CategoriaReporteDTO>> reporteFuncional() {
        return ResponseEntity.ok(funcionalService.generarReporte());
    }

    @GetMapping("/reporte/ai-resumen")
    public ResponseEntity<Map<String, Object>> reporteAiResumen() {
        List<CategoriaReporteDTO> reporte = funcionalService.generarReporte();

        StringBuilder sb = new StringBuilder();
        for (CategoriaReporteDTO dto : reporte) {
            sb.append(String.format(
                    "Categoría: %s | Equipos: %d | Valor Total: $%.2f | Promedio: $%.2f | Más caro: %s%n",
                    dto.getCategoria(),
                    dto.getTotalEquipos(),
                    dto.getValorTotal(),
                    dto.getPromedioPrecio(),
                    dto.getEquipoMasCaro()
            ));
        }

        String resumenIA = aiService.generarResumenInventario(sb.toString());

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("reporte", reporte);
        respuesta.put("resumenIA", resumenIA);

        return ResponseEntity.ok(respuesta);
    }
}