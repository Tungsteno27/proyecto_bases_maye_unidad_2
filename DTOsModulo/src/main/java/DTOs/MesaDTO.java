/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author Dayanara Peralta G
 */
public class MesaDTO {
    private Long id;
    private Integer nuemro;
    private String estado;

    public MesaDTO() {
    }

    public MesaDTO(Long id, Integer nuemro, String estado) {
        this.id = id;
        this.nuemro = nuemro;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNuemro() {
        return nuemro;
    }

    public void setNuemro(Integer nuemro) {
        this.nuemro = nuemro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
