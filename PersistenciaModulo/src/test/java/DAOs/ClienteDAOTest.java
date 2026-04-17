/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAOs;

import conexion.ConexionBD;
import entidades.Cliente;
import entidades.ClienteFrecuente;
import excepciones.PersistenciaException;
import insertsMasivosGPTOs.PobladorBD;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author Dayanara Peralta G
 */
public class ClienteDAOTest {
    private ClienteDAO dao;
    private Long idPrueba;
    
    public ClienteDAOTest() {
    }

    @BeforeEach
    void setUp() throws PersistenciaException {
        dao = new ClienteDAO();
        ClienteFrecuente temp = new ClienteFrecuente();
        temp.setNombres("Test");
        temp.setApellidoPaterno("Unitario");
        String telefonoEncriptado= PobladorBD.encriptarTelefono("6442014769");
        temp.setTelefono(telefonoEncriptado);
        temp.setCorreo("test@mail.com");
        
        Cliente guardado = dao.registrarCliente(temp);
        this.idPrueba = guardado.getId();
    }
    
    @AfterEach
    public void tearDown(){
        EntityManager em = ConexionBD.crearConexion();
        try{
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Comanda c WHERE c.cliente.nombres IN ('Test', 'Dayanara', 'Nombre Editado', 'Fantasma')").executeUpdate();
            em.createQuery("DELETE FROM Cliente c WHERE c.nombres IN ('Test', 'Dayanara', 'Nombre Editado', 'Fantasma')").executeUpdate();
            em.getTransaction().commit();
        }catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("Error limpiando datos de prueba: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    
    /**
     * Test que inserta un ClienteFrecuente con todos los campos obligatorios.
     */
    @Test
    public void testRegistrarCliente_Exitoso() throws Exception {
        System.out.println("registrarCliente");
        ClienteFrecuente cliente = new ClienteFrecuente();
        cliente.setNombres("Dayanara");
        cliente.setApellidoPaterno("Garcia");
        String telefonoEncriptado= PobladorBD.encriptarTelefono("6442014769");
        cliente.setTelefono(telefonoEncriptado);
        
        Cliente nuevo = dao.registrarCliente(cliente);
        
        assertNotNull(nuevo);
        assertNotNull(nuevo.getId());
        assertEquals("Dayanara", nuevo.getNombres());
    }
    
    /**
     * Test que intenta registrar un cliente sin el campo nombres.
     */
    @Test
    public void testRegistrarCliente_Error() throws Exception {
        System.out.println("registrarCliente");
        ClienteFrecuente cliente = new ClienteFrecuente();
        cliente.setApellidoPaterno("Garcia");
        
        assertThrows(PersistenciaException.class, () -> {
            dao.registrarCliente(cliente);
        });
    }

    /**
     * B
     * Tests que busca un cliente usando el idPrueba generado en el setUp.
     */
    @Test
    public void testBuscarCliente_Exitoso() {
        System.out.println("buscarCliente");
        Cliente buscado = dao.buscarCliente(idPrueba);
        
        assertNotNull(buscado);
        assertEquals(idPrueba, buscado.getId());
    }
    
    /**
     * Test que busca un cliente con un ID que no existe (-1L).
     */
    @Test
    public void testBuscarCliente_Fail() {
        System.out.println("buscarCliente");
        Cliente buscado = dao.buscarCliente(-1L);
        
        assertNull(buscado);
    }

    /**
     * Test que elimina un cliente por su ID y luego intenta buscarlo.
     */
    @Test
    public void testEliminarCliente_Exitoso() throws Exception {
        System.out.println("eliminarCliente");
        assertDoesNotThrow(() -> dao.eliminarCliente(idPrueba));
        
        //se verifica que ya ni existe
        Cliente exterminado = dao.buscarCliente(idPrueba);
        assertNull(exterminado);
    }
    
    /**
     * Test que intenta eliminar pasando un parámetro null.
     */
    @Test
    public void testEliminarCliente_Fail() throws Exception {
        System.out.println("eliminarCliente");
        assertThrows(Exception.class, () ->{
            dao.eliminarCliente(null);
        });
    }

    /**
     * Test que del metodo obtenerTodosLosClientes, que solicita la lista 
     * completa de la tabla clientes.
     */
    @Test
    public void testObtenerTodosLosClientes() {
        System.out.println("obtenerTodosLosClientes");
        ClienteDAO instance = new ClienteDAO();
        List<Cliente> result = instance.obtenerTodosLosClientes();
        assertNotNull(result);
    }
  
    /**
     * Test del metodo obtenerFrecuentes, que olicita solo los registros de 
     * la subclase ClienteFrecuente.
     */
    @Test
    public void testObtenerFrecuentes() {
        System.out.println("obtenerFrecuentes");
        ClienteDAO instance = new ClienteDAO();
        List<ClienteFrecuente> result = instance.obtenerFrecuentes();
        assertNotNull(result);
    }

    /**
     * Test of calcularVisitas method, of class ClienteDAO.
     */
    @Test
    public void testCalcularVisitas_Vacio() throws Exception {
        System.out.println("calcularVisitas");
        int visitas = dao.calcularVisitas(idPrueba);
        assertEquals(0, visitas);
    }
    
    /**
     * Test of calcularVisitas method, of class ClienteDAO.
     */
    @Test
    public void testCalcularVisitas_Fail() throws Exception {
        System.out.println("calcularVisitas");
        assertDoesNotThrow(() -> {
            int visitas = dao.calcularVisitas(-1L);
            assertEquals(0, visitas);
        });
    }

    /**
     * Test of calcularTotalGastado method, of class ClienteDAO.
     */
    @Test
    public void testCalcularTotalGastado_Exitoso() throws Exception {
        System.out.println("calcularTotalGastado");
        ClienteDAO instance = new ClienteDAO();
        double result = instance.calcularTotalGastado(idPrueba);
        assertEquals(0.0, result, 0.001);
    }
    
    /**
     * Test of calcularTotalGastado method, of class ClienteDAO.
     */
    @Test
    public void testCalcularTotalGastado_Fail() throws Exception {
        System.out.println("calcularTotalGastado");
        ClienteDAO instance = new ClienteDAO();
        //Long id = null;
        double result = instance.calcularTotalGastado(null);
        assertEquals(0.0, result, 0.001);
    }

    /**
     * Test que modifica el nombre de un cliente ya existente.
     */
    @Test
    public void testActualizarCliente_Exitoso() throws Exception {
        System.out.println("actualizarCliente");
        Cliente cliente = dao.buscarCliente(idPrueba);
        cliente.setNombres("Nombre Editado");
        
        Cliente actualizado = dao.actualizarCliente(cliente);
        
        assertEquals("Nombre Editado", actualizado.getNombres());
        assertEquals(idPrueba, actualizado.getId());
    }
    
    /**
     * Test que intenta actualizar un objeto nulo.
     */
    @Test
    public void testActualizarCliente_Fail() throws Exception {
        System.out.println("actualizarCliente");
        Cliente nuevoSinId = new ClienteFrecuente();
        nuevoSinId.setNombres("Fantasma");
        
        assertThrows(Exception.class, () -> {
            dao.actualizarCliente(null);
        });
    }

    /**
     * Test del metodo buscarFrecuentesPorFiltros, de la clase ClienteDAO.
     */
//    @Test
//    public void testBuscarFrecuentesPorFiltros_Exitoso() throws Exception {
//        System.out.println("buscarFrecuentesPorFiltros");
//        ClienteDAO instance = new ClienteDAO();
//        String nombre = "pedro";
//        String correo = "";
//        List<ClienteFrecuente> result = instance.buscarFrecuentesPorFiltros(nombre, correo);
//        assertNotNull(result);
//        assertFalse(result.isEmpty());
//    }
    
    /**
     * Test del metodo buscarFrecuentesPorFiltros, de la clase ClienteDAO.
     */
    @Test
    public void testBuscarFrecuentesPorFiltros_Fail() throws Exception {
        System.out.println("buscarFrecuentesPorFiltros");
        ClienteDAO instance = new ClienteDAO();
        String nombre = "Carmen";
        String correo = "car@gmail.com";
        List<ClienteFrecuente> result = instance.buscarFrecuentesPorFiltros(nombre, correo);
        assertTrue(result.isEmpty());
    }
    
}
