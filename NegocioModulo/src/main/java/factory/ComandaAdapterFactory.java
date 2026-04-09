/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import DTOs.ComandaDTO;
import DTOs.ComandaProductoDTO;
import DTOs.EstadoComandaDTO;
import adaptadores.ComandaProductosAdapter;
import adaptadores.MesaAdapter;
import adaptadores.MeseroAdapter;
import entidades.Comanda;
import entidades.ComandaProducto;
import entidades.EstadoComanda;
import excepciones.NegocioException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Dayanara Peralta G
 */
public class ComandaAdapterFactory {
    private MesaAdapter mesaAdapter;
    
    public static ComandaDTO entidadADTO(Comanda comanda){
        if(comanda == null){
            return null;
        }
        ComandaDTO dto = new ComandaDTO();
        dto.setId(comanda.getId());
        dto.setFolio(comanda.getFolio());
        dto.setFechaHora(comanda.getFechaHora());
        dto.setEstado(EstadoComandaDTO.ABIERTA);
        dto.setTotalComanda(comanda.getTotalComanda());
        
        if(comanda.getCliente() != null){
            dto.setCliente(ClienteAdapterFactory.entidadADTO(comanda.getCliente()));
        }
        
        if(comanda.getMesa() != null){
            dto.setMesa(MesaAdapter.entidadADTO(comanda.getMesa()));
        }
        
        if(comanda.getMesero() != null){
            dto.setMesero(MeseroAdapter.entidadADTO(comanda.getMesero()));
        }
        
        if(comanda.getComandaProductos() != null){
            List<ComandaProductoDTO> detallesDTO = comanda.getComandaProductos()
                    .stream().map(ComandaProductosAdapter::entidadADTO)
                    .collect(Collectors.toList());
            dto.setComandaProductos(detallesDTO);
        }
        return dto;
    }
    
    public static Comanda dtoAEntidad(ComandaDTO dto) throws NegocioException{
        if(dto == null){
            return null;
        }
        Comanda comanda = new Comanda();
        comanda.setId(dto.getId());
        comanda.setFolio(dto.getFolio());
        comanda.setFechaHora(dto.getFechaHora());
        comanda.setTotalComanda(dto.getTotalComanda());
        
        if(dto.getEstado() != null){
            comanda.setEstado(EstadoComanda.valueOf(dto.getEstado().name()));
        }
        
        if(dto.getCliente() != null){
            comanda.setCliente(ClienteAdapterFactory.dtoAEntidad(dto.getCliente()));
        }
        
        if(dto.getComandaProductos() != null){
            List<ComandaProducto> detalles = new ArrayList<>();
            for(ComandaProductoDTO cpDTO : dto.getComandaProductos()){
                ComandaProducto cp = ComandaProductosAdapter.dtoAEntidad(cpDTO);
                cp.setComanda(comanda);
                detalles.add(cp);
            }
            comanda.setComandaProductos(detalles);
        }
        return comanda;
    }
}
