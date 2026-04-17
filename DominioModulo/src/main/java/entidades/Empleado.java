/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 *
 * @author Tungs
 */
@Entity
@Table(name = "empleados")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "rol", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("EMPLEADO")
public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Long id;

    @Column(name = "nombres", length = 200)
    private String nombres;

    @Column(name = "apellido_paterno", length = 100)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", length = 100)
    private String apellidoMaterno;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    /**
     * Constructor que recibe como parámetros todos los atributos de la clase
     * @param id
     * @param nombres
     * @param apellidoPaterno
     * @param apellidoMaterno
     * @param fechaRegistro
     */
    public Empleado(Long id, String nombres, String apellidoPaterno, String apellidoMaterno, LocalDate fechaRegistro) {
        this.id = id;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Constructor vacio
     */
    public Empleado() {
    }

    /**
     * Metodo que devuelve el id del empleado
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Metodo que modifica el id del empleado
     * @param id nuevo id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Metodo que devuelve el nombre o los nombres del empleado
     * @return nombre
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Metodo que modifica el nombre o los nombres del empleado
     * @param nombres nuevo nombre o nombres
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Metodo que devuelve el apellido paterno del empleado
     * @return apellido paterno
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Metodo que modifica el apellido paterno del empleado
     * @param apellidoPaterno
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Metodo que devuelve el apellido materno del empleado
     * @return apellido materno
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Metodo que modifica el apellido materno del empleado
     * @param apellidoMaterno nuevo apellido materno
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * Metodo que devuelve la fecha de registro del empleado
     * @return fecha de registro
     */
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Metodo que modifica la fecha de registro del empleado
     * @param fechaRegistro
     */
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
