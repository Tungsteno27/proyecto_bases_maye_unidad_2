/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Tungs
 */
@Entity
@Table(name = "mesas")
public class Mesa implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesa")
    private Long id;

    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "estado", nullable = false)
    private EstadoMesa estado;

    /**
     * Constructor que recibe como paramentro todos los atributos de la clase
     * @param id
     * @param numero
     * @param estado
     */
    public Mesa(Long id, Integer numero, EstadoMesa estado) {
        this.id = id;
        this.numero = numero;
        this.estado = estado;
    }

    /**
     * Constructor vacio.
     */
    public Mesa() {
    }
    
    /**
     * Metodo que devuelve el id de una mesa
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Metodo que modifica el id de una mesa
     * @param id el nuevo id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Metodo que devuelve el numero de una mesa
     * @return el numero de la mesa
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * Metodo que modifica el id de una mesa
     * @param numero el nuevo numero de la mesa
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    /**
     * Metodo que devuelve el estado de una mesa
     * @return estado de la mesa
     */
    public EstadoMesa getEstado() {
        return estado;
    }

    /**
     * Metodo que modifica el estado de una mesa
     * @param estado el nuevo estado de la mesa
     */
    public void setEstado(EstadoMesa estado) {
        this.estado = estado;
    }
}
