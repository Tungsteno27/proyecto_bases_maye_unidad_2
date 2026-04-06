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

    public ProductoIngredienteDTO() {
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
