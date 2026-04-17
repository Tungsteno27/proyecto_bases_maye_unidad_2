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

    /**
     * Constructor con todos los parametros de la clase padre y de la hija
     * @param totalVisitas
     * @param totalGastado
     * @param puntos
     * @param nombres
     * @param apellidoPaterno
     * @param apellidoMaterno
     * @param telefono
     * @param correo
     * @param fechaRegistro
     */
    public ClienteFrecuente(Integer totalVisitas, Double totalGastado, Integer puntos, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String correo, LocalDate fechaRegistro) {
        super(nombres, apellidoPaterno, apellidoMaterno, telefono, correo, fechaRegistro);
        this.totalVisitas = totalVisitas;
        this.totalGastado = totalGastado;
        this.puntos = puntos;
    }

    /**
     * Constructor con los parametros de la clase Cliente frecuente
     * @param totalVisitas
     * @param totalGastado
     * @param puntos
     */
    public ClienteFrecuente(Integer totalVisitas, Double totalGastado, Integer puntos) {
        this.totalVisitas = totalVisitas;
        this.totalGastado = totalGastado;
        this.puntos = puntos;
    }

    /**
     * Metodo que devuelve el total de visistas de un cliente frecuente
     * @return total de visistas
     */
    public Integer getTotalVisitas() {
        return totalVisitas;
    }

    /**
     * Metodo que modifica el total de visistas de un cliente frecuente
     * @param totalVisitas nuevo total de visitas
     */
    public void setTotalVisitas(Integer totalVisitas) {
        this.totalVisitas = totalVisitas;
    }

    /**
     * Metodo que devuelve el total gastado de un cliente frecuente
     * @return total gastado
     */
    public Double getTotalGastado() {
        return totalGastado;
    }

    /**
     * Metodo que modifica el total gastado de un cliente
     * @param totalGastado nuevo total gastado
     */
    public void setTotalGastado(Double totalGastado) {
        this.totalGastado = totalGastado;
    }

    /**
     * Metodo que devuelve los puntos de un cliente frecuente
     * @return puntos
     */
    public Integer getPuntos() {
        return puntos;
    }

    /**
     * Metodo que modifica los puntos de un cliente frecuente
     * @param puntos nuevos puntos
     */
    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    
}
