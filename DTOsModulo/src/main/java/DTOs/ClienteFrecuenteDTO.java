/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.time.LocalDate;

/**
 *
 * @author Tungs
 */
public class ClienteFrecuenteDTO extends ClienteDTO {

private Integer totalVisitas;
    private Double totalGastado;
    private Integer puntos;

    public ClienteFrecuenteDTO() {
        super();
    }

    public ClienteFrecuenteDTO(Long id, String nombres, String apellidoPaterno, String apellidoMaterno,
                               String telefono, String correo, LocalDate fechaRegistro,
                               Integer totalVisitas, Double totalGastado, Integer puntos) {
        super(id, nombres, apellidoPaterno, apellidoMaterno, telefono, correo, fechaRegistro);
        this.totalVisitas = totalVisitas;
        this.totalGastado = totalGastado;
        this.puntos = puntos;
    }

    public Integer getTotalVisitas() {
        return totalVisitas;
    }

    public void setTotalVisitas(Integer totalVisitas) {
        this.totalVisitas = totalVisitas;
    }

    public Double getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(Double totalGastado) {
        this.totalGastado = totalGastado;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "ClienteFrecuenteDTO{" + "totalVisitas=" + totalVisitas + ", totalGastado=" + totalGastado + ", puntos=" + puntos + '}';
    }
    
    
}

    

    
   