package com.espe.espetech.service;

import com.espe.espetech.dto.CategoriaReporteDTO;
import com.espe.espetech.entity.HardwareEntity;
import com.espe.espetech.repository.HardwareRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@Service
public class HardwareImperativoService {

    private final HardwareRepository repo;

    public HardwareImperativoService(HardwareRepository repo) {
        this.repo = repo;
    }


    public List<CategoriaReporteDTO> generarReporte() {

        // PASO 1: Filtrar
        LocalDate hace5Anios = LocalDate.now().minusYears(5);
        List<HardwareEntity> todos = repo.findByEstadoAndFechaCompraGreaterThanEqual(
                "ACTIVO", hace5Anios);

        // PASO 2: Agrupar manualmente con HashMap
        Map<String, List<HardwareEntity>> porCategoria = new HashMap<>();

        for (HardwareEntity hw : todos) {
            String cat = hw.getCategoria();
            if (!porCategoria.containsKey(cat)) {
                porCategoria.put(cat, new ArrayList<>());
            }
            porCategoria.get(cat).add(hw);
        }

        // PASOS 3 y 4: Calcular métricas por categoría
        List<CategoriaReporteDTO> resultado = new ArrayList<>();

        for (Map.Entry<String, List<HardwareEntity>> entry : porCategoria.entrySet()) {
            String categoria = entry.getKey();
            List<HardwareEntity> equipos = entry.getValue();

            BigDecimal sumaTotal   = BigDecimal.ZERO;
            HardwareEntity masCaro = equipos.get(0);

            for (HardwareEntity hw : equipos) {
                // Acumulador manual de suma
                sumaTotal = sumaTotal.add(hw.getPrecio());

                // Comparación manual para el más caro
                if (hw.getPrecio().compareTo(masCaro.getPrecio()) > 0) {
                    masCaro = hw;
                }
            }

            long totalEquipos = equipos.size();
            BigDecimal promedio = sumaTotal.divide(
                    BigDecimal.valueOf(totalEquipos), 2, RoundingMode.HALF_UP);

            resultado.add(new CategoriaReporteDTO(
                    categoria,
                    totalEquipos,
                    sumaTotal.setScale(2, RoundingMode.HALF_UP),
                    promedio,
                    masCaro.getModelo()
            ));
        }

        // Ordenar alfabéticamente por categoría
        resultado.sort(Comparator.comparing(CategoriaReporteDTO::getCategoria));
        return resultado;
    }
}
