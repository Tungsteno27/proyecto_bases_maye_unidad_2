/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import DTOs.ClienteDTO;
import entidades.Cliente;

/**
 * Gestiona la transformación de objetos de cliente
 * @author Tungs
 */
public class ClienteAdapter {
    
    //ESTA CLASE NO ME SIRVE POR AHORA (:()) PUES A COMO SE MANEJA AHORA NO SE DEBE DE PERSISTIR UN CLIENTE PURO
    
    /**
     * Método que convierte una entidad cliente a su versión DTO
     * @param cliente la entidad cliente
     * @return un clienteDTO
     */
    public static ClienteDTO entidadADTO(Cliente cliente){
        if(cliente == null){
            return null;
        }
        String apellidoMaterno;
        String correoElectronico;
        if(cliente.getApellidoMaterno() !=null){
            apellidoMaterno=cliente.getApellidoMaterno();
        }else{
            apellidoMaterno= "N/A";
        }
        if(cliente.getCorreo() !=null){
            correoElectronico= cliente.getCorreo();
        }else{
            correoElectronico= "N/A";
        }
        return new ClienteDTO(cliente.getId(),cliente.getNombres(),cliente.getApellidoPaterno(),
                apellidoMaterno,cliente.getTelefono(),correoElectronico,cliente.getFechaRegistro());
    }
    
    /**
     * Método que convierte un dto de cliente a entidad
     * @param dto el dto de cliente
     * @return  una entidad cliente
     */
    public static Cliente DTOAEntidad(ClienteDTO dto){
        if(dto == null){
            return null;
        }
        Cliente cliente = new Cliente();
        
        if (dto.getId() != null) {
            cliente.setId(dto.getId());
        }

        cliente.setNombres(dto.getNombres());
        cliente.setApellidoPaterno(dto.getApellidoPaterno());
        cliente.setApellidoMaterno(dto.getApellidoMaterno());
        cliente.setTelefono(dto.getTelefono());
        cliente.setCorreo(dto.getCorreo());
        cliente.setFechaRegistro(dto.getFechaRegistro());
        
        return cliente;
    }
    
    
    
}
