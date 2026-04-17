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
import javax.persistence.Table;

/**
 *
 * @author Tungs
 */
@Entity
@Table(name = "ingredientes")
public class Ingrediente implements Serializable {
    private static final long serialVersionUID = 1L;
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ingrediente")
    private Long id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "stock")
    private Double stock;
    
    //Así se hacen los Enums :)
    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida")
    private UnidadMedida unidadMedida;

    /**
     * Constructor que recibe como parametro todos los atributos de la clase
     * @param id
     * @param nombre
     * @param stock
     * @param unidadMedida
     */
    public Ingrediente(Long id, String nombre, Double stock, UnidadMedida unidadMedida) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.unidadMedida = unidadMedida;
    }

    /**
     * Constructor vacio.
     */
    public Ingrediente() {
    }

    /**
     * Metodo que devuelve el id de un ingrediente
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Metodo que modifica el id de un ingrediente
     * @param id el nuevo id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Metodo que devuelve el nombre de un ingrediente
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo que modifica el nombre de un ingrediente
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo que devuelve el stock que hay de un ingrediente
     * @return stock
     */
    public Double getStock() {
        return stock;
    }

    /**
     * Metodo que modifica el stock que hay de un ingrediente
     * @param stock el nuevo stock
     */
    public void setStock(Double stock) {
        this.stock = stock;
    }

    /**
     * Metodo que devuelve la unidad de medida de un ingrediente
     * @return unidad de medida
     */
    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    /**
     * Metodo que modifica la unidad de medida de un ingrediente
     * @param unidadMedida nueva unidad de medida
     */
    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
}
