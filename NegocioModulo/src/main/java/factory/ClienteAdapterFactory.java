/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import DTOs.ClienteDTO;
import DTOs.ClienteFrecuenteDTO;
import adaptadores.ClienteFrecuenteAdapter;
import entidades.Cliente;
import entidades.ClienteFrecuente;

/**
 *
 * @author Tungs
 */
public class ClienteAdapterFactory {
    public static ClienteDTO entidadADTO(Cliente cliente) {
        if (cliente instanceof ClienteFrecuente) {
            return ClienteFrecuenteAdapter.entidadADTO((ClienteFrecuente) cliente);
        }
        // Cuando existan otros tipos irán aquí
        throw new IllegalArgumentException("Tipo de cliente no soportado");
    }

    public static Cliente dtoAEntidad(ClienteDTO dto) {
        if (dto instanceof ClienteFrecuenteDTO) {
            return ClienteFrecuenteAdapter.dtoAEntidad((ClienteFrecuenteDTO) dto);
        }
        // Cuando existan otros tipos irán aquí
        throw new IllegalArgumentException("Tipo de DTO no soportado");
    }
}
