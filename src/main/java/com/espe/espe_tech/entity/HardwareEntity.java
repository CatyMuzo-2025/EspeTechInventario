package com.espe.espetech.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "hardware")
public class HardwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String categoria; // Laptop, PC, Servidor

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private LocalDate fechaCompra;

    @Column(nullable = false)
    private String estado; // ACTIVO, DEBAJA

    // constructores

    public HardwareEntity() {}

    public HardwareEntity(String modelo, String categoria, BigDecimal precio,
                          LocalDate fechaCompra, String estado) {
        this.modelo      = modelo;
        this.categoria   = categoria;
        this.precio      = precio;
        this.fechaCompra = fechaCompra;
        this.estado      = estado;
    }

    // getters & setters

    public Long getId()                    { return id; }
    public void setId(Long id)             { this.id = id; }

    public String getModelo()              { return modelo; }
    public void setModelo(String modelo)   { this.modelo = modelo; }

    public String getCategoria()                   { return categoria; }
    public void setCategoria(String categoria)     { this.categoria = categoria; }

    public BigDecimal getPrecio()                  { return precio; }
    public void setPrecio(BigDecimal precio)        { this.precio = precio; }

    public LocalDate getFechaCompra()              { return fechaCompra; }
    public void setFechaCompra(LocalDate f)        { this.fechaCompra = f; }

    public String getEstado()                      { return estado; }
    public void setEstado(String estado)           { this.estado = estado; }
}
