/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import DTOs.EstadoComandaDTO;
import conexion.ConexionBD;
import entidades.Cliente;
import entidades.Comanda;
import entidades.EstadoComanda;
import entidades.Mesa;
import excepciones.PersistenciaException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
            if(comanda.getMesa() == null || comanda.getMesa().getId() == null){
                throw new Exception("El objeto Mesa o su ID llegaron nulos al DAO.");
            }
            System.out.println("DAO: Buscando mesa con ID: " + comanda.getMesa().getId());
            
            Mesa mesa = em.getReference(Mesa.class, comanda.getMesa().getId());
            if (mesa == null) {
                throw new Exception("La mesa con ID " + comanda.getMesa().getId() + " no existe en la base de datos.");
            }
            
            comanda.setMesa(mesa);
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
            e.printStackTrace();
            LOG.warning("Ocurrió un error al intentar insertar la comanda de folio: " + comanda.getFolio());
            throw new PersistenciaException("No se pudo insertar la comanda: "+e.getMessage());
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
    public List<Comanda> buscarPorEstado(EstadoComanda estado)throws PersistenciaException{
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
    
    public Comanda buscarPorFolio(String folio)throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try{
            String jpql = "select c from Comanda c where c.folio = :folio";
            TypedQuery<Comanda> query = em.createQuery(jpql, Comanda.class);
            query.setParameter("folio", folio);
            return query.getSingleResult();
        }catch(Exception e){
            throw new PersistenciaException("Error al buscar las comandas");
        }finally{
            em.close();
        }
    }
    
    public List<Comanda> buscarPorFiltros(Integer mesa, EstadoComandaDTO estado, LocalDateTime inicio, LocalDateTime fin) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            StringBuilder jpql = new StringBuilder("select c from Comanda c where 1=1");
            if (mesa != null) {
                jpql.append(" and c.mesa.numero = :mesa");
            }
            if (estado != null) {
                jpql.append(" and c.estado = :estado");
            }
            if (inicio != null && fin != null) {
                jpql.append(" and c.fechaHora between :inicio and :fin");
            }

            TypedQuery<Comanda> query = em.createQuery(jpql.toString(), Comanda.class);
            
            if(mesa != null){
                query.setParameter("mesa", mesa);
            }
            if(estado != null){
                query.setParameter("estado", EstadoComanda.valueOf(estado.name()));
            }
            if(inicio != null && fin != null){
                query.setParameter("inicio", inicio);
                query.setParameter("fin", fin);
            }
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar las comandas");
        } finally {
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
    
    public Long contarComandas(LocalDate fecha) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try{
            LocalDateTime inicio = fecha.atStartOfDay();
            LocalDateTime fin = fecha.atTime(LocalTime.MAX);

            String jpql = "SELECT COUNT(c) FROM Comanda c WHERE c.fechaHora BETWEEN :inicio AND :fin";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            return query.getSingleResult();
        }catch(Exception e){
            e.printStackTrace();
            throw new PersistenciaException("Error al contar las comandas");
        }finally{
            em.close();
        }
    }
    
    public void actualizar(Comanda comanda) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try{
            em.getTransaction().begin();
            em.merge(comanda);
            em.getTransaction().commit();
        }catch(Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("No se pudo actualizar");
        }finally{
            em.close();
        }
    }
    
}
