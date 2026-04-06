/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import DTOs.IngredienteDTO;
import entidades.Ingrediente;
import entidades.UnidadMedida;
import excepciones.NegocioException;

/**
 *
 * @author Tungs
 */
public class IngredienteAdapter {
    
    public static IngredienteDTO ingredienteADTO(Ingrediente ingrediente) {
        if (ingrediente == null) {
            return null;
        }

        String unidadMedida = null;

        if (ingrediente.getUnidadMedida() != null) {
            unidadMedida = ingrediente.getUnidadMedida().name();
        }

        return new IngredienteDTO(
                ingrediente.getId(),
                ingrediente.getNombre(),
                ingrediente.getStock(),
                unidadMedida
        );
    }


    public static Ingrediente dtoAIngrediente(IngredienteDTO dto) throws NegocioException {
        if (dto == null) {
            return null;
        }

        Ingrediente ingrediente = new Ingrediente();

        ingrediente.setId(dto.getId());
        ingrediente.setNombre(dto.getNombre());
        ingrediente.setStock(dto.getStock());

        if (dto.getUnidadMedida() != null && !dto.getUnidadMedida().isEmpty()) {
            try {
                UnidadMedida unidad = UnidadMedida.valueOf(dto.getUnidadMedida().toUpperCase());
                ingrediente.setUnidadMedida(unidad);
            } catch (IllegalArgumentException e) {
                throw new NegocioException("Unidad de medida inválida: " + dto.getUnidadMedida());
            }
        }

        return ingrediente;
    }
    
}
