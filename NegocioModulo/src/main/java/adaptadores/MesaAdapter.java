/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import DTOs.MesaDTO;
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
        MesaDTO dto = new MesaDTO();
        dto.setId(mesa.getId());
        dto.setNuemro(mesa.getNumero());
        dto.setEstado(mesa.getEstado());
        return dto;
    }
    
    public static Mesa dtoAEntidad(MesaDTO dto){
        if(dto == null){
            return null;
        }
        Mesa mesa = new Mesa();
        mesa.setId(dto.getId());
        mesa.setNumero(dto.getNuemro());
        mesa.setEstado(dto.getEstado());
        return mesa;
    }
}
