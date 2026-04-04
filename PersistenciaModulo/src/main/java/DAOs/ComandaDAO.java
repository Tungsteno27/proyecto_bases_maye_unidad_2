/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import conexion.ConexionBD;
import entidades.Cliente;
import entidades.Comanda;
import excepciones.PersistenciaException;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Dayanara Peralta G
 */
public class ComandaDAO {

    private static final Logger LOG = Logger.getLogger(ComandaDAO.class.getName());
    
    /**
     *
     * @param comanda
     * @return
     * @throws PersistenciaException
     */
    public Comanda registrarComanda(Comanda comanda) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try {
            //Iniciamos la transacción
            em.getTransaction().begin();
            //persistimos 
            em.persist(comanda);
            //Guardamos los cambios si todo salió bien
            em.getTransaction().commit();
            //regresamos el comanda
            LOG.info("Se insertó la comanda de folio: " + comanda.getFolio());
            return comanda;

        } catch (Exception e) {
            //Si ocurre un error no se hace nada, la base vuelve a la normalidad
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOG.warning("Ocurrió un error al intentar insertar la comanda de folio: " + comanda.getFolio());
            throw new PersistenciaException("No se pudo insertar la comanda");
        } finally {
            //Liberamos los recursos del entity manager
            em.close();
        }
    }
    
    /**
     *
     * @return
     * @throws PersistenciaException
     */
    public List<Comanda> buscarTodasComandas() throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try {
            String jpql = "select c from Comanda c";
            TypedQuery<Comanda> query = em.createQuery(jpql, Comanda.class);
            List<Comanda> comanda = query.getResultList();
            return comanda;
        } catch(Exception e){
            throw new PersistenciaException("Error al buscar las comandas");
        } finally {
            //Liberamos los recursos del entity manager
            em.close();
        }
    }
    
    /**
     *
     * @param estado
     * @return
     * @throws PersistenciaException
     */
    public List<Comanda> buscarPorEstado(String estado)throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try{
            String jpql = "select c from Comanda c where c.estado = :estado";
            TypedQuery<Comanda> query = em.createQuery(jpql, Comanda.class);
            query.setParameter("estado", estado);
            List<Comanda> comandas = query.getResultList();
            return comandas;
        }catch(Exception e){
            throw new PersistenciaException("Error al buscar las comandas");
        }finally{
            em.close();
        }
    }
    
    /**
     *
     * @param comanda
     * @return
     * @throws PersistenciaException
     */
    public Comanda modificarComanda(Comanda comanda) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try{
            em.getTransaction().begin();
            Comanda actualizada = em.merge(comanda);
            em.getTransaction().commit();
            LOG.info("Se actualizó la comanda con folio: " + comanda.getFolio());
            return actualizada;
        }catch(Exception e){
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOG.warning("Error al actualizar la comanda con el folio: " + comanda.getFolio());
            throw new PersistenciaException("Error al modificar la comanda");
        }finally{
            em.close();
        }
    }
    
}
