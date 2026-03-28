/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import conexion.ConexionBD;
import entidades.Cliente;
import entidades.ClienteFrecuente;
import entidades.EstadoComanda;
import excepciones.PersistenciaException;
import interfaces.IClienteDAO;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Clase que realiza operaciones sobre la tabla clientes (e hijos) de la base de
 * datos
 *
 * @author Tungs
 */
public class ClienteDAO implements IClienteDAO {

    //Logger para debuggear (porque habrá muchos errores)
    private static final Logger LOG = Logger.getLogger(ClienteDAO.class.getName());

    /**
     * Método que inserta un cliente en la base de datos, como se maneja
     * herencia si el cliente es un cliente frecuente igual se inserta el
     * Cliente padre
     *
     * @param cliente el cliente a insertar, puede ser de cualquiera de las
     * clases hijas
     * @return el cliente insertado
     */
    @Override
    public Cliente registrarCliente(Cliente cliente) throws PersistenciaException {
        //Creamos la conexión a la BD con el entityManager
        EntityManager em = ConexionBD.crearConexion();
        try {
            //Iniciamos la transacción
            em.getTransaction().begin();
            //persistimos 
            em.persist(cliente);
            //Guardamos los cambios si todo salió bien
            em.getTransaction().commit();
            //regresamos el cliente
            LOG.info("se insertó al cliente de nombre: " + cliente.getNombres());
            return cliente;

        } catch (Exception e) {
            //Si ocurre un error no se hace nada, la base vuelve a la normalidad
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOG.warning("Ocurrió un error al intentar insertar al cliente de nombre: " + cliente.getNombres());
            throw new PersistenciaException("No se pudo insertar al cliente");
        } finally {
            //Liberamos los recursos del entity manager
            em.close();
        }
    }

    /**
     * Método que busca un cliente por su id
     *
     * @param id el id del cliente a buscar
     * @return el cliente encontrado
     */
    @Override
    public Cliente buscarCliente(Long id) {
        //Se crea la conexión
        EntityManager em = ConexionBD.crearConexion();
        try {
            Cliente cliente = em.find(Cliente.class, id);
            //Dependiendo si lo encuentra o no muestra un mensaje
            if (cliente != null) {
                LOG.info("Se encontró al cliente con id: " + id);
            } else {
                LOG.warning("No se encontró ningún cliente con id: " + id);
            }
            //Se regresa el cliente
            return cliente;
        } catch (Exception e) {
            LOG.warning("Ocurrió un error al intentar buscar al cliente con id: " + id);
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Método que elimina un cliente de la BD por su id
     *
     * @param id el id del cliente a eliminar
     */
    @Override
    public void eliminarCliente(int id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {

            em.getTransaction().begin();

            Cliente cliente = em.find(Cliente.class, id);

            if (cliente != null) {
                em.remove(cliente);
                em.getTransaction().commit();
                LOG.info("Se eliminó al cliente con id: " + id);
            } else {
                em.getTransaction().rollback();
                LOG.warning("No se encontró al cliente con id: " + id);
            }

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOG.warning("Ocurrió un error al intentar eliminar al cliente con id: " + id);
            throw new PersistenciaException("NO se pudo eliminar al cliente");
        } finally {
            em.close();
        }
    }

    /**
     * Método que regresa todos los clientes de la BD sin importar los tipos
     *
     * @return una lista con todos lo clientes en la base de datos
     */
    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        //No avienta ninguna excepción para que se pueda mostrar más facilmente en la tabla dinámica
        EntityManager em = ConexionBD.crearConexion();
        String jpql = "SELECT c FROM Cliente c";
        TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
        return query.getResultList();
    }

    /**
     * Método que obtiene una Lista de clientes frecuentes
     *
     * @return una lista de clientes frecuentes
     */
    public List<ClienteFrecuente> obtenerFrecuentes() {
        EntityManager em = ConexionBD.crearConexion();
        String jpql = "SELECT cf FROM ClienteFrecuente cf";
        TypedQuery<ClienteFrecuente> query = em.createQuery(jpql, ClienteFrecuente.class);
        return query.getResultList();
    }

    /**
     * Método que calcula las visitas de un cliente accediendo a sus comandas
     * registradas
     *
     * @param idCliente el id del cliente a calcular
     * @return el número de visitas máximas
     * @throws PersistenciaException si ocurre un error
     */
    public int calcularVisitas(Long idCliente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String jpql = "SELECT COUNT(c) FROM Comanda c "
                    + "WHERE c.cliente.id = :id "
                    + "AND c.estado = :estado";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("id", idCliente);
            query.setParameter("estado", EstadoComanda.ENTREGADA);
            Long visitas = query.getSingleResult();
            if (visitas != null) {
                return visitas.intValue();
            }
            LOG.info("No hay visitas registradas");
            return 0;
        } catch (Exception e) {
            throw new PersistenciaException("Error al calcular visitas del cliente: " + e.getMessage());
        }
    }

    /**
     * Método que calcula el total gastado de un cliente
     *
     * @param idCliente el id del cliente
     * @return la cantidad decimal de dinero gastado
     * @throws PersistenciaException si ocurre un error
     */
    public double calcularTotalGastado(Long idCliente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String jpql = "SELECT SUM(c.totalComanda) FROM Comanda c "
                    + "WHERE c.cliente.id = :id "
                    + "AND c.estado = :estado";
            TypedQuery<Double> query = em.createQuery(jpql, Double.class);
            query.setParameter("id", idCliente);

            query.setParameter("estado", EstadoComanda.ENTREGADA);
            Double total = query.getSingleResult();
            //O sea si encontró algo
            if (total != null) {
                return total;
            }
            LOG.info("El cliente de id " + idCliente + " no  ha gastado nada aún");
            return 0.0;
        } catch (Exception e) {
            throw new PersistenciaException("Error al calcular total gastado: " + e.getMessage());
        }
    }

    /**
     * Actualiza los datos de un cliente frecuente en la base de datos
     *
     * @param cliente la entidad con los datos nuevos
     * @return el cliente actualizado
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public Cliente actualizarCliente(Cliente cliente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            Cliente actualizado = em.merge(cliente);
            em.getTransaction().commit();
            LOG.info("Se actualizó el cliente con id: " + cliente.getId());
            return actualizado;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOG.warning("Error al actualizar cliente id: " + cliente.getId());
            throw new PersistenciaException("No se pudo actualizar al cliente");
        } finally {
            em.close();
        }
    }

    /**
     * Busca clientes frecuentes filtrando por nombre parcial y/o correo
     * parcial. El filtro de teléfono se aplica en la capa de negocio porque el
     * teléfono está encriptado en la BD.
     *
     * @param nombre fragmento de nombre (puede ser null para ignorar este
     * filtro)
     * @param correo fragmento de correo (puede ser null para ignorar este
     * filtro)
     * @return lista de ClienteFrecuente que coinciden
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public List<ClienteFrecuente> buscarFrecuentesPorFiltros(String nombre, String correo) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            // Construimos la query dinámicamente según qué filtros llegan
            StringBuilder jpql = new StringBuilder("SELECT cf FROM ClienteFrecuente cf WHERE 1=1");
            if (nombre != null && !nombre.isBlank()) {
                jpql.append(" AND (LOWER(cf.nombres) LIKE :nombre"
                        + " OR LOWER(cf.apellidoPaterno) LIKE :nombre"
                        + " OR LOWER(cf.apellidoMaterno) LIKE :nombre)");
            }
            if (correo != null && !correo.isBlank()) {
                jpql.append(" AND LOWER(cf.correo) LIKE :correo");
            }

            TypedQuery<ClienteFrecuente> query = em.createQuery(jpql.toString(), ClienteFrecuente.class);

            if (nombre != null && !nombre.isBlank()) {
                query.setParameter("nombre", "%" + nombre.toLowerCase() + "%");
            }
            if (correo != null && !correo.isBlank()) {
                query.setParameter("correo", "%" + correo.toLowerCase() + "%");
            }

            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar clientes frecuentes: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
