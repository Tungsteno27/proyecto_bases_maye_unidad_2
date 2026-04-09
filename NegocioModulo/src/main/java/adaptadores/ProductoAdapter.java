/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import DAOs.IngredienteDAO;
import DTOs.ProductoDTO;
import DTOs.ProductoIngredienteDTO;
import entidades.EstadoProducto;
import entidades.Ingrediente;
import entidades.Producto;
import entidades.ProductoIngrediente;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class ProductoAdapter {
    
    IngredienteDAO ingredienteDAO;
    
    /**
     * Método que convierte una entidad producto en un productoDTO
     * @param producto el producto a convertir
     * @return  el producto convertido a DTO
     */
    public static ProductoDTO productoADTO(Producto producto){
        if (producto== null){
            return null;
        }
        
        List<ProductoIngredienteDTO> ingredientesDTO = null;

        if (producto.getIngredientes() != null) {
            ingredientesDTO = producto.getIngredientes()
                .stream()
                .map(pi -> productoIngredienteADTO(pi))
                .toList();
        }
        return new ProductoDTO(producto.getId()
                ,producto.getNombre()
                ,producto.getPrecio()
                , producto.getTipo()
                , producto.getDescripcion()
                , producto.getEstado().name()
                , producto.getImagenUrl()
                , ingredientesDTO );
    }
    
    /**
     * Método qie convierte un dto a un producto
     * @param dto el dto a convertir a producto
     * @return una entidad de tipo producto
     * @throws NegocioException si ocurre un error
     */
    public Producto dtoAProducto(ProductoDTO dto) throws NegocioException {
        if (dto == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setTipo(dto.getTipo());

        if (dto.getEstado() != null && !dto.getEstado().isEmpty()) {
            try {
                producto.setEstado(EstadoProducto.valueOf(dto.getEstado()));
            } catch (IllegalArgumentException e) {
                throw new NegocioException("Estado de producto inválido: " + dto.getEstado());
            }
        }
        producto.setImagenUrl(dto.getImagenUrl());
        if (dto.getIngredientes() != null && !dto.getIngredientes().isEmpty()) {

            List<ProductoIngrediente> lista = new ArrayList<>();

            for (ProductoIngredienteDTO piDTO : dto.getIngredientes()) {
                try {
                    ProductoIngrediente pi = productoIngredienteADominio(piDTO, producto);
                    lista.add(pi);
                } catch (PersistenciaException e) {
                    throw new NegocioException("Error al acceder a datos de ingredientes");
                }
            }

            producto.setIngredientes(lista);
        }
        return producto;
    }
    
    /**
     * Método que convierte un productoIngredeinteDTO a dominio
     * @param dto el dto de productoIngredeinte a convertir
     * @param producto la entidad de producto
     * @return la entidad de productoIngrediente
     * @throws PersistenciaException si ocurre un error al llamar al DAO
     * @throws NegocioException si el ingrediente recuperado es nulo
     */
    private ProductoIngrediente productoIngredienteADominio(ProductoIngredienteDTO dto,Producto producto) throws PersistenciaException, NegocioException {
        ProductoIngrediente pi = new ProductoIngrediente();
        Ingrediente ingrediente = ingredienteDAO.buscarPorId(dto.getIdIngrediente());

        if (ingrediente == null) {
            throw new NegocioException("Ingrediente no encontrado con ID: " + dto.getIdIngrediente());
        }
        pi.setIngrediente(ingrediente);
        pi.setCantidad(dto.getCantidad());
        pi.setProducto(producto);
        return pi;
    }
    
    /**
     * Método que convierte un productoIngrediente a DTO
     * @param pi la entidad de productoIngrediente a convertir
     * @return el dto de productoIngrediente
     */
    public static ProductoIngredienteDTO productoIngredienteADTO(ProductoIngrediente pi){
        if (pi == null){
            return null;
        }
        ProductoIngredienteDTO dto = new ProductoIngredienteDTO();

        dto.setIdIngrediente(pi.getIngrediente().getId());
        dto.setCantidad(pi.getCantidad());

        dto.setNombreIngrediente(pi.getIngrediente().getNombre());
        dto.setUnidad(pi.getIngrediente().getUnidadMedida().name());

        return dto;
    }
}
