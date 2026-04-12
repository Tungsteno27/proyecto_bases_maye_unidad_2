/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import conexion.ConexionBD;
import entidades.EstadoMesa;
import entidades.Mesa;
import excepciones.PersistenciaException;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Dayanara Peralta G
 */
public class MesaDAO {

    private static final Logger LOG = Logger.getLogger(MesaDAO.class.getName());
    
    public Mesa agregarMesa(Mesa mesa)throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try{
            em.getTransaction().begin();
            em.persist(mesa);
            em.getTransaction().commit();
            LOG.info("Se insertó la mesa número: " + mesa.getNumero());
            return mesa;
        }catch(Exception e){
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOG.warning("Ocurrió un error al intentar agregar la mesa con número: " + mesa.getNumero());
            throw new PersistenciaException("No se pudo agregar la mesa");
        }finally{
            em.close();
        }
    }
    
    public Mesa actualizar(Mesa mesa) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try{
            em.getTransaction().begin();
            Mesa actualizada = em.merge(mesa);
            em.getTransaction().commit();
            LOG.info("Se actualizó la mesa número: " + mesa.getNumero());
            return actualizada;
        }catch(Exception e){
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOG.warning("Ocurrió un error al intentar actualizar la mesa con número: " + mesa.getNumero());
            throw new PersistenciaException("No se pudo actualizar la mesa");
        }finally{
            
        }
    }
    
    public Mesa buscarPorId(Long id) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try{
            Mesa mesa = em.find(Mesa.class, id);
            if(mesa != null){
                LOG.info("Se encontró la mesa número: " + mesa.getNumero());
            }else{
                LOG.warning("No se encontró la mesa número: " + mesa.getNumero());
            }
            return mesa;
        }catch(Exception e){
            LOG.warning("Ocurrió un error al intentar buscar la mesa con id: " + id);
            throw new PersistenciaException("No se pudo encontrar la mesa");
        }finally{
            em.close();
        }
    }
    
    public void actualizarEstado(Long id, EstadoMesa nuevoEstado) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try{
            em.getTransaction().begin();
            Mesa mesa = em.find(Mesa.class, id);
            if(mesa != null){
                mesa.setEstado(nuevoEstado);
                em.merge(mesa);
                //throw new Exception("No existe la mesa con ID: " + id);
            }
            
            em.getTransaction().commit();
            System.out.println("Mesa " + id + " actualizada a " + nuevoEstado);
        
        }catch(Exception e){
            LOG.warning("Ocurrió un error al intentar modificar el estado de la mesa con id: " + id);
            throw new PersistenciaException("No se pudo encontrar la mesa");
        }finally{
            em.close();
        }
    }
    
    public List<Mesa> consultarPorEstado(EstadoMesa estado) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try{
            String jpql = "select m from Mesa m where m.estado = :estado";
            TypedQuery<Mesa> query = em.createQuery(jpql, Mesa.class);
            query.setParameter("estado", estado);
            List<Mesa> mesas = query.getResultList();
            return mesas;
        }catch(Exception e){
            LOG.warning("Ocurrió un error al intentar buscar las mesas con estado: " + estado);
            throw new PersistenciaException("No se pudo encontrar la mesa");
        }finally{
            em.close();
        }
    }
    
    public List<Mesa> consultarTodas() throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try{
            String jpql = "select m from Mesa m";
            TypedQuery<Mesa> query = em.createQuery(jpql, Mesa.class);
            
            query.setHint("javax.persistence.cache.retrieveMode", "BYPASS");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Mesa> mesas = query.getResultList();
            return mesas;
        }catch(Exception e){
            throw new PersistenciaException("No se pudo consultar todas las mesas");
        }finally{
            em.close();
        }
    }
}
