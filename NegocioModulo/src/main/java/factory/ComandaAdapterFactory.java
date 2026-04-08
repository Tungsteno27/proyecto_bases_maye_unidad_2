/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import DTOs.ComandaDTO;
import DTOs.EstadoComandaDTO;
import adaptadores.MesaAdapter;
import entidades.Comanda;

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
        dto.setFolio(comanda.getFolio());
        dto.setFechaHora(comanda.getFechaHora());
        dto.setEstado(EstadoComandaDTO.ABIERTA);
        dto.setTotalComanda(comanda.getTotalComanda());
        
        if(comanda.getMesa() != null){
            dto.setMesa(MesaAdapter.entidadADTO(comanda.getMesa()));
        }
        
        if(comanda.getCliente() != null){
            dto.setCliente(ClienteAdapterFactory.entidadADTO(comanda.getCliente()));
        }
        
        if(comanda.getComandaProductos() != null){
            List<ComandaProductosDTO> detallesDTO = comanda.getComandaProductos().stream().map(mapper)
        }
    }
}
