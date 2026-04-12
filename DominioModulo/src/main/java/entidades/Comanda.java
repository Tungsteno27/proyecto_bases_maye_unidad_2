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

    public Comanda() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoComanda getEstado() {
        return estado;
    }

    public void setEstado(EstadoComanda estado) {
        this.estado = estado;
    }

    public Double getTotalComanda() {
        return totalComanda;
    }

    public void setTotalComanda(Double totalComanda) {
        this.totalComanda = totalComanda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Mesero getMesero() {
        return mesero;
    }

    public void setMesero(Mesero mesero) {
        this.mesero = mesero;
    }

    public List<ComandaProducto> getComandaProductos() {
        return comandaProductos;
    }

    public void setComandaProductos(List<ComandaProducto> comandaProductos) {
        this.comandaProductos = comandaProductos;
    }
    
    
    
}
