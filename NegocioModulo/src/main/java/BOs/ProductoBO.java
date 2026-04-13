/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.IngredienteDAO;
import DAOs.ProductoDAO;
import DTOs.ProductoDTO;
import DTOs.ProductoIngredienteDTO;
import adaptadores.ProductoAdapter;
import entidades.EstadoProducto;
import entidades.Ingrediente;
import entidades.Producto;
import entidades.ProductoIngrediente;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import utilidadesBO.Validadores;

/**
 * Clase que agrega reglas de negocio al CRUD de productos
 * @author Tungs
 */
public class ProductoBO {
    
    private final ProductoDAO productoDAO;
    private final IngredienteDAO ingredienteDAO;
    
    ProductoAdapter Adapter= new ProductoAdapter();


    public ProductoBO(ProductoDAO productoDAO, IngredienteDAO ingredienteDAO) {
        this.productoDAO = productoDAO;
        this.ingredienteDAO = ingredienteDAO;
    }
    
    /**
     * Método que registra un producto en la BD después de aplicarle reglas de negocio
     * @param dto el dto del produto recibido
     * @return el dto de producto insertado
     * @throws NegocioException si ocurre un error
     */
    public ProductoDTO registrarProducto(ProductoDTO dto) throws NegocioException{
        
        if (dto == null) {
            throw new NegocioException("El producto no puede ser nulo");
        }
        if (dto.getPrecio() < 0) {
            throw new NegocioException("El precio no puede ser negativo");
        }
        if (dto.getIngredientes() == null || dto.getIngredientes().isEmpty()) {
            throw new NegocioException("El producto debe tener al menos un ingrediente");
        }

        try {
            Producto producto = new Producto();
            producto.setNombre(dto.getNombre());
            producto.setPrecio(dto.getPrecio());
            producto.setTipo(dto.getTipo());
            producto.setDescripcion(dto.getDescripcion());
            producto.setEstado(EstadoProducto.ACTIVO);
            producto.setImagenUrl(dto.getImagenUrl());
            //Antes pasaba que no se agregaban bien los ingredientes, por eso lo hice como en el actualizar
            List<ProductoIngrediente> lista = new ArrayList<>();

            if (dto.getIngredientes() != null) {
                for (ProductoIngredienteDTO piDTO : dto.getIngredientes()) {
                    if (piDTO.getCantidad() == null || piDTO.getCantidad() <= 0) {
                        throw new NegocioException("Cantidad inválida");
                    }
                    Ingrediente ingrediente = ingredienteDAO.buscarPorId(piDTO.getIdIngrediente());

                    if (ingrediente == null) {
                        throw new NegocioException("No se encontró al ingrediente");
                    }
                    ProductoIngrediente pi = new ProductoIngrediente();
                    pi.setProducto(producto);
                    pi.setIngrediente(ingrediente);
                    pi.setCantidad(piDTO.getCantidad());

                    lista.add(pi);
                }
            }
            producto.setIngredientes(lista);
            Producto guardado = productoDAO.registrarProducto(producto);
            return ProductoAdapter.productoADTO(guardado);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar producto");
        }
        
    }
    
    
    /**
     * Método que busca todos los productos que coincidan con los filtros adecuados
     * @param nombre el nombre total o parcial del producto
     * @param tipo el tipo o categoría del producto
     * @return los productos que coincidan con los filtros
     * @throws NegocioException si ocurre un error con la conexión (si la lista está vacía no pasa nada, la tabla estaría vacía en presentación)
     */
    public List<ProductoDTO> buscarProductos(String nombre, String tipo) throws NegocioException {
        try {
            List<Producto> productos = productoDAO.buscarProductos(nombre, tipo);
            return productos.stream().map(ProductoAdapter::productoADTO).toList();
        } catch (Exception e) {
            throw new NegocioException("Error al buscar productos");
        }
    }
    
    
    /**
     * Método que actualiza un producto en la bd y le aplica reglas de negocio
     * @param dto el dto del producto a recibir
     * @return el productoDTO actualizado
     * @throws NegocioException si ocurre un error
     */
    public ProductoDTO actualizarProducto(ProductoDTO dto) throws NegocioException {
        if (dto == null || dto.getId() == null) {
            throw new NegocioException("Producto inválido");
        }
        try {
            //Llamamos al DAO ara buscar el producto (ver si existe)
            Producto productoBD = productoDAO.buscarPorId(dto.getId());

            if (productoBD == null) {
                throw new NegocioException("Producto no encontrado");
            }
            
            if(dto.getPrecio() < 0){
                throw new NegocioException("El precio del producto no puede ser negativo");
            }
            //En caso de que haya cambiado el nombre validamos que no esté registrado
            if (!productoBD.getNombre().equalsIgnoreCase(dto.getNombre())) {
                if (productoDAO.existeProductoNombreDuplicado(dto.getNombre())) {
                    throw new NegocioException("Ya existe otro producto con ese nombre");
                }
            }
            productoBD.setNombre(dto.getNombre());
            productoBD.setDescripcion(dto.getDescripcion());
            productoBD.setPrecio(dto.getPrecio());
            productoBD.setTipo(dto.getTipo());
            productoBD.setImagenUrl(dto.getImagenUrl()); 
            if (dto.getEstado() != null) {
                productoBD.setEstado(EstadoProducto.valueOf(dto.getEstado()));
            }
            // Aquí es mejor reemplazar todo, pues el usuario puede agregar un ingrediente, o quitar uno, o cambiar la cantidad de otro, etc, es más fácil así
            productoBD.getIngredientes().clear();
            if (dto.getIngredientes() != null) {
                for (ProductoIngredienteDTO piDTO : dto.getIngredientes()) {
                    if (piDTO.getCantidad() == null || piDTO.getCantidad() <= 0) {
                        throw new NegocioException("Cantidad inválida");
                    }
                    Ingrediente ingrediente = ingredienteDAO.buscarPorId(piDTO.getIdIngrediente());
                    if (ingrediente == null) {
                        throw new NegocioException("Ingrediente no encontrado");
                    }
                    ProductoIngrediente pi = new ProductoIngrediente();
                    pi.setProducto(productoBD);
                    pi.setIngrediente(ingrediente);
                    pi.setCantidad(piDTO.getCantidad());

                    productoBD.getIngredientes().add(pi);
                }
            }

            Producto actualizado = productoDAO.actualizarProducto(productoBD);

            return ProductoAdapter.productoADTO(actualizado);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar producto");
        }
    }
    
    /**
     * Método que cambia el estado de un producto en la bd
     * @param id el id del producto
     * @param estado el estado nuevo del producto
     * @throws NegocioException si ocurre un error
     */
    public void cambiarEstado(Long id, String estado) throws NegocioException {
        try {
            if (id == null) {
                throw new NegocioException("El ID del producto es obligatorio");
            }
            productoDAO.cambiarEstado(id, estado);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al cambiar estado: " + e.getMessage());
        }
    }
    
    
    /**
     * Método que obtiene todos los productos de la base de datos
     * @return una lista con todos lo productos en la BD
     */
    public List<ProductoDTO> obtenerTodos() {
        List<Producto> productos = productoDAO.obtenerTodos();
        List<ProductoDTO> resultado = new ArrayList<>();
        for (Producto p : productos) {
            resultado.add(ProductoAdapter.productoADTO(p));
        }
        return resultado;  
    }
    
    /**
     * Método que busca un producto en la base de datos por su id
     * @param id el id del producto a buscar
     * @return un DTO de producto
     * @throws NegocioException si ocurre un error al buscar
     */
    public ProductoDTO obtenerPorId(Long id) throws NegocioException {
        try {
            Producto producto = productoDAO.buscarPorId(id);
            if (producto == null) return null;
            return ProductoAdapter.productoADTO(producto);
        } catch (Exception e) {
            throw new NegocioException("Error al obtener producto");
        }
    }
    
    
    
    
    
    
    
}
