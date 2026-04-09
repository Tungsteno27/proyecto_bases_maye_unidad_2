/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author Dayanara Peralta G
 */
public class ProductoIngredienteDTO {
    private Long idIngrediente;
    private Double cantidad;
    private String nombreIngrediente;
    private String unidad;

    public ProductoIngredienteDTO() {
    }

    public ProductoIngredienteDTO(Long idIngrediente, Double cantidad, String nombreIngrediente, String unidad) {
        this.idIngrediente = idIngrediente;
        this.cantidad = cantidad;
        this.nombreIngrediente = nombreIngrediente;
        this.unidad = unidad;
    }

    public String getNombreIngrediente() {
        return nombreIngrediente;
    }

    public void setNombreIngrediente(String nombreIngrediente) {
        this.nombreIngrediente = nombreIngrediente;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
    
    public ProductoIngredienteDTO(Long idIngrediente, Double cantidad) {
        this.idIngrediente = idIngrediente;
        this.cantidad = cantidad;
    }

    public Long getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Long idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }
    
    
   
}
