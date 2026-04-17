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
 * Clase Cliente que crea la tabla clientes en la base de datos
 * @author Tungs
 */
@Entity
@Table (name = "clientes")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_cliente", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("NORMAL")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_cliente")
    protected Long id;

    @Column(name = "nombres", length = 200, nullable = false)
    private String nombres;
    
    @Column(name = "apellido_paterno", length = 100, nullable= false)
    private String apellidoPaterno;
    
    @Column(name = "apellido_materno", length = 100)
    private String apellidoMaterno;
    
    @Column (name = "telefono", nullable= false, length=255)
    private String telefono;
    
    @Column (name = "correo", length = 100)
    private String correo;
    
    @Column (name= "fecha_registro")
    private LocalDate fechaRegistro;

    /**
     * Constructor que inicializa todos los atributos de la clase Cliente
     * @param nombres
     * @param apellidoPaterno
     * @param apellidoMaterno
     * @param telefono
     * @param correo
     * @param fechaRegistro
     */
    public Cliente(String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String correo, LocalDate fechaRegistro) {
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Constructor vacio.
     */
    public Cliente() {
    }

    /**
     * Metodo que devuelve el nombre o los nombres del cliente
     * @return nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Metodo que modifica el nombre o los nombre del cliente
     * @param nombres el nombre o los nombres nuevo
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Metodo que devuelve el apellido paterno del cliente
     * @return apellido paterno
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Metodo que modifica el apellido paterno del cliente
     * @param apellidoPaterno el nuevo apellido
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Metodo que devuelve el apellido materno del cliente
     * @return apellido materno
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Metodo que modifica el apellido materno del cliente
     * @param apellidoMaterno el nuevo apellido
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * Metodo que devuelve el telefono del cliente
     * @return telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Metodo que modifica el telefono del cliente
     * @param telefono el nuevo telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Metodo que devuelve el correo del cliente
     * @return correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Metodo que modifica el correo del cliente
     * @param correo el correo nuevo
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Metodo que devuelve la fecha de registro del cliente
     * @return fecha de registro
     */
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Metodo que modifica el telefono del cliente
     * @param fechaRegistro la nueva fecha de registro
     */
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    /**
     * Metodo que devuelve el id del cliente
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Metodo que modifica el id del cliente
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "com.mycompany.persistenciamodulo.Cliente[ id=" + id + " ]";
    }
    
}
