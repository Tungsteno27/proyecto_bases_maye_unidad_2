/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Dayanara Peralta G
 */
public class MeseroDTO extends EmpleadoDTO{
    private List<ComandaDTO> comandas;

    public MeseroDTO() {
    }

    public MeseroDTO(List<ComandaDTO> comandas) {
        this.comandas = comandas;
    }

    public MeseroDTO(List<ComandaDTO> comandas, Long id, String nombres, String apellidoPaterno, String apellidoMaterno, LocalDate fechaRegistro) {
        super(id, nombres, apellidoPaterno, apellidoMaterno, fechaRegistro);
        this.comandas = comandas;
    }

    public List<ComandaDTO> getComandas() {
        return comandas;
    }

    public void setComandas(List<ComandaDTO> comandas) {
        this.comandas = comandas;
    }
    
}
