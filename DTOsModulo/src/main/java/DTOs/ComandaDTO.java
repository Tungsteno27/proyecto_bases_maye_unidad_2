/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Dayanara Peralta G
 */
public class ComandaDTO {
    private Long id; //se genera solo
    private String folio; //se genera con el formato: OB-YYYYMMDD-XXX. Ej: OB-20260415-001
    private LocalDateTime fechaHora; //Lo agarra del sistema que no?
    private EstadoComandaDTO estado; //al crearla en automatico es ABIERTA, que no?
    private Double totalComanda; //Este no es un dato que se ingrese sino que se va generando en automatico conforme vas agregando cosas
    private ClienteDTO cliente; //este si es un dato que se ingrese, es opcional
    private MesaDTO mesa; //Si es un dato que se ingrese, es obligatorio
    private MeseroDTO mesero; //Se ingresa?
    private List<ComandaProductoDTO> comandaProductos; //Si se ingresa y por lo menos debe haber un producto

    public ComandaDTO() {
    }

    public ComandaDTO(Long id, String folio, LocalDateTime fechaHora, EstadoComandaDTO estado, Double totalComanda, ClienteDTO cliente, MesaDTO mesa, MeseroDTO mesero, List<ComandaProductoDTO> comandaProductos) {
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

    public EstadoComandaDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoComandaDTO estado) {
        this.estado = estado;
    }

    public Double getTotalComanda() {
        return totalComanda;
    }

    public void setTotalComanda(Double totalComanda) {
        this.totalComanda = totalComanda;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public MesaDTO getMesa() {
        return mesa;
    }

    public void setMesa(MesaDTO mesa) {
        this.mesa = mesa;
    }

    public MeseroDTO getMesero() {
        return mesero;
    }

    public void setMesero(MeseroDTO mesero) {
        this.mesero = mesero;
    }

    public List<ComandaProductoDTO> getComandaProductos() {
        return comandaProductos;
    }

    public void setComandaProductos(List<ComandaProductoDTO> comandaProductos) {
        this.comandaProductos = comandaProductos;
    }
    
}
