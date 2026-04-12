/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import DTOs.MesaDTO;
import entidades.EstadoMesa;
import entidades.Mesa;

/**
 *
 * @author Dayanara Peralta G
 */
public class MesaAdapter {
    
    public static MesaDTO entidadADTO(Mesa mesa){
        if(mesa == null){
            return null;
        }
        EstadoMesa estado = mesa.getEstado();
        MesaDTO dto = new MesaDTO();
        dto.setId(mesa.getId());
        dto.setNuemro(mesa.getNumero());
        if(mesa.getEstado() != null){
            dto.setEstado(mesa.getEstado().name());
        }
        return dto;
    }
    
    public static Mesa dtoAEntidad(MesaDTO dto){
        if(dto == null){
            return null;
        }
        Mesa mesa = new Mesa();
        mesa.setId(dto.getId());
        mesa.setNumero(dto.getNuemro());
        if(dto.getEstado() != null){
            mesa.setEstado(EstadoMesa.valueOf(dto.getEstado().toUpperCase()));
        }
        return mesa;
    }
}
