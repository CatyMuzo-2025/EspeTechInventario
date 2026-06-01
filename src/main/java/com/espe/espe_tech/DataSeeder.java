package com.espe.espetech;

import com.espe.espetech.entity.HardwareEntity;
import com.espe.espetech.repository.HardwareRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component
public class DataSeeder implements CommandLineRunner {

    private final HardwareRepository repo;

    public DataSeeder(HardwareRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        if (repo.count() > 0) {
            System.out.println("Base de datos ya contiene datos. Seeder omitido.");
            return;
        }

        System.out.println("Poblando base de datos con 10,000 registros...");

        String[] categorias = {"Laptop", "PC", "Servidor"};
        String[] estados    = {"ACTIVO", "DEBAJA"};
        String[] modelos    = {
                "Dell XPS 15", "HP EliteBook 840", "Lenovo ThinkPad X1",
                "Apple MacBook Pro", "Asus ZenBook 14", "Acer Swift 3",
                "Dell PowerEdge R740", "HP ProLiant DL380", "IBM System x3650",
                "HP Z4 Workstation", "Dell Precision 5820", "Lenovo ThinkStation P520"
        };

        Random random    = new Random(42); // seed fijo para reproducibilidad
        List<HardwareEntity> batch = new ArrayList<>(500);

        for (int i = 0; i < 10_000; i++) {
            String categoria = categorias[random.nextInt(categorias.length)];
            String estado    = random.nextDouble() < 0.7 ? "ACTIVO" : "DEBAJA"; // 70% activos
            String modelo    = modelos[random.nextInt(modelos.length)];

            // Fechas distribuidas entre hace 8 años y hoy
            long diasAtras = random.nextInt(365 * 8);
            LocalDate fecha = LocalDate.now().minusDays(diasAtras);

            // Precio entre $300 y $15,000
            double precioBase = 300 + random.nextDouble() * 14_700;
            BigDecimal precio = BigDecimal.valueOf(Math.round(precioBase * 100.0) / 100.0);

            batch.add(new HardwareEntity(modelo + " #" + (i + 1), categoria, precio, fecha, estado));

            // Guardar en lotes de 500 para eficiencia
            if (batch.size() == 500) {
                repo.saveAll(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            repo.saveAll(batch);
        }

        System.out.println(" 10,000 registros insertados correctamente.");
    }
}
