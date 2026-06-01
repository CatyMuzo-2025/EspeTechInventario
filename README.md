# Universidad de las Fuerzas Armadas-ESPE
# Programación avanzada
# ESPE-Tech — Gestión de Inventario de Laboratorios

Sistema de gestión y análisis del inventario tecnológico de los laboratorios ESPE-Tech,
implementado el problema algorítmico mediante dos enfoques distintos:Paradigma Imperativo y
Paradigma Funcional/Declarativo.

---

## Tecnologías utilizadas

- **Java 21** + **Spring Boot 4.0.6**
- **Spring Data JPA** + **PostgreSQL 16**
- **LangChain4j 1.15.1-beta25** + **Groq** (llama-3.3-70b-versatile).Se generó una API KEY
- **Docker Compose** (infraestructura local)

---

## Estructura del Proyecto

```
src/main/java/com/espe/espetech/
├── EspeTechApplication.java         # Clase principal
├── DataSeeder.java                  # Llena los 10,000 registros al iniciar
├── entity/
│   └── HardwareEntity.java          # Entidad JPA (Capa de Persistencia)
├── dto/
│   └── CategoriaReporteDTO.java     # DTO del reporte analítico
├── repository/
│   └── HardwareRepository.java      # Repositorio Spring Data JPA
├── service/
│   ├── HardwareImperativoService.java  # Paradigma Imperativo
│   └── HardwareFuncionalService.java   # Paradigma Funcional 
├── controller/
│   └── HardwareController.java      # Capa Web REST
└── ai/
    └── InventarioAIService.java     # Capa AI con LangChain4j
```

---

## Ejecución

### 1. Prerrequisitos
- Java 21
- Docker Desktop
- IntelliJ IDEA

### 2. Levantar base de datos
```bash
docker compose up -d
```

### 3. Importante: Configurar API Key de Groq
En `application.properties`:
```properties
langchain4j.open-ai.chat-model.base-url=https://api.groq.com/openai/v1
langchain4j.open-ai.chat-model.api-key=TU_API_KEY
langchain4j.open-ai.chat-model.model-name=llama-3.3-70b-versatile
```

### 4. Ejecutar la aplicación
```bash
./gradlew bootRun
```
Al iniciar, el `DataSeeder` inserta automáticamente **10,000 registros** de prueba si la tabla está vacía.

---

## Endpoints REST

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | `/api/hardware/reporte/imperativo` | Reporte — Paradigma Imperativo |
| GET | `/api/hardware/reporte/funcional` | Reporte — Paradigma Funcional |
| GET | `/api/hardware/reporte/ai-resumen` | Reporte + Resumen IA (LangChain4j + Groq) |


## El Problema Algorítmico

Dado el conjunto de 10,000 registros de hardware, el sistema:

1. **Filtra** equipos comprados en los últimos 5 años con estado `ACTIVO`
2. **Agrupa** los equipos por categoría (Laptop, PC, Servidor)
3. **Calcula** el valor total y el promedio de precio por categoría
4. **Retorna** el equipo más caro de cada categoría

---

## Análisis Comparativo de Paradigmas

### Paradigma Imperativo (`HardwareImperativoService`)

Resuelve el problema usando estructuras de control tradicionales:
`for`, `if/else`, `HashMap` manual y acumuladores explícitos.


**Ventajas:**
- Fácil de depurar (cada paso es visible)
- Familiar para todos los niveles de experiencia
- Comportamiento predecible y directo

**Desventajas:**
- Menos conciso (~30% más líneas)
- Mayor probabilidad de errores en acumuladores manuales
- Difícil de paralelizar

---

### Paradigma Funcional / Declarativo (`HardwareFuncionalService`)

Resuelve el mismo problema usando Java Streams API, `Optional` y `Collectors`,
expresando *qué* se quiere lograr en lugar de *cómo* lograrlo.


**Ventajas:**
- Código más conciso (~30% menos líneas)
- Expresivo: el código describe la intención, no los pasos
- Fácilmente paralelizable con `parallelStream()`
- `Optional` elimina riesgo de `NullPointerException`
- Menor riesgo de errores en acumuladores

**Desventajas:**
- Curva de aprendizaje para perfiles sin experiencia en Streams
- Stack traces más difíciles de interpretar al depurar

---

## Conclusión

Se concluye que ambos paradigmas dan resultados idénticos y correctos. La elección siempre dependerá del contexto:

- Para el paradigma imperativo, es preferible cuando el equipo incluye perfiles junior o cuando la lógica requiere depuración paso a paso.
- Para el paradigma funcional, es preferible en equipos experimentados,donde se priorice mantenibilidad, concisión y procesamiento de grandes volúmenes de datos.
