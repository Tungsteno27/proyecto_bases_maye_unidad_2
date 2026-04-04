/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.ClienteDAO;
import DAOs.ComandaDAO;
import DAOs.MesaDAO;
import DTOs.ComandaDTO;
import entidades.Cliente;
import entidades.Mesa;
import excepciones.NegocioException;

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
            
            ComandaDTO comanda = C
        }
    }
}
