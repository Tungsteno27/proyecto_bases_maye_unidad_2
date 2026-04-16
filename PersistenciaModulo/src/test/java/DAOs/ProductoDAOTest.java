/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAOs;

import entidades.EstadoProducto;
import entidades.Producto;
import entidades.ProductoIngrediente;
import excepciones.PersistenciaException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Tungs
 *
public class ProductoDAOTest {
    
    private ProductoDAO productoDAO;
    
    public ProductoDAOTest() {
    }
    
    @BeforeEach
    void setUp() {
        productoDAO = new ProductoDAO();
    }

    @Test
    void registrarProducto_exitoso() throws Exception {
        Producto p = new Producto();
        p.setNombre("Dogo Test");
        p.setPrecio(100.0);
        p.setTipo("Comida");
        p.setDescripcion("Test");
        p.setEstado(EstadoProducto.ACTIVO);

        Producto resultado = productoDAO.registrarProducto(p);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals("Dogo Test", resultado.getNombre());
    }
    
    @Test
    void registrarProducto_fallido() {
        assertThrows(NullPointerException.class, () -> {
            productoDAO.registrarProducto(null);
        });
    }
    
    @Test
    void buscarProductos_exitoso() {
        List<Producto> lista = productoDAO.buscarProductos("Dogo Test", null);
        assertNotNull(lista);
    }
    
    @Test
    void buscarProductos_sinResultados() {
        List<Producto> lista = productoDAO.buscarProductos("NO_EXISTE_123", null);

        assertNotNull(lista);
        assertTrue(lista.isEmpty());
    }
    
    @Test
    void actualizarProducto_exitoso() throws Exception {
        Producto p = new Producto();
        p.setNombre("Producto Update");
        p.setPrecio(50.0);
        p.setEstado(EstadoProducto.ACTIVO);

        p = productoDAO.registrarProducto(p);

        p.setPrecio(80.0);

        Producto actualizado = productoDAO.actualizarProducto(p);

        assertEquals(80.0, actualizado.getPrecio());
    }
    
    @Test
    void actualizarProducto_fallido() {
        Producto p = new Producto();
        assertThrows(PersistenciaException.class, () -> {
            productoDAO.actualizarProducto(p);
        });
    }
    
    @Test
    void buscarPorId_exitoso() throws Exception {
        Producto p = new Producto();
        p.setNombre("Buscar Test");
        p.setPrecio(30.0);
        p.setEstado(EstadoProducto.ACTIVO);

        p = productoDAO.registrarProducto(p);

        Producto encontrado = productoDAO.buscarPorId(p.getId());

        assertNotNull(encontrado);
    }
    
    @Test
    void buscarPorId_noExiste() throws Exception {
        Producto encontrado = productoDAO.buscarPorId(999999L);

        assertNull(encontrado);
    }
    
    @Test
    void existeProductoNombreDuplicado_true() throws Exception {
        Producto p = new Producto();
        p.setNombre("DuplicadoTest");
        p.setPrecio(20.0);
        p.setEstado(EstadoProducto.ACTIVO);

        productoDAO.registrarProducto(p);

        boolean existe = productoDAO.existeProductoNombreDuplicado("DuplicadoTest");

        assertTrue(existe);
    }
    
    @Test
    void existeProductoNombreDuplicado_false() throws Exception {
        boolean existe = productoDAO.existeProductoNombreDuplicado("NoExiste");

        assertFalse(existe);
    }
    
    @Test
    void obtenerIngredientesPorProducto_exitoso() throws Exception {
        Producto p = new Producto();
        p.setNombre("Producto con ingredientes");
        p.setPrecio(10.0);
        p.setEstado(EstadoProducto.ACTIVO);

        p = productoDAO.registrarProducto(p);

        List<ProductoIngrediente> lista
                = productoDAO.obtenerIngredientesPorProducto(p.getId());

        assertNotNull(lista);
    }
    
    
    @Test
    void cambiarEstado_exitoso() throws Exception {
        Producto p = new Producto();
        p.setNombre("EstadoTest");
        p.setPrecio(10.0);
        p.setEstado(EstadoProducto.ACTIVO);

        p = productoDAO.registrarProducto(p);

        productoDAO.cambiarEstado(p.getId(), "INACTIVO");

        Producto actualizado = productoDAO.buscarPorId(p.getId());

        assertEquals(EstadoProducto.INACTIVO, actualizado.getEstado());
    }
    @Test
    void obtenerTodos_vacio() {
        List<Producto> lista = productoDAO.obtenerTodos();

        assertNotNull(lista);
    }
    
    
}*/
