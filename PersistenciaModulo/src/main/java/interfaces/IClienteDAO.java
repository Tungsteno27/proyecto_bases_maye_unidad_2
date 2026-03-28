/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Cliente;
import entidades.ClienteFrecuente;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface IClienteDAO {

    public Cliente registrarCliente(Cliente cliente) throws PersistenciaException;

    public Cliente buscarCliente(Long id);

    public void eliminarCliente(int id) throws PersistenciaException;

    public List<Cliente> obtenerTodosLosClientes();

    public Cliente actualizarCliente(Cliente cliente) throws PersistenciaException;

    public List<ClienteFrecuente> buscarFrecuentesPorFiltros(String nombre, String correo) throws PersistenciaException;

}
