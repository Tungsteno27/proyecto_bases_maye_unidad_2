/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import conexion.ConexionBD;
import entidades.Cliente;
import entidades.Ingrediente;
import entidades.UnidadMedida;
import excepciones.PersistenciaException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Tungs
 */
public class IngredienteDAO {

    public Ingrediente buscarPorId(Long id) throws PersistenciaException {

        EntityManager em = ConexionBD.crearConexion();

        try {
            return em.find(Ingrediente.class, id);

        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar el ingrediente con ID: " + id);
        } finally {
            em.close();
        }
    }

    public List<Ingrediente> obtenerTodosLosIngredientes() {
        //No avienta ninguna excepción para que se pueda mostrar más facilmente en la tabla dinámica
        EntityManager em = ConexionBD.crearConexion();
        String jpql = "SELECT i FROM Ingrediente i";
        TypedQuery<Ingrediente> query = em.createQuery(jpql, Ingrediente.class);
        return query.getResultList();
    }

    /**
     * Registra un nuevo ingrediente en la base de datos.
     *
     * @param ingrediente la entidad a persistir
     * @return el ingrediente persistido con su id generado
     * @throws PersistenciaException si ocurre un error en la BD
     */
    public Ingrediente registrar(Ingrediente ingrediente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            em.persist(ingrediente);
            em.getTransaction().commit();
            return ingrediente;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("No se pudo registrar el ingrediente: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza los datos de un ingrediente existente.
     *
     * @param ingrediente la entidad con los datos nuevos (debe tener id)
     * @return el ingrediente actualizado
     * @throws PersistenciaException si ocurre un error en la BD
     */
    public Ingrediente actualizar(Ingrediente ingrediente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            Ingrediente actualizado = em.merge(ingrediente);
            em.getTransaction().commit();
            return actualizado;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("No se pudo actualizar el ingrediente: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Busca ingredientes filtrando opcionalmente por nombre parcial y/o unidad
     * de medida. Si ambos filtros son null o vacíos, regresa todos los
     * ingredientes.
     *
     * @param nombre fragmento de nombre (null = sin filtro)
     * @param unidadMedida nombre exacto del enum (null = sin filtro)
     * @return lista de ingredientes que coinciden
     * @throws PersistenciaException si ocurre un error
     */
    public List<Ingrediente> buscarPorFiltros(String nombre, String unidadMedida) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            StringBuilder jpql = new StringBuilder("SELECT i FROM Ingrediente i WHERE 1=1");
            if (nombre != null && !nombre.isBlank()) {
                jpql.append(" AND LOWER(i.nombre) LIKE :nombre");
            }
            if (unidadMedida != null && !unidadMedida.isBlank()) {
                jpql.append(" AND i.unidadMedida = :unidad");
            }
            jpql.append(" ORDER BY i.nombre");

            TypedQuery<Ingrediente> query = em.createQuery(jpql.toString(), Ingrediente.class);

            if (nombre != null && !nombre.isBlank()) {
                query.setParameter("nombre", "%" + nombre.toLowerCase() + "%");
            }
            if (unidadMedida != null && !unidadMedida.isBlank()) {
                query.setParameter("unidad", UnidadMedida.valueOf(unidadMedida.toUpperCase()));
            }

            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar ingredientes: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Verifica si ya existe un ingrediente con el mismo nombre y unidad de
     * medida. Se usa para prevenir duplicados antes de registrar o actualizar.
     *
     * @param nombre el nombre a verificar
     * @param unidadMedida la unidad de medida a verificar
     * @param idExcluir id a excluir de la búsqueda (útil al modificar; pasar
     * null al registrar)
     * @return true si ya existe un duplicado
     * @throws PersistenciaException si ocurre un error
     */
    public boolean existeDuplicado(String nombre, UnidadMedida unidadMedida, Long idExcluir) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String jpql = "SELECT COUNT(i) FROM Ingrediente i "
                    + "WHERE LOWER(i.nombre) = :nombre "
                    + "AND i.unidadMedida = :unidad "
                    + (idExcluir != null ? "AND i.id <> :idExcluir" : "");

            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("nombre", nombre.toLowerCase().trim());
            query.setParameter("unidad", unidadMedida);
            if (idExcluir != null) {
                query.setParameter("idExcluir", idExcluir);
            }

            return query.getSingleResult() > 0;
        } catch (Exception e) {
            throw new PersistenciaException("Error al verificar duplicado: " + e.getMessage());
        } finally {
            em.close();
        }
    }

}
