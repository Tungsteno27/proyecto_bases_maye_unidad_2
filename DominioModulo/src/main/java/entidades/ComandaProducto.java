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

    public ComandaProducto(Long id, Double cantidad, String comentario, Double totalProductos, Comanda comanda, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.comentario = comentario;
        this.totalProductos = totalProductos;
        this.comanda = comanda;
        this.producto = producto;
    }

    public ComandaProducto() {
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Double getTotalProductos() {
        return totalProductos;
    }

    public void setTotalProductos(Double totalProductos) {
        this.totalProductos = totalProductos;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public Producto getProducto() {
        return producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    
    
}
