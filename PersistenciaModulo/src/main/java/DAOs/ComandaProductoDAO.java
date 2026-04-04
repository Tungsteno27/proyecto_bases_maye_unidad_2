/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import conexion.ConexionBD;
import entidades.ComandaProducto;
import excepciones.PersistenciaException;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Dayanara Peralta G
 */
public class ComandaProductoDAO {

    private static final Logger LOG = Logger.getLogger(ComandaProductoDAO.class.getName());
    
    public ComandaProducto agregar(ComandaProducto producto) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try{
            em.getTransaction().begin();
            em.persist(producto);
            em.getTransaction().commit();
            LOG.info("Se agregó el producto a la comanda");
            return producto;
        }catch(Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            LOG.warning("Ocurrió un error al intentar agregar el producto a la comanda.");
            throw new PersistenciaException("No se pudo agregar un producto a la comanda.");
        }finally{
            em.close();
        }
    }
    
    public void eliminar(Long id) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try{
            ComandaProducto producto = em.find(ComandaProducto.class, id);
            if(producto != null){
                em.getTransaction().begin();
                em.remove(producto);
                em.getTransaction().commit();
                LOG.info("Se eliminó el producto");
            }else{
                LOG.warning("No se encontró el producto.");
            }
        }catch(Exception e){
            
        }finally{
            em.close();
        }
    }
    
    public List<ComandaProducto> consultarPorComanda(Long idComanda)throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try{
            String jpql = "select c from ComandaProducto c where c.idComanda = :idComanda";
            TypedQuery<ComandaProducto> query = em.createQuery(jpql, ComandaProducto.class);
            query.setParameter("idComanda", idComanda);
            List<ComandaProducto> prod = query.getResultList();
            return prod;
        }catch(Exception e){
            throw new PersistenciaException("Error al buscar los productos de la comanda con id: " + idComanda);
        }
    }
}
