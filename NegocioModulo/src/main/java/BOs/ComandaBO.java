/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.ClienteDAO;
import DAOs.ComandaDAO;
import DAOs.MesaDAO;
import DAOs.ProductoDAO;
import DTOs.ComandaDTO;
import DTOs.EstadoComandaDTO;
import DTOs.ProductoDTO;
import adaptadores.ProductoAdapter;
import entidades.Cliente;
import entidades.Comanda;
import entidades.EstadoProducto;
import entidades.Mesa;
import entidades.Producto;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import factory.ComandaAdapterFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Dayanara Peralta G
 */
public class ComandaBO {
    private ComandaDAO comandaDAO;
    private ClienteDAO clienteDAO;
    private MesaDAO mesaDAO;
    private ProductoDAO productoDAO;
    
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
            this.validarDisponibilidad(dto.getMesa().getId());
            
            if(dto.getComandaProductos() == null || dto.getComandaProductos().isEmpty()){
                throw new NegocioException("No se puede registrar una comanda sin productos.");
            }
            this.consultarProductosDisponibles();
            
            String folio = this.generarFolio();
            dto.setFolio(folio);
            
            dto.setFechaHora(LocalDateTime.now());
            dto.setEstado(EstadoComandaDTO.ABIERTA);
            
            Comanda comanda = ComandaAdapterFactory.dtoAEntidad(dto);
            comandaDAO.registrarComanda(comanda);
        } catch (PersistenciaException ex) {
            Logger.getLogger(ComandaBO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<ProductoDTO> consultarProductosDisponibles() throws NegocioException{
        try{
            List<Producto> todos = productoDAO.obtenerTodos();
            List<ProductoDTO> activos = todos.stream().filter(p -> p.getEstado() == EstadoProducto.ACTIVO)
                    .map(p -> ProductoAdapter.productoADTO(p))
                    .collect(Collectors.toList());
            return activos;
        }catch(Exception e){
            throw new NegocioException("Error al cargar el menú.");
        }
    }
    
    private String generarFolio() throws PersistenciaException{
        LocalDate hoy = LocalDate.now();
        String fecha = hoy.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        
        Long numero = comandaDAO.contarComandas(hoy) + 1;
        String num = String.format("%03d", numero);
        return "OB_"+ fecha + "-" + num;
    }
    
    private void validarDisponibilidad(Long idMesa) throws PersistenciaException{
        try{
            Mesa mesa = mesaDAO.buscarPorId(idMesa);
            if(mesa == null){
                throw new NegocioException("La mesa no existe.");
            }
            if(!"Disponible".equals(mesa.getEstado())){
                throw new NegocioException("Mesa ocupada.");
            }
        }catch(NegocioException e){
            throw new PersistenciaException("Error al consultar el estado de la mesa.");
        }   
    }
}
