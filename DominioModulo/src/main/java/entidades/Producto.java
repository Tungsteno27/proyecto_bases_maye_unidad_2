/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Tungs
 */
@Entity
@Table(name = "productos")
public class Producto implements Serializable {
     private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;

    @Column(name = "nombre", length = 100, nullable = false, unique=true)
    private String nombre;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "tipo", length = 50)
    private String tipo;
    
    @Column(name = "descripcion", length = 255)
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoProducto estado;
    
    @Column(name = "imagen_url", length = 255)
    private String imagenUrl;

    @OneToMany(mappedBy = "producto", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<ProductoIngrediente> ingredientes;

    /**
     * Constructor que inicializa todos los atributos de la clase
     * @param id
     * @param nombre
     * @param precio
     * @param tipo
     * @param descripcion
     * @param estado
     * @param imagenUrl
     * @param ingredientes
     */
    public Producto(Long id, String nombre, Double precio, String tipo, String descripcion, EstadoProducto estado, String imagenUrl, List<ProductoIngrediente> ingredientes) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.imagenUrl = imagenUrl;
        this.ingredientes = ingredientes;
    }

    /**
     * Constructor vacio.
     */
    public Producto() {
    }

    /**
     * Metodo que devuelve el id de un producto
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Metodo que modifica el id de un producto
     * @param id el nuevo id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Metodo que devuelve el nombre de un producto
     * @return nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo que modifica el nombre de un producto
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo que devuelve el precio de un producto
     * @return precio de un producto
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * Metodo que modifica el precio de un producto
     * @param precio
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * Metodo que devuelve el tipo de un producto
     * @return tipo de producto
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Metodo que modifica el tipo de un producto
     * @param tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoProducto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public List<ProductoIngrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<ProductoIngrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    
    
    
}
