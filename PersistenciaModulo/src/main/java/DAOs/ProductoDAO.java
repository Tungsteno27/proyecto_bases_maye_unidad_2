/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import conexion.ConexionBD;
import entidades.Cliente;
import entidades.EstadoProducto;
import entidades.Producto;
import entidades.ProductoIngrediente;
import excepciones.PersistenciaException;
import interfaces.IProductoDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Clase que implementa métodos CRUD (y demás) sobre productos en la bd
 * @author Tungs
 */
public class ProductoDAO implements IProductoDAO {
    
    //Logger para debuggear (porque habrá muchos errores)
    private static final Logger LOG = Logger.getLogger(ClienteDAO.class.getName());
    
    
    
    /**
     * Método que inserta un producto en la base de datos
     * @param producto el producto insertado
     * @return el producto si se insertó
     * @throws PersistenciaException si ocurre un error al insertar
     */
    public Producto registrarProducto(Producto producto) throws PersistenciaException {
        //Creamos la conexión a la BD con el entityManager
        EntityManager em = ConexionBD.crearConexion();
        try {
            //Iniciamos la transacción
            em.getTransaction().begin();
            //persistimos 
            em.persist(producto);
            //Guardamos los cambios si todo salió bien
            em.getTransaction().commit();
            //regresamos el producto
            LOG.info("se insertó al producto de nombre: " + producto.getNombre());
            return producto;
        } catch (Exception e) {
            //Si ocurre un error no se hace nada, la base vuelve a la normalidad
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOG.warning("Ocurrió un error al intentar insertar al producto de nombre: " + producto.getNombre());
            throw new PersistenciaException("No se pudo insertar al producto");
        } finally {
            //Liberamos los recursos del entity manager
            em.close();
        }
    }
    
    
    
    /**
     * Método que regresa todos los productos según los filtros
     * @param nombre el nombre o parte del nombre a buscar
     * @param tipo el tipo o categoría de producto
     * @return una lista con los producotos que cuadren con los filtros
     */
    public List<Producto> buscarProductos(String nombre, String tipo) {
        EntityManager em = ConexionBD.crearConexion();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Producto> query = cb.createQuery(Producto.class);
            Root<Producto> producto = query.from(Producto.class);
            List<Predicate> filtros = new ArrayList<>();
            //Filtrado por nombre parcial o completo, los % son como en el sql tradicional al usar like
            if (nombre != null && !nombre.isEmpty()) {
                filtros.add(cb.like(
                    cb.lower(producto.get("nombre")),
                    "%" + nombre.toLowerCase() + "%"
                ));
            }
            //Filtrado opcional por el tipo o categoría, aqui si no es parcial.
            if (tipo != null && !tipo.isEmpty()) {
                filtros.add(cb.equal(producto.get("tipo"), tipo));
            }
            
            //Guardamos solo los que concuerden con los filtros esto si estuvo medio enredoso
            query.select(producto).where(cb.and(filtros.toArray(new Predicate[0])));
            //Regresamos coincidencias, sin aventar null para mostrar la tabla vacía en ese caso
            return em.createQuery(query).getResultList();
        } finally {
            em.close();
        }
    }
    
    
    /**
     * Método que actualiza un producto en la BD
     * @param producto el producto nuevo
     * @return el producto actualizado
     * @throws PersistenciaException si ocurre un error al comunicarse con la BD
     */
    public Producto actualizarProducto(Producto producto) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            Producto actualizado = em.merge(producto);
            em.getTransaction().commit();
            return actualizado;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al actualizar el producto");
        } finally {
            em.close();
        }
    }
    
    
    /**
     * Método que busca un producto en la base de datos
     * @param id el id del producto a buscar
     * @return el producto si lo encontró, null en caso contrario
     * @throws PersistenciaException si ocurre un error en la BD
     */
    public Producto buscarPorId (Long id) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try {
            Producto producto= em.find(Producto.class, id);
            //Dependiendo si lo encuentra o no muestra un mensaje
            if (producto!= null) {
                LOG.info("Se encontró al producto con id: " + id);
            } else {
                LOG.warning("No se encontró ningún producto con id: " + id);
            }
            //Se regresa al producto
            return producto ;
        } catch (Exception e) {
            LOG.warning("Ocurrió un error al intentar buscar al producto con id: " + id);
            throw e;
        } finally {
            em.close();
        }
    }
    
    
    /**
     * Método que valida si el nombre de un producto ya existe en la base de datos antes de persistir
     * @param nombre el nombre a validar
     * @return verdadero si existe, falso en caso contrario
     * @throws PersistenciaException si ocurre un error en la base de datos
     */
    public boolean existeProductoNombreDuplicado(String nombre) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String jpql = "SELECT COUNT(p) FROM Producto p WHERE LOWER(p.nombre) = LOWER(:nombre) AND p.estado = :estado";
            Long count = em.createQuery(jpql, Long.class).setParameter("nombre", nombre.trim()).setParameter("estado", EstadoProducto.ACTIVO)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            throw new PersistenciaException("Error al verificar producto duplicado");
        } finally {
            em.close();
        }
    }
    
    /**
     * Método que regresa todos los ingredientes de un producto
     * @param idProducto el id del producto a regresar sus ingredientes
     * @return
     * @throws PersistenciaException 
     */
    public List<ProductoIngrediente> obtenerIngredientesPorProducto(Long idProducto) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String jpql = "SELECT pi FROM ProductoIngrediente pi WHERE pi.producto.id = :idProducto";
            TypedQuery<ProductoIngrediente> query = em.createQuery(jpql, ProductoIngrediente.class);
            query.setParameter("idProducto", idProducto);
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al obtener ingredientes del producto: " + e.getMessage());
        }finally {
            em.close(); 
        }
    }
    
    /**
     * Método que cambia el estado de un producto, este se utilizaría para "eliminar"
     * @param id el id del producto
     * @param estado el nuevo estado
     * @throws PersistenciaException si ocurre un error
     */
    public void cambiarEstado(Long id, String estado) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            String jpql = "UPDATE Producto p SET p.estado = :estado WHERE p.id = :id";
            em.createQuery(jpql).setParameter("estado", EstadoProducto.valueOf(estado)).setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); 
            }
            throw new PersistenciaException("Error al cambiar estado: " + e.getMessage());
        } finally {
            em.close(); 
        }
    }
    /**
     * Método que regresa todos los productos
     * @return una lista con todos los productos
     */
    public List<Producto> obtenerTodos() {
        EntityManager em = ConexionBD.crearConexion();
            String jpql = "SELECT p FROM Producto p ORDER BY p.nombre ASC";
            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);
            return query.getResultList();
    }

}
