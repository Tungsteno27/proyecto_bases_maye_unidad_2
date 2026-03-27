/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import DTOs.ClienteFrecuenteDTO;
import entidades.ClienteFrecuente;

/**
 * 
 * @author Tungs
 */
public class ClienteFrecuenteAdapter {
    
    /**
     * Método que convierte una entidad de cliente frecuente a DTO
     * @param cliente el cliente freceunte a convertir
     * @return el cliente frecuente convertido a clienteFrecuenteDTO
     */
    public static ClienteFrecuenteDTO entidadADTO(ClienteFrecuente cliente) {
        if (cliente == null) {
            return null;
        }
        return new ClienteFrecuenteDTO(
            cliente.getId(),                
            cliente.getNombres(),
            cliente.getApellidoPaterno(),
            cliente.getApellidoMaterno(),
            cliente.getTelefono(),
            cliente.getCorreo(),
            cliente.getFechaRegistro(),
            cliente.getTotalVisitas(),      
            cliente.getTotalGastado(),
            cliente.getPuntos()
        );
    }
    
    /**
     * Método que convierte de clienteFrecuenteDTO a la entidad
     * @param dto un dto de cliente frecuente
     * @return una entidad cliente
     */
    public static ClienteFrecuente dtoAEntidad(ClienteFrecuenteDTO dto) {
        if (dto == null) {
            return null;
        }

        ClienteFrecuente cliente = new ClienteFrecuente();
        if (dto.getId() != null) {
            cliente.setId(dto.getId());
        }
        cliente.setNombres(dto.getNombres());
        cliente.setApellidoPaterno(dto.getApellidoPaterno());
        cliente.setApellidoMaterno(dto.getApellidoMaterno());
        cliente.setTelefono(dto.getTelefono());
        cliente.setCorreo(dto.getCorreo());
        cliente.setFechaRegistro(dto.getFechaRegistro());
        cliente.setTotalVisitas(dto.getTotalVisitas());
        cliente.setTotalGastado(dto.getTotalGastado());
        cliente.setPuntos(dto.getPuntos());

        return cliente;
    }


}
