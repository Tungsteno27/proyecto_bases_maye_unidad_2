/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Producto;
import entidades.ProductoIngrediente;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface IProductoDAO {
    
    public Producto registrarProducto(Producto producto) throws PersistenciaException;
    
    public List<Producto> buscarProductos(String nombre, String tipo);
    
    public Producto actualizarProducto(Producto producto) throws PersistenciaException;
    
    public Producto buscarPorId (Long id) throws PersistenciaException;
    
    public boolean existeProductoNombreDuplicado(String nombre) throws PersistenciaException;
     
    public List<ProductoIngrediente> obtenerIngredientesPorProducto(Long idProducto) throws PersistenciaException;
    
    public void cambiarEstado(Long id, String estado) throws PersistenciaException ;
     
    public List<Producto> obtenerTodos();
    
}
