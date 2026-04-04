/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author Dayanara Peralta G
 */
public class ComandaProductoDTO {
    private Long id;
    private Double cantidad;
    private String comentario;
    private Double totalProductos;
    private ComandaDTO comanda;
    private ProductoDTO producto;

    public ComandaProductoDTO() {
    }

    public ComandaProductoDTO(Long id, Double cantidad, String comentario, Double totalProductos, ComandaDTO comanda, ProductoDTO producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.comentario = comentario;
        this.totalProductos = totalProductos;
        this.comanda = comanda;
        this.producto = producto;
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

    public ComandaDTO getComanda() {
        return comanda;
    }

    public void setComanda(ComandaDTO comanda) {
        this.comanda = comanda;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }
    
}
