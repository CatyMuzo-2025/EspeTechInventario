package com.espe.espetech.repository;

import com.espe.espetech.entity.HardwareEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HardwareRepository extends JpaRepository<HardwareEntity, Long> {


    List<HardwareEntity> findByEstadoAndFechaCompraGreaterThanEqual(
            String estado, LocalDate fechaDesde);
}

