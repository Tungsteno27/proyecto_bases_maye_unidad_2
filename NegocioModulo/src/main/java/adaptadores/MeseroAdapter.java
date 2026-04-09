/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import DTOs.MeseroDTO;
import entidades.Mesero;

/**
 *
 * @author Dayanara Peralta G
 */
public class MeseroAdapter {
    public static MeseroDTO entidadADTO(Mesero mesero){
        if(mesero == null){
            return null;
        }
        MeseroDTO dto = new MeseroDTO();
        dto.setId(mesero.getId());
        dto.setNombres(mesero.getNombres());
        dto.setApellidoPaterno(mesero.getApellidoPaterno());
        dto.setApellidoMaterno(mesero.getApellidoMaterno());
        dto.setFechaRegistro(mesero.getFechaRegistro());
        return dto;
    }
    
    public static Mesero dtoAEntidad(MeseroDTO dto){
        if(dto == null){
            return null;
        }
        Mesero mesero = new Mesero();
        mesero.setId(dto.getId());
        mesero.setNombres(dto.getNombres());
        mesero.setApellidoPaterno(dto.getApellidoPaterno());
        mesero.setApellidoMaterno(dto.getApellidoMaterno());
        mesero.setFechaRegistro(dto.getFechaRegistro());
        return mesero;
    }
}
