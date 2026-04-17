/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Tungs
 */
@Entity
@Table(name = "comanda_productos")
public class ComandaProducto implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comanda_producto")
    private Long id;

    @Column(name = "cantidad", nullable = false)
    private Double cantidad;

    @Column(name = "comentario", length = 200)
    private String comentario;

    @Column(name = "total_productos")
    private Double totalProductos;

    @ManyToOne
    @JoinColumn(name = "id_comanda", nullable = false)
    private Comanda comanda;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    /**
     * Constructor que recibe como parametro todos los atributos
     * @param id
     * @param cantidad
     * @param comentario
     * @param totalProductos
     * @param comanda
     * @param producto
     */
    public ComandaProducto(Long id, Double cantidad, String comentario, Double totalProductos, Comanda comanda, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.comentario = comentario;
        this.totalProductos = totalProductos;
        this.comanda = comanda;
        this.producto = producto;
    }

    /**
     * Constructor vacio
     */
    public ComandaProducto() {
    }
    
    /**
     * Metodo que devuelve el id de la lista de productos
     * @return id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Metodo que modifica el id de la lista de productos
     * @param id el nuevo id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Metodo que devuelve la cantidad de producto de la lista de productos
     * @return cantidad
     */
    public Double getCantidad() {
        return cantidad;
    }

    /**
     * Metodo que modifica la cantidad de producto de la lista de productos
     * @param cantidad nueva cantidad
     */
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Metodo que devuelve el comentario del producto de la lista de productos
     * @return comnetario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Metodo que modifica el comentario del producto de la lista de productos
     * @param comentario
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Metodo que devuelve el total de los productos
     * @return total de los productos
     */
    public Double getTotalProductos() {
        return totalProductos;
    }

    /**
     * Metodo que modifica el total de productos
     * @param totalProductos el nuevo total de productos
     */
    public void setTotalProductos(Double totalProductos) {
        this.totalProductos = totalProductos;
    }

    /**
     * Metodo que devuelve la comanda asociada a la lista de productos
     * @return comanda
     */
    public Comanda getComanda() {
        return comanda;
    }

    /**
     * Metodo que modifica la comanda asociada a la lista de productos
     * @param comanda la nueva comanda
     */
    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    /**
     * Metodo que devuelve el el producto de la lista de productos
     * @return producto
     */
    public Producto getProducto() {
        return producto;
    }
    
    /**
     * Metodo que modifica el producto de la lista de productos
     * @param producto el nuevo producto
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    
    
}
