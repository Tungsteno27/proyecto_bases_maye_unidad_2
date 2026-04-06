/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author Dayanara Peralta G
 */
public class IngredienteDTO {
    private Long id;
    private String nombre;
    private Double stock;
    private String unidadMedida;

    public IngredienteDTO() {
    }

    public IngredienteDTO(Long id, String nombre, Double stock, String unidadMedida) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.unidadMedida = unidadMedida;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
    
}
