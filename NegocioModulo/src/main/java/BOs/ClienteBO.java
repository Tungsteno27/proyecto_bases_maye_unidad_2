/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.ClienteDAO;
import DTOs.ClienteDTO;
import DTOs.ClienteFrecuenteDTO;
import adaptadores.ClienteAdapter;
import adaptadores.ClienteFrecuenteAdapter;
import entidades.Cliente;
import entidades.ClienteFrecuente;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import factory.ClienteAdapterFactory;
import java.util.ArrayList;
import java.util.List;
import utilidadesBO.Encriptador;
import utilidadesBO.Validadores;

/**
 *
 * @author Tungs
 */
public class ClienteBO {
    
    
    
    //El obtener por id ya funciona, faltaría adaptar los otros métodos usando el factory

    private ClienteDAO clienteDAO;

    public ClienteBO(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public void registrar(ClienteDTO dto) throws NegocioException {
        try {
            if (dto.getNombres() == null || dto.getNombres().isBlank()) {
                throw new NegocioException("El nombre es obligatorio");
            }
            if (dto.getTelefono() == null || dto.getTelefono().isBlank()) {
                throw new NegocioException("El teléfono es obligatorio");
            }
            if (!Validadores.validarNombre(dto.getNombres())) {
                throw new NegocioException("El nombre no es válido");
            }
            if (!Validadores.validarTelefono(dto.getTelefono())) {
                throw new NegocioException("El teléfono debe tener 10 dígitos");
            }
            // Encripta el teléfono antes de persistir
            String telefonoEncriptado = Encriptador.encriptar(dto.getTelefono());
            dto.setTelefono(telefonoEncriptado);
            //La verdad hacer esto fue lo único que se me ocurrió, si se maneja otro cliente entonces se agregaría un if con instanceOf
            //que no es lo más óptimo pero ya no se como arreglarlo
            Cliente cliente = ClienteFrecuenteAdapter.dtoAEntidad((ClienteFrecuenteDTO)dto);
            clienteDAO.registrarCliente(cliente);
        } catch (PersistenciaException ex) {
            System.getLogger(ClienteBO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }


    public void eliminar(int idCliente) throws NegocioException {
        try {
            clienteDAO.eliminarCliente(idCliente);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al eliminar cliente: " + e.getMessage());
        }
    }

    public ClienteDTO obtenerPorId(Long idCliente) throws NegocioException{

            Cliente cliente = clienteDAO.buscarCliente(idCliente);
            if (cliente == null) {
                return null;
            }

            ClienteDTO dto = ClienteAdapterFactory.entidadADTO(cliente);

            // Desencripta el teléfono antes de regresar el DTO
            String telefonoDesencriptado = Encriptador.desencriptar(dto.getTelefono());
            dto.setTelefono(telefonoDesencriptado);

            return dto;
    }

    public List<ClienteDTO> obtenerTodos() throws NegocioException {
        List<Cliente> clientes = clienteDAO.obtenerTodosLosClientes();
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        for (Cliente cliente : clientes) {
            ClienteDTO dto = ClienteAdapter.entidadADTO(cliente);
            
            // Desencripta el teléfono de cada cliente
            String telefonoDesencriptado = Encriptador.desencriptar(dto.getTelefono());
            dto.setTelefono(telefonoDesencriptado);
            
            clientesDTO.add(dto);
        }
        return clientesDTO;
    }

    public List<ClienteFrecuenteDTO> obtenerFrecuentes() throws NegocioException {
        try {
            List<ClienteFrecuente> frecuentes = clienteDAO.obtenerFrecuentes();
            List<ClienteFrecuenteDTO> frecuentesDTO = new ArrayList<>();
            //Aquí es donde entran los atributos exclusivos del cliente frecuente que no se persisten
            for (ClienteFrecuente cf : frecuentes) {
                int visitas = clienteDAO.calcularVisitas(cf.getId());
                double totalGastado = clienteDAO.calcularTotalGastado(cf.getId());
                int puntos = calcularPuntos(totalGastado);

                cf.setTotalVisitas(visitas);
                cf.setTotalGastado(totalGastado);
                cf.setPuntos(puntos);
                ClienteFrecuenteDTO dto = ClienteFrecuenteAdapter.entidadADTO(cf);
                // Desencripta el teléfono
                String telefonoDesencriptado = Encriptador.desencriptar(dto.getTelefono());
                dto.setTelefono(telefonoDesencriptado);

                frecuentesDTO.add(dto);
            }
            
            return frecuentesDTO;

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener clientes frecuentes: " + e.getMessage());
        }
    }
    
    /**
     * Método que calcula los puntos de un cliente frecuente, se castean a int para que sean cerrados
     * @param totalGastado el totalGastado del clienteFrecuente
     * @return el número entero de puntos
     */
    private int calcularPuntos(double totalGastado) {
        return (int) (totalGastado / 20);
    }
}
