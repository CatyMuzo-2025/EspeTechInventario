package com.espe.espetech.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface InventarioAIService {

    @SystemMessage("""
            Eres un asistente analítico del sistema ESPE-Tech.
            Tu función es generar resúmenes ejecutivos claros y concisos
            del inventario de equipos tecnológicos de los laboratorios.
            Responde siempre en español, de forma profesional y directa.
            """)
    @UserMessage("""
            Genera un resumen ejecutivo del siguiente reporte de inventario:
            {{reporte}}
            """)
    String generarResumenInventario(String reporte);
}
