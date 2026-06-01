package com.espe.espetech.service;

import com.espe.espetech.dto.CategoriaReporteDTO;
import com.espe.espetech.entity.HardwareEntity;
import com.espe.espetech.repository.HardwareRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class HardwareFuncionalService {

    private final HardwareRepository repo;

    public HardwareFuncionalService(HardwareRepository repo) {
        this.repo = repo;
    }


    public List<CategoriaReporteDTO> generarReporte() {

        LocalDate hace5Anios = LocalDate.now().minusYears(5);

        // PASOS 1-4 en una sola pipeline declarativa
        return repo.findByEstadoAndFechaCompraGreaterThanEqual("ACTIVO", hace5Anios)
                .stream()

                // PASO 1: filtro ya aplicado por la query del repositorio

                .filter(hw -> !hw.getFechaCompra().isBefore(hace5Anios))

                // PASO 2: agrupar por categoría
                .collect(Collectors.groupingBy(HardwareEntity::getCategoria))

                // PASO 3 y 4: transformar cada grupo en un DTO
                .entrySet().stream()
                .map(entry -> {
                    String categoria = entry.getKey();
                    List<HardwareEntity> equipos = entry.getValue();

                    // Valor total acumulado con reduce funcional
                    BigDecimal valorTotal = equipos.stream()
                            .map(HardwareEntity::getPrecio)
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
                            .setScale(2, RoundingMode.HALF_UP);

                    // Promedio usando DoubleSummaryStatistics
                    BigDecimal promedio = valorTotal.divide(
                            BigDecimal.valueOf(equipos.size()), 2, RoundingMode.HALF_UP);

                    // Equipo más caro con Optional (patrón funcional seguro)
                    String masCaro = equipos.stream()
                            .max(Comparator.comparing(HardwareEntity::getPrecio))
                            .map(HardwareEntity::getModelo)
                            .orElse("N/A");  // Optional maneja el caso vacío

                    return new CategoriaReporteDTO(
                            categoria,
                            equipos.size(),
                            valorTotal,
                            promedio,
                            masCaro
                    );
                })
                .sorted(Comparator.comparing(CategoriaReporteDTO::getCategoria))
                .collect(Collectors.toList());
    }
}
