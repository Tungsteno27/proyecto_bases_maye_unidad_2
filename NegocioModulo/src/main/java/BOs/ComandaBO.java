/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.ClienteDAO;
import DAOs.ComandaDAO;
import DAOs.MesaDAO;
import DTOs.ComandaDTO;
import DTOs.EstadoComandaDTO;
import entidades.Cliente;
import entidades.Mesa;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dayanara Peralta G
 */
public class ComandaBO {
    private ComandaDAO comandaDAO;
    private ClienteDAO clienteDAO;
    private MesaDAO mesaDAO;
    
    public ComandaBO(ComandaDAO comandaDAO){
        this.comandaDAO = comandaDAO;
    }
    
    public void registrar(ComandaDTO dto) throws NegocioException{
        try{
            if(dto.getCliente() != null && dto.getCliente().getId() != null){
                Long idCliente = dto.getCliente().getId();
                Cliente existe = clienteDAO.buscarCliente(idCliente);
                if(existe == null){
                    throw new NegocioException("El cliente con el id: " + idCliente);
                }
            }
            
            if(dto.getMesa() == null || dto.getMesa().getId() == null){
                throw new NegocioException("La mesa es obligatoria.");
            }
            
            if(dto.getComandaProductos() == null || dto.getComandaProductos().isEmpty()){
                throw new NegocioException("Ingrese por lo menos un producto.");
            }
            
            String folio = this.generarFolio();
            dto.setFolio(folio);
            
            dto.setFechaHora(LocalDateTime.now());
            dto.setEstado(EstadoComandaDTO.ABIERTA);
            
            
            
            ComandaDTO comanda = C
        } catch (PersistenciaException ex) {
            Logger.getLogger(ComandaBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    private String generarFolio() throws PersistenciaException{
        LocalDate hoy = LocalDate.now();
        String fecha = hoy.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        
        Long numero = comandaDAO.contarComandas(hoy) + 1;
        String num = String.format("%03d", numero);
        return "OB_"+ fecha + "-" + num;
    }
}
