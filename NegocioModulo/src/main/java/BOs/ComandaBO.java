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
import DTOs.ComandaProductoDTO;
import DTOs.EstadoComandaDTO;
import DTOs.MesaDTO;
import DTOs.ProductoDTO;
import adaptadores.MesaAdapter;
import adaptadores.ProductoAdapter;
import entidades.Cliente;
import entidades.Comanda;
import entidades.EstadoComanda;
import entidades.EstadoMesa;
import entidades.EstadoProducto;
import entidades.Mesa;
import entidades.Producto;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import factory.ComandaAdapterFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Dayanara Peralta G
 */
public class ComandaBO {
    private ComandaDAO comandaDAO = new ComandaDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private MesaDAO mesaDAO = new MesaDAO();
    private ProductoDAO productoDAO = new ProductoDAO();

    
    public ComandaBO() {
    }

    public ComandaBO(ComandaDAO comandaDAO) {
        this.comandaDAO = comandaDAO;
    }

    public ComandaBO(ComandaDAO comandaDAO, MesaDAO mesaDAO) {
        this.comandaDAO = new ComandaDAO();
        this.mesaDAO = new MesaDAO();
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
                throw new NegocioException("No se puede registrar una comanda sin productos.");
            }
            double total = 0;
            for(ComandaProductoDTO c : dto.getComandaProductos()){
                Double precio = c.getProducto().getPrecio();
                Double cant = c.getCantidad();
                double tot = precio * cant;
                c.setTotalProductos(tot);
                total += tot;
            }
            
            if(dto.getFolio()== null || dto.getFolio().isEmpty()){
                dto.setFolio(this.generarFolio());
            }
            
            dto.setFechaHora(LocalDateTime.now());
            dto.setEstado(EstadoComandaDTO.ABIERTA);
            dto.setTotalComanda(total);
            
            Comanda comanda = ComandaAdapterFactory.dtoAEntidad(dto);
            Mesa m = mesaDAO.buscarPorId(dto.getMesa().getId());
            m.setEstado(EstadoMesa.OCUPADA);
            comanda.setMesa(m);
            
            if(comanda.getComandaProductos() != null){
                for(entidades.ComandaProducto c : comanda.getComandaProductos()){
                    c.setComanda(comanda);
                }
            }
            comandaDAO.registrarComanda(comanda);
            mesaDAO.actualizar(m);
        } catch (PersistenciaException ex) {
            Logger.getLogger(ComandaBO.class.getName()).log(Level.SEVERE, null, ex);
            throw new NegocioException("Error al persistir."+ ex.getMessage());
        }
    }
    
    public void actualizar(ComandaDTO dto) throws NegocioException{
        if(dto == null || dto.getId() == null){
            throw new NegocioException("Comanda inválida");
        }
        try{
            Comanda comandaBD = ComandaAdapterFactory.dtoAEntidad(dto);
            comandaDAO.actualizar(comandaBD);
            if(dto.getEstado() != EstadoComandaDTO.ABIERTA){
                mesaDAO.actualizarEstado(dto.getMesa().getId(), EstadoMesa.DISPONIBLE);
            }
        }catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar producto");
        }
    }
    
    public List<ComandaDTO> buscarComanda(Integer numeroMesa, EstadoComandaDTO estadoComanda,LocalDateTime inicio, LocalDateTime fin)throws NegocioException{
        try{
            List<Comanda> comanda = comandaDAO.buscarPorFiltros(numeroMesa, estadoComanda, inicio, fin);
            List<ComandaDTO> dto = new ArrayList<>();
            for(Comanda c : comanda){
                dto.add(ComandaAdapterFactory.entidadADTO(c));
            }
            return dto;
        }catch (PersistenciaException e) {
            throw new NegocioException("No se pudo encontrar comandas");
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
    
    public List<MesaDTO> consultarTodasMesas() throws NegocioException{
        try{
            List<Mesa> mesas = mesaDAO.consultarTodas();
            return mesas.stream()
                    .map(MesaAdapter::entidadADTO)
                    .collect(Collectors.toList());
        }catch(PersistenciaException e){
            throw new NegocioException("Error al consultar las mesas. Error: "+e.getMessage());
        }
    }
    
    public List<MesaDTO> consulatarMesasDisponibles() throws NegocioException{
        try{
            List<Mesa> mesas = mesaDAO.consultarPorEstado(EstadoMesa.DISPONIBLE);
            return mesas.stream()
                    .map(MesaAdapter::entidadADTO)
                    .collect(Collectors.toList());
        }catch(PersistenciaException e){
            throw new NegocioException("Error al consultar las mesas disponibles. Error: "+e.getMessage());
        }
    }
    
    public List<ComandaDTO> obtenerComandas() throws PersistenciaException{
        List<Comanda> comandas = comandaDAO.buscarTodasComandas();
        List<ComandaDTO> todas = new ArrayList<>();
        for(Comanda c : comandas){
            todas.add(ComandaAdapterFactory.entidadADTO(c));
        }
        return todas;
    }
    
    public List<ComandaDTO> obtenerComandasAbiertas() throws PersistenciaException{
        List<Comanda> comandas = comandaDAO.buscarPorEstado(EstadoComanda.ABIERTA);
        List<ComandaDTO> todas = new ArrayList<>();
        for(Comanda c : comandas){
            todas.add(ComandaAdapterFactory.entidadADTO(c));
        }
        return todas;
    }
    
    public ComandaDTO obtenerPorFolio(String folio) throws NegocioException{
        try{
            Comanda comanda = comandaDAO.buscarPorFolio(folio);
            if(comanda == null){
                return null;
            }
            return ComandaAdapterFactory.entidadADTO(comanda);
        }catch(Exception e){
            throw new NegocioException("Error al obtener comanda");
        }
    }
    
    public String generarFolio() throws PersistenciaException, NegocioException{
        try{
            LocalDate hoy = LocalDate.now();
        String fecha = hoy.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        
        Long numero = comandaDAO.contarComandas(hoy) + 1;
        String num = String.format("%03d", numero);
        return "OB_"+ fecha + "-" + num;
        }catch(PersistenciaException e){
            e.printStackTrace();
            throw new NegocioException("Error al generar el folio: "+e.getMessage());
        }
        
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
