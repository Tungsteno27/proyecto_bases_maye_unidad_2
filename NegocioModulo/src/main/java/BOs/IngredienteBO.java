/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.IngredienteDAO;
import DTOs.IngredienteDTO;
import adaptadores.IngredienteAdapter;
import entidades.Ingrediente;
import entidades.UnidadMedida;
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

    /**
     * Registra un nuevo ingrediente aplicando validaciones de negocio que
     * valida que nombre, stock y unidad de medida estén presentes, y que no
     * exista un ingrediente con el mismo nombre y unidad.
     *
     * @param dto el DTO con los datos del ingrediente nuevo
     * @throws NegocioException si alguna validación falla
     */
    public void registrar(IngredienteDTO dto) throws NegocioException {
        validarCampos(dto);
        try {
            UnidadMedida unidad = UnidadMedida.valueOf(dto.getUnidadMedida().toUpperCase());
            boolean duplicado = ingredienteDAO.existeDuplicado(dto.getNombre(), unidad, null);
            if (duplicado) {
                throw new NegocioException("Ya existe un ingrediente con el nombre \""
                        + dto.getNombre() + "\" en " + dto.getUnidadMedida().toLowerCase() + ".");
            }
            Ingrediente entidad = IngredienteAdapter.dtoAIngrediente(dto);
            ingredienteDAO.registrar(entidad);
        } catch (NegocioException e) {
            throw e;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar ingrediente: " + e.getMessage());
        }
    }

    /**
     * Actualiza los datos de un ingrediente existente. Aplica las mismas
     * validaciones que el registro, excluyendo el propio ingrediente al
     * verificar duplicados.
     *
     * @param dto el DTO con los datos actualizados (debe tener id)
     * @throws NegocioException si alguna validación falla
     */
    public void actualizar(IngredienteDTO dto) throws NegocioException {
        if (dto.getId() == null) {
            throw new NegocioException("El ingrediente no tiene un ID válido.");
        }
        validarCampos(dto);
        try {
            UnidadMedida unidad = UnidadMedida.valueOf(dto.getUnidadMedida().toUpperCase());
            boolean duplicado = ingredienteDAO.existeDuplicado(dto.getNombre(), unidad, dto.getId());
            if (duplicado) {
                throw new NegocioException("Ya existe otro ingrediente con el nombre \""
                        + dto.getNombre() + "\" en " + dto.getUnidadMedida().toLowerCase() + ".");
            }
            Ingrediente entidad = IngredienteAdapter.dtoAIngrediente(dto);
            ingredienteDAO.actualizar(entidad);
        } catch (NegocioException e) {
            throw e;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar ingrediente: " + e.getMessage());
        }
    }

    /**
     * Busca ingredientes aplicando filtros opcionales de nombre y unidad de
     * medida.
     *
     * @param nombre fragmento de nombre (null o vacío = sin filtro)
     * @param unidadMedida nombre del enum (null o vacío = sin filtro)
     * @return lista de IngredienteDTO que coinciden con los filtros
     * @throws NegocioException si ocurre un error
     */
    public List<IngredienteDTO> buscarPorFiltros(String nombre, String unidadMedida) throws NegocioException {
        try {
            return ingredienteDAO.buscarPorFiltros(nombre, unidadMedida)
                    .stream()
                    .map(IngredienteAdapter::ingredienteADTO)
                    .toList();
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al buscar ingredientes: " + e.getMessage());
        }
    }

    /**
     * Valida los campos obligatorios de un IngredienteDTO.
     *
     * @param dto el DTO a validar
     * @throws NegocioException si algún campo obligatorio falla
     */
    private void validarCampos(IngredienteDTO dto) throws NegocioException {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new NegocioException("El nombre del ingrediente es obligatorio.");
        }
        if (dto.getNombre().length() > 100) {
            throw new NegocioException("El nombre no puede exceder 100 caracteres.");
        }
        if (dto.getUnidadMedida() == null || dto.getUnidadMedida().isBlank()) {
            throw new NegocioException("La unidad de medida es obligatoria.");
        }
        // Verificamos que la unidad sea válida
        try {
            UnidadMedida.valueOf(dto.getUnidadMedida().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NegocioException("Unidad de medida inválida: " + dto.getUnidadMedida());
        }
        if (dto.getStock() == null) {
            throw new NegocioException("La cantidad en stock es obligatoria.");
        }
        if (dto.getStock() < 0) {
            throw new NegocioException("El stock no puede ser negativo.");
        }
    }
}
