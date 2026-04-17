/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Tungs
 */
@Entity
@Table(name = "comandas")
public class Comanda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comanda")
    private Long id;

    @Column(name = "folio", length = 50, nullable = false)
    private String folio;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoComanda estado;

    @Column(name = "total_comanda")
    private Double totalComanda;
    //Aquí cliente puede ser nulo porque se pueden realizar comandas sin estar registrado, o sea, el cliente general.
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "id_mesa", nullable = false)
    private Mesa mesa;
    
    @ManyToOne
    @JoinColumn(name = "id_mesero")
    private Mesero mesero;
    
    @OneToMany(mappedBy = "comanda", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<ComandaProducto> comandaProductos;

    /**
     * Constructor que recibe como parametros todos los parametros
     * @param id
     * @param folio
     * @param fechaHora
     * @param estado
     * @param totalComanda
     * @param cliente
     * @param mesa
     * @param mesero
     * @param comandaProductos
     */
    public Comanda(Long id, String folio, LocalDateTime fechaHora, EstadoComanda estado, Double totalComanda, Cliente cliente, Mesa mesa, Mesero mesero, List<ComandaProducto> comandaProductos) {
        this.id = id;
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.totalComanda = totalComanda;
        this.cliente = cliente;
        this.mesa = mesa;
        this.mesero = mesero;
        this.comandaProductos = comandaProductos;
    }

    /**
     * Constructor vacio.
     */
    public Comanda() {
    }

    /**
     * Metodo que devuelve el id de la comanda
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Metodo que modifica el id de la comanda
     * @param id nuevo id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Metodo que devuelve el folio de la comanda
     * @return folio
     */
    public String getFolio() {
        return folio;
    }

    /**
     * Metodo que modifica el folio de la comanda
     * @param folio nuevo folio
     */
    public void setFolio(String folio) {
        this.folio = folio;
    }

    /**
     * Metodo que devuelve la fecha y la hora de la comanda
     * @return fecha y hora de creacion de la comanda
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * Metodo que modifica la fecha y hora de la comanda
     * @param fechaHora fecha y hora nuevas
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * Metodo que devuelve el estado de la comanda
     * @return estado
     */
    public EstadoComanda getEstado() {
        return estado;
    }

    /** 
     * Metodo que modifica el estado de la comanda
     * @param estado nuevo estado
     */
    public void setEstado(EstadoComanda estado) {
        this.estado = estado;
    }

    /**
     * Metodo que devuelve el total de la comanda
     * @return total de la comanda
     */
    public Double getTotalComanda() {
        return totalComanda;
    }

    /**
     * Metodo que modifica el total de la comanda
     * @param totalComanda el nuevo total de la comanda
     */
    public void setTotalComanda(Double totalComanda) {
        this.totalComanda = totalComanda;
    }

    /**
     * Metodo que devuelve el cliente asignado a comanda
     * @return cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Metodo que modifica el cliente asignado de la comanda
     * @param cliente el nuevo cliente
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Metodo que devuelve la mesa asignada a la comanda
     * @return mesa
     */
    public Mesa getMesa() {
        return mesa;
    }

    /**
     * Metodo que modifica la mesa asignada a la comanda
     * @param mesa la nueva mesa
     */
    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    /**
     * Metodo que devuelve el mesero de la comanda
     * @return mesero
     */
    public Mesero getMesero() {
        return mesero;
    }

    /**
     * Metodo que modifica el mesero de la comanda
     * @param mesero el nuevo mesero
     */
    public void setMesero(Mesero mesero) {
        this.mesero = mesero;
    }

    /**
     * Metodo que devuelve la lista de productos de la comanda
     * @return la lista de productos
     */
    public List<ComandaProducto> getComandaProductos() {
        return comandaProductos;
    }

    /**
     * Metodo que modifica la lista de productos de la comanda
     * @param comandaProductos los nuevos productos
     */
    public void setComandaProductos(List<ComandaProducto> comandaProductos) {
        this.comandaProductos = comandaProductos;
    }
    
    
    
}
