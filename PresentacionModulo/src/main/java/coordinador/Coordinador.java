/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coordinador;

import BOs.ClienteBO;
import BOs.ComandaBO;
import BOs.IngredienteBO;
import BOs.ProductoBO;
import DAOs.ClienteDAO;
import DAOs.ComandaDAO;
import DAOs.IngredienteDAO;
import DAOs.MesaDAO;
import DAOs.ProductoDAO;
import DTOs.ClienteDTO;
import DTOs.ClienteFrecuenteDTO;
import DTOs.ComandaDTO;
import DTOs.ComandaProductoDTO;
import DTOs.EstadoComandaDTO;
import DTOs.IngredienteDTO;
import DTOs.MesaDTO;
import DTOs.ProductoDTO;
import DTOs.ReporteClienteFrecuenteDTO;
import Interfaces.ComandaObserver;
import Interfaces.MesaObserver;
import entidades.ComandaProducto;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperPrint;
import pantallas.FrmBuscadorClientes;
import pantallas.FrmBuscadorClientesComanda;
import pantallas.FrmBuscadorComanda;
import pantallas.FrmBuscadorIngredientes;
import pantallas.FrmBuscadorProductos;
import pantallas.FrmComanda;
import pantallas.FrmEnMantenimiento;
import pantallas.FrmModulosComandas;
import pantallas.FrmExito;
import pantallas.FrmInfoAdicional;
import pantallas.FrmModificar;
import pantallas.FrmModificarCliente;
import pantallas.FrmModificarComanda;
import pantallas.FrmModificarIngrediente;
import pantallas.FrmModuloClientes;
import pantallas.FrmModuloIngredientes;
import pantallas.FrmModuloReportes;
import pantallas.FrmSeleccionRol;
import pantallas.FrmModulos;
import pantallas.FrmPassword;
import pantallas.FrmRegistrarCliente;
import pantallas.FrmRegistrarIngrediente;
import pantallas.FrmRegistrarProducto;
import pantallas.FrmReporteClientesFrecuentes;
import pantallas.FrmReporteComandas;
import pantallas.FrmSeleccionarId;
import pantallas.FrmSeleccionarMesa;

/**
 * Clase que coordina el flujo entre pantallas y la lógica de negocio
 *
 * @author Tungs
 */
public class Coordinador {

    private static final Logger LOG = Logger.getLogger(Coordinador.class.getName());
    //aquí se ponen los frames y objetos a utilizar como los BO's
    private FrmSeleccionRol frmSeleccionRol;
    private FrmPassword frmPassword;
    private FrmModulos frmModulos;

    // Modulo clientes
    private FrmModuloClientes frmModuloClientes;
    private FrmRegistrarCliente frmRegistrarCliente;
    private FrmSeleccionarId frmSeleccionarId;
    private FrmModificarCliente frmModificarCliente;
    private FrmBuscadorClientes frmBuscadorClientes;
    private FrmInfoAdicional frmInfoAdicional;
    private FrmExito frmExito;
    private FrmEnMantenimiento frmMantenimieto;
    
    //ModuloComandas
    public MesaDTO mesa;
    private String origen = "Modulos";
    private String comanda = "registrar";
    private List<MesaObserver> observer = new ArrayList<>();
    private List<ComandaObserver> obser = new ArrayList<>();
    private FrmModulosComandas frmModuloComandas;
    private FrmComanda frmComanda;
    private FrmSeleccionarMesa frmSeleccionarMesa;
    private FrmBuscadorClientesComanda frmBuscadorClientesComanda;
    private FrmModificarComanda frmModificarComanda;
    private FrmModificar frmModificar;
    private FrmBuscadorComanda frmBuscadorComanda;

    //Módulo de productos
    private FrmBuscadorProductos frmBuscadorProductos;
    private FrmRegistrarProducto frmRegistrarProducto;

    //Módulo de Reportes
    private FrmModuloReportes frmModuloReportes;
    private FrmReporteClientesFrecuentes frmReporteClientes;
    private FrmReporteComandas frmReporteComandas;

    // Modulo de ingredientes
    private FrmModuloIngredientes frmModuloIngredientes;
    private FrmRegistrarIngrediente frmRegistrarIngrediente;
    private FrmModificarIngrediente frmModificarIngrediente;
    private FrmBuscadorIngredientes frmBuscadorIngredientes;
    private Consumer<IngredienteDTO> callbackIngrediente;

    //BO's
    private ClienteBO clienteBO;
    private ComandaBO comandaBO;
    private ProductoBO productoBO;
    private IngredienteBO ingredienteBO;

    //Método que arranca el sistema
    public void iniciarSistema() {

        clienteBO = new ClienteBO(new ClienteDAO());
        productoBO = new ProductoBO(new ProductoDAO(), new IngredienteDAO());
        ingredienteBO = new IngredienteBO(new IngredienteDAO());
        comandaBO = new ComandaBO(new ComandaDAO());

        if (frmSeleccionRol == null) {
            frmSeleccionRol = new FrmSeleccionRol(this);
        }
        frmSeleccionRol.setVisible(true);
    }

    //Si se eligió el rol de admin se pide contraseña
    public void rolAdministradorSeleccionado() {
        if (frmSeleccionRol != null) {
            frmSeleccionRol.setVisible(false);
        }

        if (frmPassword == null) {
            frmPassword = new FrmPassword(this);
        }
        frmPassword.limpiar();
        frmPassword.setVisible(true);
        frmPassword.toFront();
    }

    //Si elige mesero lo manda al modulo de comandas (Dayanara)
    public void rolMeseroSeleccionado() {
        if (frmSeleccionRol != null) {
            frmSeleccionRol.setVisible(false);
        }
        if (frmModuloComandas != null) {
            frmModuloComandas.dispose();
        }
        frmModuloComandas = new FrmModulosComandas(this);
        frmModuloComandas.setVisible(true);
        frmModuloComandas.toFront();
    }

    //Valida la contraseña ingresada (falta implementar, aún no sé donde guardarla)
    public void validarPassword(String password) {
        // TODO: aquí irá la validación real con el BO
        mostrarModulos();
    }

    //Volver de contraseña  seleccion de rol
    public void regresarDesdePassword() {
        if (frmPassword != null) {
            frmPassword.setVisible(false);
        }

        if (frmSeleccionRol == null) {
            frmSeleccionRol = new FrmSeleccionRol(this);
        }
        frmSeleccionRol.setVisible(true);
        frmSeleccionRol.toFront();
    }

    //muestra los modulos después de ingresar la contraseña
    private void mostrarModulos() {
        if (frmPassword != null) {
            frmPassword.setVisible(false);
        }

        if (frmModulos == null) {
            frmModulos = new FrmModulos(this);
        }
        frmModulos.setVisible(true);
        frmModulos.toFront();
    }

    //Volver desde módulos hasta la seleción del rol anterior
    public void cerrarSesion() {
        if (frmModulos != null) {
            frmModulos.dispose();
            frmModulos = null;
        }

        if (frmPassword != null) {
            frmPassword.dispose();
            frmPassword = null;
        }

        if (frmSeleccionRol == null) {
            frmSeleccionRol = new FrmSeleccionRol(this);
        }

        if (frmModuloIngredientes != null) {
            frmModuloIngredientes.dispose();
            frmModuloIngredientes = null;
        }
        if (frmRegistrarIngrediente != null) {
            frmRegistrarIngrediente.dispose();
            frmRegistrarIngrediente = null;
        }
        if (frmSeleccionarId != null) {
            frmSeleccionarId.dispose();
            frmSeleccionarId = null;
        }
        if (frmModificarIngrediente != null) {
            frmModificarIngrediente.dispose();
            frmModificarIngrediente = null;
        }
        if (frmBuscadorIngredientes != null) {
            frmBuscadorIngredientes.dispose();
            frmBuscadorIngredientes = null;
        }

        frmSeleccionRol.setVisible(true);
        frmSeleccionRol.toFront();

    }

    // Navegacion modulo clientes
    public void abrirModuloClientes() {
        if (frmModulos != null) {
            frmModulos.setVisible(false);
        }
        if (frmModuloClientes == null) {
            frmModuloClientes = new FrmModuloClientes(this);
        }
        frmModuloClientes.setVisible(true);
        frmModuloClientes.toFront();
    }

    public void regresarDesdeModuloClientes() {
        if (frmModuloClientes != null) {
            frmModuloClientes.setVisible(false);
        }

        if (frmModulos == null) {
            frmModulos = new FrmModulos(this);
        }
        frmModulos.setVisible(true);
        frmModulos.toFront();
    }

    // Registrar
    public void abrirRegistrarCliente() {
        if (frmModuloClientes != null) {
            frmModuloClientes.setVisible(false);
        }
        if (frmRegistrarCliente == null) {
            frmRegistrarCliente = new FrmRegistrarCliente(this, clienteBO);
        }
        frmRegistrarCliente.limpiar();
        frmRegistrarCliente.setVisible(true);
        frmRegistrarCliente.toFront();
    }

    public void regresarDesdeRegistrarCliente() {
        if (frmRegistrarCliente != null) {
            frmRegistrarCliente.setVisible(false);
        }
        if (frmModuloClientes == null) {
            frmModuloClientes = new FrmModuloClientes(this);
        }
        frmModuloClientes.setVisible(true);
        frmModuloClientes.toFront();
    }

    // Seleccionar ID (para modificar o eliminar)
    public void abrirSeleccionarIdCliente(String accion) {
        if (frmModuloClientes != null) {
            frmModuloClientes.setVisible(false);
        }
        if (frmSeleccionarId == null) {
            frmSeleccionarId = new FrmSeleccionarId(this);
        }
        frmSeleccionarId.setAccion(accion);
        frmSeleccionarId.setVisible(true);
        frmSeleccionarId.toFront();
    }

    public void regresarDesdeSeleccionarId() {
        if (frmSeleccionarId != null) {
            frmSeleccionarId.setVisible(false);
        }
        if (frmModuloClientes == null) {
            frmModuloClientes = new FrmModuloClientes(this);
        }
        frmModuloClientes.setVisible(true);
        frmModuloClientes.toFront();
    }

    // Modificar
    public void abrirModificarCliente(Long id) {
        try {
            ClienteFrecuenteDTO dto = (ClienteFrecuenteDTO) clienteBO.obtenerPorId(id);
            if (dto == null) {
                JOptionPane.showMessageDialog(frmSeleccionarId, "No se encontró ningún cliente con ID: " + id,
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (frmSeleccionarId != null) {
                frmSeleccionarId.setVisible(false);
            }
            if (frmModificarCliente == null) {
                frmModificarCliente = new FrmModificarCliente(this, clienteBO);
            }
            frmModificarCliente.cargarDatos(dto);
            frmModificarCliente.setVisible(true);
            frmModificarCliente.toFront();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(frmSeleccionarId,
                    ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void regresarDesdeModificarCliente() {
        if (frmModificarCliente != null) {
            frmModificarCliente.setVisible(false);
        }
        if (frmModuloClientes == null) {
            frmModuloClientes = new FrmModuloClientes(this);
        }
        frmModuloClientes.setVisible(true);
        frmModuloClientes.toFront();
    }

    // Eliminar
    public void eliminarCliente(Long id) {
        try {
            ClienteDTO dto = clienteBO.obtenerPorId(id);
            if (dto == null) {
                JOptionPane.showMessageDialog(frmSeleccionarId, "No se entro el cliente con el "
                        + "id indicado", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int respuesta = JOptionPane.showConfirmDialog(frmSeleccionarId,
                    "¿Seguro de que desea eliminar al cliente?\n"
                    + "Nombre: " + dto.getNombres() + " " + dto.getApellidoPaterno(),
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (respuesta == JOptionPane.YES_OPTION) {
                clienteBO.eliminar(id);
                if (frmSeleccionarId != null) {
                    frmSeleccionarId.setVisible(false);
                }
                mostrarExito("El Cliente con ID: " + id + " fue\neliminado con éxito", "eliminar_cliente");
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(frmSeleccionarId,
                    ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Buscador 
    public void abrirBuscadorClientes() {
        if (frmModuloClientes != null) {
            frmModuloClientes.setVisible(false);
        }
        if (frmBuscadorClientes == null) {
            frmBuscadorClientes = new FrmBuscadorClientes(this, clienteBO);
        }
        frmBuscadorClientes.cargarTodos();
        frmBuscadorClientes.setVisible(true);
        frmBuscadorClientes.toFront();
    }

    public void regresarDesdeBuscadorClientes() {
        if (frmBuscadorClientes != null) {
            frmBuscadorClientes.setVisible(false);
        }
        if (frmModuloClientes == null) {
            frmModuloClientes = new FrmModuloClientes(this);
        }
        frmModuloClientes.setVisible(true);
        frmModuloClientes.toFront();
    }

    // Info adicional 
    public void abrirInfoAdicionalCliente(ClienteFrecuenteDTO dto) {
        if (frmBuscadorClientes != null) {
            frmBuscadorClientes.setVisible(false);
        }
        if (frmInfoAdicional == null) {
            frmInfoAdicional = new FrmInfoAdicional(this);
        }
        frmInfoAdicional.cargarDatos(dto);
        frmInfoAdicional.setVisible(true);
        frmInfoAdicional.toFront();
    }

    public void regresarDesdeInfoAdicional() {
        if (frmInfoAdicional != null) {
            frmInfoAdicional.setVisible(false);
        }
        if (frmBuscadorClientes == null) {
            frmBuscadorClientes = new FrmBuscadorClientes(this, clienteBO);
        }
        frmBuscadorClientes.cargarTodos();
        frmBuscadorClientes.setVisible(true);
        frmBuscadorClientes.toFront();
    }

    // Éxito 
    /**
     * Muestra la pantalla de éxito con un mensaje personalizado.
     *
     * @param mensaje texto a mostrar
     * @param origen clave del flujo: "registro_cliente", "eliminar_cliente",
     * "modificar_cliente"
     */
    public void mostrarExito(String mensaje, String origen) {
        if (frmExito == null) {
            frmExito = new FrmExito(this);
        }
        frmExito.configurar(mensaje, origen);
        frmExito.setVisible(true);
        frmExito.toFront();
    }

    /**
     * Maneja la navegación al aceptar la pantalla de éxito.
     *
     * @param origen la clave que indica de dónde venía el flujo
     */
    public void regresarDesdeExito(String origen) {
        if (frmExito != null) {
            frmExito.setVisible(false);
        }
        switch (origen) {
            case "registro_cliente" -> {
                if (frmRegistrarCliente != null) {
                    frmRegistrarCliente.setVisible(false);
                }
                if (frmModuloClientes == null) {
                    frmModuloClientes = new FrmModuloClientes(this);
                }
                frmModuloClientes.setVisible(true);
                frmModuloClientes.toFront();
            }
            case "eliminar_cliente" -> {
                if (frmSeleccionarId != null) {
                    frmSeleccionarId.setVisible(false);
                }
                if (frmModuloClientes == null) {
                    frmModuloClientes = new FrmModuloClientes(this);
                }
                frmModuloClientes.setVisible(true);
                frmModuloClientes.toFront();
            }
            case "modificar_cliente" -> {
                if (frmModificarCliente != null) {
                    frmModificarCliente.setVisible(false);
                }
                if (frmModuloClientes == null) {
                    frmModuloClientes = new FrmModuloClientes(this);
                }
                frmModuloClientes.setVisible(true);
                frmModuloClientes.toFront();
            }

            case "registro_ingrediente" -> {
                if (frmRegistrarIngrediente != null) {
                    frmRegistrarIngrediente.setVisible(false);
                }
                if (frmModuloIngredientes == null) {
                    frmModuloIngredientes = new FrmModuloIngredientes(this);
                }
                frmModuloIngredientes.setVisible(true);
                frmModuloIngredientes.toFront();
            }
            case "modificar_ingrediente" -> {
                if (frmModificarIngrediente != null) {
                    frmModificarIngrediente.setVisible(false);
                }
                if (frmModuloIngredientes == null) {
                    frmModuloIngredientes = new FrmModuloIngredientes(this);
                }
                frmModuloIngredientes.setVisible(true);
                frmModuloIngredientes.toFront();
            }

            default -> {
                if (frmModuloClientes == null) {
                    frmModuloClientes = new FrmModuloClientes(this);
                }
                frmModuloClientes.setVisible(true);
                frmModuloClientes.toFront();
            }
        }
    }

    // Cliente general
    /**
     * Registra el cliente general predeterminado desde cualquier parte del
     * sistema.
     */
    public void registrarClienteGeneral() {
        try {
            clienteBO.registrarClienteGeneral();
            JOptionPane.showMessageDialog(null,
                    "Cliente General registrado con éxito.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void abrirModuloIngredientes() {
        if (frmModulos != null) {
            frmModulos.setVisible(false);
        }
        if (frmModuloIngredientes == null) {
            frmModuloIngredientes = new FrmModuloIngredientes(this);
        }
        frmModuloIngredientes.setVisible(true);
        frmModuloIngredientes.toFront();
    }

    /**
     * Método que abre la pantalla del buscador de productos
     */
    public void abrirModuloProductos() {
        this.origen = "Modulos";
        if (frmModulos != null) {
            frmModulos.setVisible(false);
        }

        if (frmBuscadorProductos == null) {
            frmBuscadorProductos = new FrmBuscadorProductos(this);
        }

        frmBuscadorProductos.setVisible(true);
        frmBuscadorProductos.toFront();
    }

    //modulo comandas

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
    
    public void agregarObserver(MesaObserver obs){
        this.observer.add(obs);
    }
    
    public void comandaObserver(ComandaObserver obs){
        this.obser.add(obs);
    }
    
    public void notiCambioMesas() throws NegocioException{
        for(MesaObserver obs : observer){
            try{
                obs.actualizarMesas();
            }catch(NegocioException ex){
                ex.printStackTrace();
            }
        }
    }
    
    public void notiCambioComanda() throws NegocioException{
        for(ComandaObserver obs : obser){
            obs.comandaObserver();
        }
    }
    
    public List<MesaDTO> obtenerMesas() throws NegocioException{
        //return comandaBO.consulatarMesasDisponibles();
        return comandaBO.consultarTodasMesas();
    }
    
    public List<ComandaDTO> obtenerComandasAbiertas() throws PersistenciaException{
        return comandaBO.obtenerComandasAbiertas();
    }
    
    public List<ComandaDTO> obtenerComandasEntregadas() throws PersistenciaException{
        return comandaBO.obtenerComandasEntregadas();
    }
    
    public List<ComandaDTO> obtenerComandas() throws PersistenciaException{
        return comandaBO.obtenerComandas();
    }
    
    public String actualizarComanda(ComandaDTO dto) {
        try {
            comandaBO.actualizar(dto);
            notiCambioComanda();
            return null;
        } catch (NegocioException e) {
            return e.getMessage();
        }
    }
    
    public String obtenerFolio() throws NegocioException{
        try{
            return comandaBO.generarFolio();
        }catch(PersistenciaException e){
            return "FOl-000";
        }
    }
    
    public void abrirSeleccionadorMesa() throws NegocioException {
        if (frmModuloComandas != null) {
            frmModuloComandas.setVisible(false);
        }
        if (frmSeleccionarMesa == null) {
            frmSeleccionarMesa = new FrmSeleccionarMesa(this);
        }
        frmSeleccionarMesa.setVisible(true);
        frmSeleccionarMesa.toFront();
    }
    
    public void abrirSeleccionarCliente(MesaDTO mesa){
        this.mesa = mesa;
        if(frmSeleccionarMesa != null){
            frmSeleccionarMesa.setVisible(false);
        }
        if (frmBuscadorClientesComanda == null){
            frmBuscadorClientesComanda = new FrmBuscadorClientesComanda(this, clienteBO);
        }
        frmBuscadorClientesComanda.setVisible(true);
        frmBuscadorClientesComanda.toFront();
    }
    
    public void abrirNuevaComanda(ClienteFrecuenteDTO cliente) throws NegocioException{
        this.comanda = "registrar";
        if(frmSeleccionarMesa != null){
            frmSeleccionarMesa.setVisible(false);
        }
        if(frmComanda == null){
            frmComanda = new FrmComanda(this);
        }
        frmComanda.setVisible(true);
        frmComanda.cargarDatos(mesa, cliente);
        frmComanda.toFront();
    }
    
    public void agregarProductoAComanda(ProductoDTO producto) {
        if ("Modulos".equals(this.origen)) {
            JOptionPane.showMessageDialog(null, "Opcion no valida");
            this.mostrarModulos();

        } else {
            if ("registrar".equals(this.comanda)) {
                if (frmComanda != null) {
                    frmComanda.agregarProducto(producto);
                    frmComanda.setVisible(true);
                    frmComanda.toFront();
                }
            } else {
                if (frmModificar != null) {
                    frmModificar.agregarProducto(producto);
                    frmModificar.setVisible(true);
                    frmModificar.toFront();
                }
            }
        }
    }
    
    public void guardarComanda(ComandaDTO comanda) throws NegocioException {
        try {
            comandaBO.registrar(comanda);
            JOptionPane.showMessageDialog(null, "¡Registrada con éxito!");
            notiCambioMesas();
            this.rolMeseroSeleccionado();
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, "Error, no se pudo guardar: "+e.getMessage());
        }
    }
    
    public void abrirModificadorComanda() throws PersistenciaException{
        if (frmModuloComandas != null) {
            frmModuloComandas.setVisible(false);
        }
        
        if(frmModificarComanda == null){
            frmModificarComanda = new FrmModificarComanda(this);
        }
        
        List<ComandaDTO> list = comandaBO.obtenerComandasAbiertas();
        
        frmModificarComanda.cargarTabla(list);
        
        frmModificarComanda.setVisible(true);
        frmModificarComanda.toFront();
    }
    
    public void abrirEdicionComanda(String folio){
        try{
            this.comanda = "modificar";
            ComandaDTO  dto = comandaBO.obtenerPorFolio(folio);
            if (dto == null) {
                JOptionPane.showMessageDialog(null, "Comanda no encontrado");
                return;
            }
            
            if (frmModificarComanda != null) {
                frmModificarComanda.setVisible(false);
            }
            
            if(frmModificar == null){
                frmModificar = new FrmModificar(this);
            }
            frmModificar.cargarDatos(dto);
            frmModificar.setVisible(true);
            frmModificar.toFront();
        }catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar producto");
        }
    }
    
    public void abrirBuscadorComanda() throws PersistenciaException{
        if (frmModuloComandas != null) {
            frmModuloComandas.setVisible(false);
        }
        if(frmBuscadorComanda == null){
            frmBuscadorComanda = new FrmBuscadorComanda(this);
        }
        frmBuscadorComanda.setVisible(true);
        frmBuscadorComanda.toFront();
    }
    
    public List<ComandaDTO> buscarComandas(Integer numeroMesa, EstadoComandaDTO estadoComanda,LocalDateTime inicio, LocalDateTime fin) {
        try {
            return comandaBO.buscarComanda(numeroMesa, estadoComanda, inicio, fin);
        }catch (NegocioException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al buscar comandas: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public List<ComandaDTO> buscarComandasEntregadas(Integer numeroMesa, LocalDateTime inicio, LocalDateTime fin, String cliente) {
        try {
            return comandaBO.buscarComanda(numeroMesa, inicio, fin, cliente);
        }catch (NegocioException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al buscar comandas: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public Double calcularTotalComandas(List<ComandaDTO> comandas) {
        return comandaBO.calcularTotalComandas(comandas);
    }
    
    public ProductoDTO obtenerProductoPorId(Long id) throws NegocioException{
        return productoBO.obtenerPorId(id);
    }
    public void abrirBuscadorProductosComanda() {
        this.origen = "Comanda";
        if (frmComanda != null) {
            frmComanda.setVisible(false);
        }
        if (frmBuscadorProductos == null) {
            frmBuscadorProductos = new FrmBuscadorProductos(this);
        }
        frmBuscadorProductos.setVisible(true);
        frmBuscadorProductos.toFront();
    }


    //MÓDULO DE PRODUCTOS
    public void abrirBuscadorProductos() {
        if (frmModulos != null) {
            frmModulos.setVisible(false);
        }
        if (frmBuscadorProductos == null) {
            frmBuscadorProductos = new FrmBuscadorProductos(this);
        }
        frmBuscadorProductos.setVisible(true);
        frmBuscadorProductos.toFront();
    }

    public void regresarDesdeBuscadorProductos() {
        if (frmBuscadorProductos != null) {
            frmBuscadorProductos.setVisible(false);
        }
        if (origen.equals("Modulos")) {
            if (frmModulos == null) {
                frmModulos = new FrmModulos(this);
            }
            frmModulos.setVisible(true);
            frmModulos.toFront();
        } else {
            if (frmComanda == null) {
                frmComanda = new FrmComanda(this);
            }
            frmComanda.setVisible(true);
            frmComanda.toFront();
        }
    }

    /**
     * Método que abre la pantalla de registrar producto
     */
    public void abrirRegistrarProducto() {
        if (frmBuscadorProductos != null) {
            frmBuscadorProductos.setVisible(false);
        }

        if (frmRegistrarProducto == null) {
            frmRegistrarProducto = new FrmRegistrarProducto(this);
        }

        frmRegistrarProducto.limpiar();
        frmRegistrarProducto.setVisible(true);
        frmRegistrarProducto.toFront();
    }
    
    /**
     *
     * @param id
     */
    public void abrirModificarProducto(Long id) {
        try {
            ProductoDTO dto = productoBO.obtenerPorId(id);

            if (dto == null) {
                JOptionPane.showMessageDialog(null, "Producto no encontrado");
                return;
            }
            if (frmBuscadorProductos != null) {
                frmBuscadorProductos.setVisible(false);
            }
            if (frmRegistrarProducto == null) {
                frmRegistrarProducto = new FrmRegistrarProducto(this);
            }
            frmRegistrarProducto.limpiar();
            frmRegistrarProducto.cargarProducto(dto);
            frmRegistrarProducto.setVisible(true);
            frmRegistrarProducto.toFront();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar producto");
            
        }
    }
    
    
    /**
     * Método que regresa a la pantalla de buscador de productos
     */
    public void regresarDesdeRegistrarProducto() {
        if (frmRegistrarProducto != null) {
            frmRegistrarProducto.setVisible(false);
        }

        if (frmBuscadorProductos == null) {
            frmBuscadorProductos = new FrmBuscadorProductos(this);
        }

        frmBuscadorProductos.setVisible(true);
        frmBuscadorProductos.toFront();
    }
    
    /**
     * Método que registra un producto mandando a llamar al BO
     * @param dto el dto del producto a crear/agregar
     * @return un mensaje si ocurre una excepción, nulo en caso contrario
     */
    public String registrarProducto(ProductoDTO dto) {
        try {
            productoBO.registrarProducto(dto);
            return null;
        } catch (NegocioException e) {
            return e.getMessage();
        }
    }


    private Long idProductoSeleccionado;
    
    public Long getIdProductoSeleccionado() {
        return idProductoSeleccionado;
    }

    public List<ProductoDTO> obtenerProductos() {
        return productoBO.obtenerTodos();
    }

    public List<ProductoDTO> buscarProductos(String nombre, String tipo) {
        try {
            return productoBO.buscarProductos(nombre, tipo);
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al buscar productos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean eliminarProducto(Long id) {
        try {
            productoBO.cambiarEstado(id, "INACTIVO");
            return true;
        } catch (NegocioException e) {
            LOG.severe("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
    
    public String actualizarProducto(ProductoDTO dto) {
        try {
            productoBO.actualizarProducto(dto);
            return null;
        } catch (NegocioException e) {
            return e.getMessage();
        }
    }
    

    //MÓDULO DE REPORTES
    
    /**
     * Método que abre la pantalla del módulo de reportes
     */
    public void abrirModuloReportes() {
        if (frmModulos != null) {
            frmModulos.setVisible(false);
        }
        if (frmModuloReportes == null) {
            frmModuloReportes = new FrmModuloReportes(this);
        }
        frmModuloReportes.setVisible(true);
        frmModuloReportes.toFront();
    }

    /**
     * Método que regresa desde el módulo de reportes
     */
    public void regresarDesdeModuloReportes() {
        if (frmModuloReportes != null) {
            frmModuloReportes.setVisible(false);
        }

        if (frmModulos == null) {
            frmModulos = new FrmModulos(this);
        }
        frmModulos.setVisible(true);
        frmModulos.toFront();
    }

    public void abrirReporteClientes() {
        if (frmModuloReportes != null) {
            frmModuloReportes.setVisible(false);
        }
        if (frmReporteClientes == null) {
            frmReporteClientes = new FrmReporteClientesFrecuentes(this);
        }
        frmReporteClientes.setVisible(true);
        frmReporteClientes.toFront();
    }

    public void abrirReporteComandas() throws PersistenciaException {
        if (frmModuloReportes != null) {
            frmModuloReportes.setVisible(false);
        }
        if(frmReporteComandas == null){
            frmReporteComandas = new FrmReporteComandas(this);
        }
        frmReporteComandas.setVisible(true);
        frmReporteComandas.toFront();
    }

    public void regresarDesdeReporteClientes() {
        if (frmReporteClientes != null) {
            frmReporteClientes.setVisible(false);
        }

        if (frmModuloReportes == null) {
            frmModuloReportes = new FrmModuloReportes(this);
        }
        frmModuloReportes.setVisible(true);
        frmModuloReportes.toFront();
    }
    
    public void regresarDesdeReporteComandas() {
        if (frmReporteComandas != null) {
            frmReporteComandas.setVisible(false);
        }

        if (frmModuloReportes == null) {
            frmModuloReportes = new FrmModuloReportes(this);
        }
        frmModuloReportes.setVisible(true);
        frmModuloReportes.toFront();
    }
    /**
     * Método que genera un reporte de clientes según los filtros
     * @param nombre el nombre del cliente
     * @param minVisitas el mínimo de visitas 
     * @return una lista de clientes frecuentes con datos específicos
     */
    public List<ReporteClienteFrecuenteDTO> obtenerReporteClientes(String nombre, Integer minVisitas) {
        try {
            return clienteBO.obtenerReporte(nombre, minVisitas);
        } catch (NegocioException e) {
            return new ArrayList<>();
        }
    }
    /**
     * Método que genera el reporte en pdf de clientes frecuentes
     * @param datos la lista de clientesFrecuntes generada por el método obtenerReporte(nombre, minVisitas)
     * @return el documento ya llenado
     * @throws NegocioException si ocurre un error al llamar al BO
     */
    public JasperPrint generarReporteClientesFrecuentes(List<ReporteClienteFrecuenteDTO> datos) throws NegocioException {
        return clienteBO.generarReporteClientesFrecuentes(datos);
    }
    
    public JasperPrint generarReporteComandas(List<ComandaDTO> datos) throws NegocioException{
        return comandaBO.generarReporteComandas(datos);
    }

    // MODULO INGREDIENTES
    
    
    /**
     * Regresa desde el módulo de ingredientes al menú de módulos.
     */
    public void regresarDesdeModuloIngredientes() {
        if (frmModuloIngredientes != null) {
            frmModuloIngredientes.setVisible(false);
        }
        if (frmModulos == null) {
            frmModulos = new FrmModulos(this);
        }
        frmModulos.setVisible(true);
        frmModulos.toFront();
    }

    /**
     * Abre el formulario de registro de ingrediente.
     */
    public void abrirRegistrarIngrediente() {
        if (frmModuloIngredientes != null) {
            frmModuloIngredientes.setVisible(false);
        }
        if (frmRegistrarIngrediente == null) {
            frmRegistrarIngrediente = new FrmRegistrarIngrediente(this, ingredienteBO);
        }
        frmRegistrarIngrediente.limpiar();
        frmRegistrarIngrediente.setVisible(true);
        frmRegistrarIngrediente.toFront();
    }

    /**
     * Regresa desde el formulario de registro al menú de ingredientes.
     */
    public void regresarDesdeRegistrarIngrediente() {
        if (frmRegistrarIngrediente != null) {
            frmRegistrarIngrediente.setVisible(false);
        }
        if (frmModuloIngredientes == null) {
            frmModuloIngredientes = new FrmModuloIngredientes(this);
        }
        frmModuloIngredientes.setVisible(true);
        frmModuloIngredientes.toFront();
    }

    /**
     * Abre la pantalla de selección de ID para modificar un ingrediente.
     */
    public void abrirSeleccionarIdIngrediente() {
        if (frmModuloIngredientes != null) {
            frmModuloIngredientes.setVisible(false);
        }
        if (frmSeleccionarId == null) {
            frmSeleccionarId = new FrmSeleccionarId(this);
        }
        frmSeleccionarId.setAccion("modificar_ingrediente");
        frmSeleccionarId.setVisible(true);
        frmSeleccionarId.toFront();
    }

    /**
     * Regresa desde la selección de ID al menú de ingredientes.
     */
    public void regresarDesdeSeleccionarIdIngrediente() {
        if (frmSeleccionarId != null) {
            frmSeleccionarId.setVisible(false);
        }
        if (frmModuloIngredientes == null) {
            frmModuloIngredientes = new FrmModuloIngredientes(this);
        }
        frmModuloIngredientes.setVisible(true);
        frmModuloIngredientes.toFront();
    }

    /**
     * Carga el ingrediente con el id dado y abre el formulario de modificación.
     * Si no existe, muestra un mensaje de error y regresa a selección de ID.
     *
     * @param id el id del ingrediente a modificar
     */
    public void abrirModificarIngrediente(Long id) {
        try {
            IngredienteDTO dto = ingredienteBO.buscarPorId(id);
            if (frmSeleccionarId != null) {
                frmSeleccionarId.setVisible(false);
            }
            if (frmModificarIngrediente == null) {
                frmModificarIngrediente = new FrmModificarIngrediente(this, ingredienteBO);
            }
            frmModificarIngrediente.cargarDatos(dto);
            frmModificarIngrediente.setVisible(true);
            frmModificarIngrediente.toFront();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(frmSeleccionarId,
                    "No se encontró ningún ingrediente con ID: " + id,
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Regresa desde el formulario de modificación al menú de ingredientes.
     */
    public void regresarDesdeModificarIngrediente() {
        if (frmModificarIngrediente != null) {
            frmModificarIngrediente.setVisible(false);
        }
        if (frmModuloIngredientes == null) {
            frmModuloIngredientes = new FrmModuloIngredientes(this);
        }
        frmModuloIngredientes.setVisible(true);
        frmModuloIngredientes.toFront();
    }

    /**
     * Abre el buscador de ingredientes en modo standalone (consulta).
     */
    public void abrirBuscadorIngredientes() {
        if (frmBuscadorIngredientes == null) {
            frmBuscadorIngredientes = new FrmBuscadorIngredientes(this, ingredienteBO);
        }
        frmBuscadorIngredientes.abrirModoConsulta();
        frmBuscadorIngredientes.setVisible(true);
        frmBuscadorIngredientes.toFront();
    }

    public void abrirBuscadorIngredientesSeleccion(Consumer<IngredienteDTO> callback) {
        this.callbackIngrediente = callback;

        if (frmBuscadorIngredientes == null) {
            frmBuscadorIngredientes = new FrmBuscadorIngredientes(this, ingredienteBO);
        }

        frmBuscadorIngredientes.abrirModoSeleccion(ingrediente -> {
            if (callbackIngrediente != null) {
                callbackIngrediente.accept(ingrediente);
            }
        });

        frmBuscadorIngredientes.setVisible(true);
        frmBuscadorIngredientes.toFront();
    }

    /**
     * Regresa desde el buscador de ingredientes al menú de ingredientes.
     */
    public void regresarDesdeBuscadorIngredientes() {
        if (frmBuscadorIngredientes != null) {
            frmBuscadorIngredientes.setVisible(false);
        }
        if (frmModuloIngredientes == null) {
            frmModuloIngredientes = new FrmModuloIngredientes(this);
        }
        frmModuloIngredientes.setVisible(true);
        frmModuloIngredientes.toFront();
    }
}
