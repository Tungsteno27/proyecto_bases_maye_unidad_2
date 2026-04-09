/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import DTOs.ComandaProductoDTO;
import entidades.ComandaProducto;
import excepciones.NegocioException;

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
        dto.setId(cp.getId());
        dto.setCantidad(cp.getCantidad());
        dto.setComentario(cp.getComentario());
        dto.setTotalProductos(cp.getTotalProductos());
        if(cp.getProducto() != null){
            dto.setProducto(ProductoAdapter.productoADTO(cp.getProducto()));
        }      
        return dto;
    }
    
    public static ComandaProducto dtoAEntidad(ComandaProductoDTO dto) throws NegocioException{
        if(dto == null){
            return null;
        }
        ComandaProducto cp = new ComandaProducto();
        cp.setId(dto.getId());
        cp.setCantidad(dto.getCantidad());
        cp.setComentario(dto.getComentario());
        cp.setTotalProductos(dto.getTotalProductos());
        if(dto.getProducto() != null){
            cp.setProducto(ProductoAdapter.dtoAProducto(dto.getProducto()));
        }
        return cp;
    }
}