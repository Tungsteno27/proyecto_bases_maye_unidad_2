/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Tungs
 */
@Entity
@Table(name = "meseros")
@PrimaryKeyJoinColumn(name = "id_empleado")
@DiscriminatorValue("MESERO")
public class Mesero extends Empleado implements Serializable {
    
    
    //Mesero (POR AHORA) solo tiene una lista con todas sus comandas, porque el es el único que toma comandas
    @OneToMany(mappedBy = "mesero")
    private List<Comanda> comandas;

    /**
     * Constructor que recibe como parametro todos los atributos de la clase padre y de esta clase
     * @param comandas
     * @param id
     * @param nombres
     * @param apellidoPaterno
     * @param apellidoMaterno
     * @param fechaRegistro
     */
    public Mesero(List<Comanda> comandas, Long id, String nombres, String apellidoPaterno, String apellidoMaterno, LocalDate fechaRegistro) {
        super(id, nombres, apellidoPaterno, apellidoMaterno, fechaRegistro);
        this.comandas = comandas;
    }

    /**
     * Constructor que recibe como parametro los atributos de esta clase
     * @param comandas
     */
    public Mesero(List<Comanda> comandas) {
        this.comandas = comandas;
    }

    /**
     * Constructor vacio.
     */
    public Mesero() {
        super();
    }

    /**
     * Metodo que devuelve el id de un mesero
     * @return comandas
     */
    public List<Comanda> getComandas() {
        return comandas;
    }

    /**
     * Metodo que modifica el id de un mesero
     * @param comandas la nueva comanda
     */
    public void setComandas(List<Comanda> comandas) {
        this.comandas = comandas;
    }
    
    
    

    
}
