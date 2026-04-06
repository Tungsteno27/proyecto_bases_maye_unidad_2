/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.util.List;

/**
 *
 * @author Dayanara Peralta G
 */
public class ProductoDTO {
    private Long id;
    private String nombre;
    private Double precio;
    private String tipo;
    private String Descripcion;
    private String estado;
    private String imagenUrl;
    private List<ProductoIngredienteDTO> ingredientes;

    public ProductoDTO() {
    }

    public ProductoDTO(Long id, String nombre, Double precio, String tipo, String Descripcion, String estado, String imagenUrl, List<ProductoIngredienteDTO> ingredientes) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.Descripcion = Descripcion;
        this.estado = estado;
        this.imagenUrl = imagenUrl;
        this.ingredientes = ingredientes;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public List<ProductoIngredienteDTO> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<ProductoIngredienteDTO> ingredientes) {
        this.ingredientes = ingredientes;
    }
    
    
    
}
