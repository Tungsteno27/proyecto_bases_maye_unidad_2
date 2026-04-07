/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.time.LocalDate;
import java.util.Date;

/**
 * Esta clase representa un DTO con datos importantes para generar un reporte de clientes Frecuentes
 * @author Tungs
 */
public class ReporteClienteFrecuenteDTO {
    private String nombres;
    private int visitas;
    private double totalGastado;
    private Date ultimaComanda;

    public ReporteClienteFrecuenteDTO(String nombres, int visitas, double totalGastado, Date ultimaComanda) {
        this.nombres = nombres;
        this.visitas = visitas;
        this.totalGastado = totalGastado;
        this.ultimaComanda = ultimaComanda;
    }

    public ReporteClienteFrecuenteDTO() {
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public int getVisitas() {
        return visitas;
    }

    public void setVisitas(int visitas) {
        this.visitas = visitas;
    }

    public double getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(double totalGastado) {
        this.totalGastado = totalGastado;
    }

    public Date getUltimaComanda() {
        return ultimaComanda;
    }

    public void setUltimaComanda(Date ultimaComanda) {
        this.ultimaComanda = ultimaComanda;
    }
    
    

    
    
}
