/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.IngredienteDAO;
import DTOs.IngredienteDTO;
import adaptadores.IngredienteAdapter;
import entidades.Ingrediente;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class IngredienteBO {
    
    private final IngredienteDAO ingredienteDAO;

    public IngredienteBO(IngredienteDAO ingredienteDAO) {
        this.ingredienteDAO = ingredienteDAO;
    }

    public List<IngredienteDTO> obtenerTodos() throws NegocioException {
            return ingredienteDAO.obtenerTodosLosIngredientes()
                    .stream()
                    .map(IngredienteAdapter::ingredienteADTO)
                    .toList();

    }

    public IngredienteDTO buscarPorId(Long id) throws NegocioException {
        try {
            Ingrediente ing = ingredienteDAO.buscarPorId(id);
            if (ing == null) {
                throw new NegocioException("Ingrediente no encontrado");
            }
            return IngredienteAdapter.ingredienteADTO(ing);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar ingrediente");
        }
    }
}
    

