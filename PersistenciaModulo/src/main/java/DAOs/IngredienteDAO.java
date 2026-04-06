/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import conexion.ConexionBD;
import entidades.Cliente;
import entidades.Ingrediente;
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
    
    
}
