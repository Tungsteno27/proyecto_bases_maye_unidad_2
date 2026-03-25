/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Tungs
 */
/**
 * Clase que representa un cliente Frecuente en el sistema
 * @author Tungs
 */
@Entity
@DiscriminatorValue("FRECUENTE")
@Table(name = "clientes_frecuentes")
@PrimaryKeyJoinColumn(name = "id_cliente")
public class ClienteFrecuente extends Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    //Los atributos son transient porque no se persisten, se calculan en tiempo real.
    @Transient
    private Integer totalVisitas;

    @Transient
    private Double totalGastado;

    @Transient
    private Integer puntos;


    public ClienteFrecuente() {
        super();
    }

    public ClienteFrecuente(Integer totalVisitas, Double totalGastado, Integer puntos, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String correo, LocalDate fechaRegistro) {
        super(nombres, apellidoPaterno, apellidoMaterno, telefono, correo, fechaRegistro);
        this.totalVisitas = totalVisitas;
        this.totalGastado = totalGastado;
        this.puntos = puntos;
    }

    public ClienteFrecuente(Integer totalVisitas, Double totalGastado, Integer puntos) {
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

    
}
