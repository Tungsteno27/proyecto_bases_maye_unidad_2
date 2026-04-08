/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import DTOs.ComandaProductoDTO;
import entidades.ComandaProducto;

/**
 *
 * @author Dayanara Peralta G
 */
public class ComandaProductosAdapter {
    
    public static ComandaProductoDTO entidadADTO(ComandaProducto cp){
        if(cp == null){
            return null; 
        }
        ComandaProductoDTO dto = new ComandaProductoDTO();
        dto.setCantidad(cp.getCantidad());
        dto.setComentario(cp.getComentario());
        dto.setTotalProductos(cp.getTotalProductos());
        return dto;
    }
}
