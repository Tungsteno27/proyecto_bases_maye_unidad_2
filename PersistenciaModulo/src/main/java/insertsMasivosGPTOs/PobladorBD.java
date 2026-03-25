/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package insertsMasivosGPTOs;

import conexion.ConexionBD;
import entidades.Cliente;
import entidades.ClienteFrecuente;
import entidades.Comanda;
import entidades.ComandaProducto;
import entidades.EstadoComanda;
import entidades.Ingrediente;
import entidades.Mesa;
import entidades.Mesero;
import entidades.Producto;
import entidades.ProductoIngrediente;
import entidades.UnidadMedida;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;

/**
 * ESTA CLASE SÓLO EXISTE COMO UNA PRUEBA RÁPIDA DE QUE LAS TABLAS FUNCIONAN COMO ESTÁN MODELADAS, BASÁNDOSE EN EL DISEÑO DESCRITO EN EL DIAGRAMA
 * DE CLASES DE DOMINIO, AÚN NO SE GUARDA EL TELEFONO CIFRADO, NI LOS CÓDIGOS TIENEN EL FORMATO ADECUADO, ESO LO VEREMOS MAÑANA
 * @author Tungs
 */
public class PobladorBD {
    public static void poblar() {
        EntityManager em = ConexionBD.crearConexion();
        Random random = new Random();

        try {
            em.getTransaction().begin();

            // =====================
            // 1. MESEROS
            // =====================
            String[][] datosMeseros = {
                {"Juan", "García", "López"},
                {"María", "Martínez", "Pérez"},
                {"Carlos", "Rodríguez", "Sánchez"},
                {"Ana", "López", "Gómez"}
            };

            List<Mesero> meseros = new ArrayList<>();
            for (String[] datos : datosMeseros) {
                Mesero m = new Mesero();
                m.setNombres(datos[0]);
                m.setApellidoPaterno(datos[1]);
                m.setApellidoMaterno(datos[2]);
                m.setFechaRegistro(LocalDate.now().minusDays(random.nextInt(365)));
                em.persist(m);
                meseros.add(m);
            }

            // =====================
            // 2. CLIENTES
            // =====================
            String[][] datosClientes = {
                {"Luis", "Hernández", "Torres"},
                {"Sofía", "Ramírez", "Díaz"},
                {"Pedro", "Flores", "Cruz"},
                {"Laura", "Morales", "Vega"}
            };

            List<Cliente> clientes = new ArrayList<>();
            for (String[] datos : datosClientes) {
                Cliente c = new Cliente();
                c.setNombres(datos[0]);
                c.setApellidoPaterno(datos[1]);
                c.setApellidoMaterno(datos[2]);
                c.setTelefono("644" + (1000000 + random.nextInt(9000000)));
                c.setCorreo(datos[0].toLowerCase() + "." + datos[1].toLowerCase() + "@gmail.com");
                c.setFechaRegistro(LocalDate.now().minusDays(random.nextInt(365)));
                em.persist(c);
                clientes.add(c);
            }

            // =====================
            // 3. CLIENTES FRECUENTES
            // =====================
            String[][] datosFrecuentes = {
                {"Roberto", "Castro", "Núñez"},
                {"Elena", "Vargas", "Ríos"}
            };

            for (String[] datos : datosFrecuentes) {
                ClienteFrecuente cf = new ClienteFrecuente();
                cf.setNombres(datos[0]);
                cf.setApellidoPaterno(datos[1]);
                cf.setApellidoMaterno(datos[2]);
                cf.setTelefono("644" + (1000000 + random.nextInt(9000000)));
                cf.setCorreo(datos[0].toLowerCase() + "." + datos[1].toLowerCase() + "@gmail.com");
                cf.setFechaRegistro(LocalDate.now().minusDays(random.nextInt(365)));
                em.persist(cf);
                clientes.add(cf);
            }

            // =====================
            // 4. INGREDIENTES
            // =====================
            String[][] datosIngredientes = {
                {"Tomate", "PIEZAS"},
                {"Queso", "GRAMOS"},
                {"Harina", "GRAMOS"},
                {"Aceite de Oliva", "MILILITROS"},
                {"Lechuga", "PIEZAS"},
                {"Pollo", "GRAMOS"},
                {"Salsa", "MILILITROS"}
            };

            List<Ingrediente> ingredientes = new ArrayList<>();
            for (String[] datos : datosIngredientes) {
                Ingrediente ing = new Ingrediente();
                ing.setNombre(datos[0]);
                ing.setStock(100.0 + random.nextDouble() * 400);
                ing.setUnidadMedida(UnidadMedida.valueOf(datos[1]));
                em.persist(ing);
                ingredientes.add(ing);
            }

            // =====================
            // 5. PRODUCTOS
            // =====================
            String[][] datosProductos = {
                {"Pizza Margherita", "Pizza"},
                {"Ensalada César", "Ensalada"},
                {"Pollo a la Plancha", "Plato Fuerte"},
                {"Pasta Alfredo", "Pasta"},
                {"Hamburguesa Clásica", "Hamburguesa"}
            };

            List<Producto> productos = new ArrayList<>();
            for (String[] datos : datosProductos) {
                Producto p = new Producto();
                p.setNombre(datos[0]);
                p.setPrecio(50.0 + random.nextDouble() * 200);
                p.setTipo(datos[1]);
                p.setIngredientes(new ArrayList<>());
                em.persist(p);
                productos.add(p);
            }

            // =====================
            // 6. PRODUCTO INGREDIENTES
            // =====================
            for (Producto p : productos) {
                int numIngredientes = 2 + random.nextInt(3);
                List<Ingrediente> usados = new ArrayList<>();
                for (int i = 0; i < numIngredientes; i++) {
                    Ingrediente ing;
                    do {
                        ing = ingredientes.get(random.nextInt(ingredientes.size()));
                    } while (usados.contains(ing));
                    usados.add(ing);

                    ProductoIngrediente pi = new ProductoIngrediente();
                    pi.setProducto(p);
                    pi.setIngrediente(ing);
                    pi.setCantidad(10.0 + random.nextDouble() * 90);
                    em.persist(pi);
                }
            }

            // =====================
            // 7. MESAS
            // =====================
            List<Mesa> mesas = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                Mesa mesa = new Mesa();
                mesa.setNumero(i);
                mesa.setEstado("DISPONIBLE");
                em.persist(mesa);
                mesas.add(mesa);
            }

            // =====================
            // 8. COMANDAS Y COMANDA PRODUCTOS
            // =====================
            String[] folios = {"F-001", "F-002", "F-003", "F-004", "F-005"};
            for (int i = 0; i < folios.length; i++) {
                Comanda comanda = new Comanda();
                comanda.setFolio(folios[i]);
                comanda.setFechaHora(LocalDateTime.now().minusDays(random.nextInt(10)));
                comanda.setEstado(EstadoComanda.ABIERTA);
                comanda.setMesero(meseros.get(random.nextInt(meseros.size())));
                comanda.setMesa(mesas.get(random.nextInt(mesas.size())));
                comanda.setCliente(clientes.get(random.nextInt(clientes.size())));
                comanda.setComandaProductos(new ArrayList<>());

                double totalComanda = 0.0;
                int numProductos = 1 + random.nextInt(4);

                em.persist(comanda);

                for (int j = 0; j < numProductos; j++) {
                    Producto p = productos.get(random.nextInt(productos.size()));
                    double cantidad = 1 + random.nextInt(3);
                    double totalProducto = cantidad * p.getPrecio();
                    totalComanda += totalProducto;

                    ComandaProducto cp = new ComandaProducto();
                    cp.setComanda(comanda);
                    cp.setProducto(p);
                    cp.setCantidad(cantidad);
                    cp.setComentario("Sin comentario");
                    cp.setTotalProductos(totalProducto);
                    em.persist(cp);
                }

                comanda.setTotalComanda(totalComanda);
                em.merge(comanda);
            }

            em.getTransaction().commit();
            System.out.println("¡Base de datos poblada con éxito!");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        poblar();
    }

}
