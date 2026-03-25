/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Tungs
 */
public class ConexionBD {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ConexionPU");

    /**
     * Constructor vacío
     */
    private ConexionBD(){
        
    }
    /**
     * Método que crea la conexión con la base de datos
     * @return un entityManager para gestionar la conexión
     */
    public static EntityManager crearConexion(){
        return entityManagerFactory.createEntityManager();
    }
    
}
