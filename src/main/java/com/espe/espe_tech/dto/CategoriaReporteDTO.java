package com.espe.espetech.dto;

import java.math.BigDecimal;

public class CategoriaReporteDTO {

    private String categoria;
    private long   totalEquipos;
    private BigDecimal valorTotal;
    private BigDecimal promedioPrecio;
    private String equipoMasCaro;   // modelo del equipo más caro



    public CategoriaReporteDTO(String categoria, long totalEquipos,
                               BigDecimal valorTotal, BigDecimal promedioPrecio,
                               String equipoMasCaro) {
        this.categoria     = categoria;
        this.totalEquipos  = totalEquipos;
        this.valorTotal    = valorTotal;
        this.promedioPrecio = promedioPrecio;
        this.equipoMasCaro = equipoMasCaro;
    }

    // getters & setters

    public String getCategoria()                         { return categoria; }
    public void setCategoria(String categoria)           { this.categoria = categoria; }

    public long getTotalEquipos()                        { return totalEquipos; }
    public void setTotalEquipos(long totalEquipos)       { this.totalEquipos = totalEquipos; }

    public BigDecimal getValorTotal()                    { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal)     { this.valorTotal = valorTotal; }

    public BigDecimal getPromedioPrecio()                { return promedioPrecio; }
    public void setPromedioPrecio(BigDecimal p)          { this.promedioPrecio = p; }

    public String getEquipoMasCaro()                     { return equipoMasCaro; }
    public void setEquipoMasCaro(String equipoMasCaro)   { this.equipoMasCaro = equipoMasCaro; }
}
