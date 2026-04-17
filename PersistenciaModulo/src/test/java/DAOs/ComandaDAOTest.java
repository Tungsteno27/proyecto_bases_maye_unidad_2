/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package DAOs;

import DTOs.EstadoComandaDTO;
import conexion.ConexionBD;
import entidades.Cliente;
import entidades.ClienteFrecuente;
import entidades.Comanda;
import entidades.EstadoComanda;
import entidades.Mesa;
import excepciones.PersistenciaException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author Dayanara Peralta G
 */
public class ComandaDAOTest {
    private ComandaDAO dao;
    public ComandaDAOTest() {
    }

    @BeforeEach
    public void setUp(){
        dao = new ComandaDAO();
    }
    
    @AfterEach
    public void tearDown() {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            String deleteDetails = "DELETE FROM ComandaProducto cp WHERE cp.comanda.folio LIKE 'TEST-%'";
            em.createQuery(deleteDetails).executeUpdate();
            String deleteComandas = "DELETE FROM Comanda c WHERE c.folio LIKE 'TEST-%'";
            em.createQuery(deleteComandas).executeUpdate();
            em.getTransaction().commit();
            System.out.println("Limpieza de datos de prueba completada.");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error durante la limpieza: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    
    /**
     * Test of registrarComanda method, of class ComandaDAO.
     */
    @Test
    public void testRegistrarComanda() throws Exception {
        System.out.println("registrarComanda");
        Comanda comanda = new Comanda();
        comanda.setFolio("Test-001");
        comanda.setEstado(EstadoComanda.ABIERTA);
        comanda.setFechaHora(LocalDateTime.now());
        
        Mesa mesa = new Mesa();
        mesa.setId(1L);
        comanda.setMesa(mesa);
        
        Comanda regis = dao.registrarComanda(comanda);
        assertNotNull(regis);
        assertEquals("Test-001", regis.getFolio());
    }
    
    /**
     * Test of registrarComanda method, of class ComandaDAO.
     */
    @Test
    public void testRegistrarComanda_Fail() throws Exception {
        System.out.println("registrarComanda");
        Comanda comanda = new Comanda();
        comanda.setFolio("TestFail-001");
        comanda.setEstado(EstadoComanda.ABIERTA);
        comanda.setFechaHora(LocalDateTime.now());
        comanda.setMesa(null);
        
        assertThrows(PersistenciaException.class, ()->{
            dao.registrarComanda(comanda);
        });
    }

    /**
     * Test of buscarTodasComandas method, of class ComandaDAO.
     */
    @Test
    public void testBuscarTodasComandas() throws Exception {
        System.out.println("buscarTodasComandas");
        List<Comanda> result = dao.buscarTodasComandas();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    /**
     * Test of buscarPorEstado method, of class ComandaDAO.
     */
    @Test
    public void testBuscarPorEstado() throws Exception {
        System.out.println("buscarPorEstado");
        List<Comanda> result = dao.buscarPorEstado(EstadoComanda.ENTREGADA);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
    
    /**
     * Test of buscarPorEstado method, of class ComandaDAO.
     */
    @Test
    public void testBuscarPorEstado_Fail() throws Exception {
        System.out.println("buscarPorEstado");
        List<Comanda> result = dao.buscarPorEstado(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * Test of buscarPorFolio method, of class ComandaDAO.
     */
    @Test
    public void testBuscarPorFolio() throws Exception {
        System.out.println("buscarPorFolio");
        String folio = "F-001";
        Comanda busc = dao.buscarPorFolio(folio);
        assertNotNull(busc);
        assertEquals(folio, busc.getFolio());
    }
    
    /**
     * Test of buscarPorFolio method, of class ComandaDAO.
     */
    @Test
    public void testBuscarPorFolio_Fail() throws Exception {
        System.out.println("buscarPorFolio");
        String folio = "folioFail-001";
        assertThrows(PersistenciaException.class, ()->{
            dao.buscarPorFolio(folio);
        });
    }

    /**
     * Test of buscarPorFiltros method, of class ComandaDAO.
     */
    @Test
    public void testBuscarPorFiltrosConCliente() throws Exception {
        System.out.println("buscarPorFiltros");
        Integer idMesa = 1;
        List<Comanda> result = dao.buscarEntregadasPorFiltros(idMesa, null, null, null);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
    
    /**
     * Test of buscarPorFiltros method, of class ComandaDAO.
     */
    @Test
    public void testBuscarPorFiltrosConCliente_Fail() throws Exception {
        System.out.println("buscarPorFiltros");
        List<Comanda> result = dao.buscarEntregadasPorFiltros(-1, null, null, null);
        assertTrue(result.isEmpty());
    }

    /**
     * Test of buscarPorFiltros method, of class ComandaDAO.
     */
    @Test
    public void testBuscarPorFiltrosSinCliente() throws Exception {
        System.out.println("buscarPorFiltros");
        List<Comanda> result = dao.buscarPorFiltros(null, EstadoComandaDTO.ABIERTA, null, null);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
    
    /**
     * Test of buscarPorFiltros method, of class ComandaDAO.
     */
    @Test
    public void testBuscarPorFiltrosSinCliente_Fail() throws Exception {
        System.out.println("buscarPorFiltros");
        List<Comanda> result = dao.buscarPorFiltros(1987, null, null, null);
        assertTrue(result.isEmpty());
    }

    /**
     * Test of modificarComanda method, of class ComandaDAO.
     */
    @Test
    public void testModificarComanda() throws Exception {
        System.out.println("modificarComanda");
        Comanda c = dao.buscarPorFolio("F-002");
        c.setEstado(EstadoComanda.ABIERTA);
        Comanda act = dao.modificarComanda(c);
        assertEquals(EstadoComanda.ABIERTA, act.getEstado());
    }
    
    /**
     * Test of modificarComanda method, of class ComandaDAO.
     */
    @Test
    public void testModificarComanda_Fail() throws Exception {
        System.out.println("modificarComanda");
        assertThrows(PersistenciaException.class, () -> {
            dao.modificarComanda(null);
        });
    }

    /**
     * Test of contarComandas method, of class ComandaDAO.
     */
    @Test
    public void testContarComandas() throws Exception {
        System.out.println("contarComandas");
        LocalDate hoy = LocalDate.now();
        Long cont = dao.contarComandas(hoy);
        assertNotNull(cont);
        assertTrue(cont >= 0);
    }
    
    /**
     * Test of contarComandas method, of class ComandaDAO.
     */
    @Test
    public void testContarComandas_Fail() throws Exception {
        System.out.println("contarComandas");
        assertThrows(PersistenciaException.class, ()->{
            dao.contarComandas(null);
        });
    }

    /**
     * Test of actualizar method, of class ComandaDAO.
     */
    @Test
    public void testActualizar() throws Exception {
        String folio = "F-003";
        Comanda com = dao.buscarPorFolio(folio);
        com.setEstado(EstadoComanda.ENTREGADA);
        dao.actualizar(com);
        Comanda act = dao.buscarPorFolio(folio);
        assertEquals(EstadoComanda.ENTREGADA, act.getEstado());
    }
    
    /**
     * Test of actualizar method, of class ComandaDAO.
     */
    @Test
    public void testActualizar_Fail() throws Exception {
        Comanda comandaFicticia = new Comanda();
        comandaFicticia.setId(999999L);
        comandaFicticia.setFolio("NO-EXISTE");
        assertThrows(PersistenciaException.class, () -> {
            dao.actualizar(null);
        });
    }
    
}
