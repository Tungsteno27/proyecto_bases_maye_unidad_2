/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coordinador;

import BOs.ClienteBO;
import DAOs.ClienteDAO;
import DTOs.ClienteFrecuenteDTO;
import excepciones.NegocioException;
import javax.swing.JOptionPane;
import pantallas.FrmBuscadorClientes;
import pantallas.FrmExito;
import pantallas.FrmInfoAdicional;
import pantallas.FrmModificarCliente;
import pantallas.FrmModuloClientes;
import pantallas.FrmSeleccionRol;
import pantallas.FrmModulos;
import pantallas.FrmPassword;
import pantallas.FrmRegistrarCliente;
import pantallas.FrmSeleccionarId;

/**
 * Clase que coordina el flujo entre pantallas y la lógica de negocio
 * @author Tungs
 */
public class Coordinador {

    //aquí se ponen los frames y objetos a utilizar como los BO's
    private FrmSeleccionRol     frmSeleccionRol;
    private FrmPassword         frmPassword;
    private FrmModulos          frmModulos;
 
    
    // Modulo clientes
    private FrmModuloClientes   frmModuloClientes;
    private FrmRegistrarCliente frmRegistrarCliente;
    private FrmSeleccionarId    frmSeleccionarId;
    private FrmModificarCliente frmModificarCliente;
    private FrmBuscadorClientes frmBuscadorClientes;
    private FrmInfoAdicional    frmInfoAdicional;
    private FrmExito            frmExito; 
    
    //
    private ClienteBO clienteBO;
    
    //Método que arranca el sistema
    public void iniciarSistema() {
        clienteBO = new ClienteBO(new ClienteDAO());
        
        if (frmSeleccionRol == null) {
            frmSeleccionRol = new FrmSeleccionRol(this);
        }
        frmSeleccionRol.setVisible(true);
    }
 
   
 
    //Si se eligió el rol de admin se pide contraseña
    public void rolAdministradorSeleccionado() {
        if (frmSeleccionRol != null) frmSeleccionRol.setVisible(false);
 
        if (frmPassword == null) {
            frmPassword = new FrmPassword(this);
        }
        frmPassword.limpiar();
        frmPassword.setVisible(true);
        frmPassword.toFront();
    }
 
    //Si elige mesero lo manda al modulo de comandas (Dayanara)
    public void rolMeseroSeleccionado() {
        // TODO: navegar al módulo de mesero cuando esté listo
    }
 
    //Valida la contraseña ingresada (falta implementar, aún no sé donde guardarla)
    public void validarPassword(String password) {
        // TODO: aquí irá la validación real con el BO
        mostrarModulos();
    }
 
    //Volver de contraseña  seleccion de rol
    public void regresarDesdePassword() {
        if (frmPassword != null) frmPassword.setVisible(false);
 
        if (frmSeleccionRol == null) {
            frmSeleccionRol = new FrmSeleccionRol(this);
        }
        frmSeleccionRol.setVisible(true);
        frmSeleccionRol.toFront();
    }
 
    //muestra los modulos después de ingresar la contraseña
    private void mostrarModulos() {
        if (frmPassword != null) frmPassword.setVisible(false);
 
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
        frmSeleccionRol.setVisible(true);
        frmSeleccionRol.toFront();
    }
    
    // Navegacion modulo clientes
    public void abrirModuloClientes(){
        if (frmModulos != null) frmModulos.setVisible(false);
        if (frmModuloClientes == null) frmModuloClientes = new FrmModuloClientes(this);
        frmModuloClientes.setVisible(true);
        frmModuloClientes.toFront();
    }
    
    public void regresarDesdeModuloClientes(){
        if(frmModuloClientes != null) frmModuloClientes.setVisible(false);
        
        if(frmModulos == null) frmModulos=new FrmModulos(this);
        frmModulos.setVisible(true);
        frmModulos.toFront();
    }
    
    // Registrar
    public void abrirRegistrarCliente() {
        if (frmModuloClientes != null) frmModuloClientes.setVisible(false);
        if (frmRegistrarCliente == null) {
            frmRegistrarCliente = new FrmRegistrarCliente(this, clienteBO);
        }
        frmRegistrarCliente.limpiar();
        frmRegistrarCliente.setVisible(true);
        frmRegistrarCliente.toFront();
    }
    
    public void regresarDesdeRegistrarCliente() {
        if (frmRegistrarCliente != null) frmRegistrarCliente.setVisible(false);
        if (frmModuloClientes == null) frmModuloClientes = new FrmModuloClientes(this);
        frmModuloClientes.setVisible(true);
        frmModuloClientes.toFront();
    }
    
    // Seleccionar ID (para modificar o eliminar)
    public void abrirSeleccionarIdCliente(String accion){
        if (frmModuloClientes != null) frmModuloClientes.setVisible(false);
        if (frmSeleccionarId == null) frmSeleccionarId = new FrmSeleccionarId(this);
        frmSeleccionarId.setAccion(accion);
        frmSeleccionarId.toFront();
    }
    
    public void regresarDesdeSeleccionarId() {
        if (frmSeleccionarId != null) frmSeleccionarId.setVisible(false);
        if (frmModuloClientes == null) frmModuloClientes = new FrmModuloClientes(this);
        frmModuloClientes.setVisible(true);
        frmModuloClientes.toFront();
    }
    
    // Modificar
    public void abrirModificarCliente(Long id){
        try {
            ClienteFrecuenteDTO dto = (ClienteFrecuenteDTO) clienteBO.obtenerPorId(id);
            if (dto == null){
                JOptionPane.showMessageDialog(frmSeleccionarId, "No se encontró ningún cliente con ID: " + id,
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (frmSeleccionarId != null) frmSeleccionarId.setVisible(false);
            if (frmModificarCliente == null) {
                frmModificarCliente = new FrmModificarCliente(this, clienteBO);
            }
            frmModificarCliente.cargarDatos(dto);
            frmModificarCliente.setVisible(true);
            frmModificarCliente.toFront();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(frmSeleccionarId,
                    ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);        }
    }
    
    public void regresarDesdeModificarCliente() {
        if (frmModificarCliente != null) frmModificarCliente.setVisible(false);
        if (frmModuloClientes == null) frmModuloClientes = new FrmModuloClientes(this);
        frmModuloClientes.setVisible(true);
        frmModuloClientes.toFront();
    }
    
    
    // Eliminar
    public void eliminarCliente(Long id) {
        try {
            clienteBO.eliminar(id.intValue());
            if (frmSeleccionarId != null) frmSeleccionarId.setVisible(false);
            mostrarExito("El Cliente con ID: " + id + " fue\neliminado con éxito", "eliminar_cliente");
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(frmSeleccionarId,
                    ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    // Buscador 
    public void abrirBuscadorClientes() {
        if (frmModuloClientes != null) frmModuloClientes.setVisible(false);
        if (frmBuscadorClientes == null) {
            frmBuscadorClientes = new FrmBuscadorClientes(this, clienteBO);
        }
        frmBuscadorClientes.cargarTodos();
        frmBuscadorClientes.setVisible(true);
        frmBuscadorClientes.toFront();
    }
 
    public void regresarDesdeBuscadorClientes() {
        if (frmBuscadorClientes != null) frmBuscadorClientes.setVisible(false);
        if (frmModuloClientes == null) frmModuloClientes = new FrmModuloClientes(this);
        frmModuloClientes.setVisible(true);
        frmModuloClientes.toFront();
    }
 
    // Info adicional 
 
    public void abrirInfoAdicionalCliente(ClienteFrecuenteDTO dto) {
        if (frmBuscadorClientes != null) frmBuscadorClientes.setVisible(false);
        if (frmInfoAdicional == null) frmInfoAdicional = new FrmInfoAdicional(this);
        frmInfoAdicional.cargarDatos(dto);
        frmInfoAdicional.setVisible(true);
        frmInfoAdicional.toFront();
    }
 
    public void regresarDesdeInfoAdicional() {
        if (frmInfoAdicional != null) frmInfoAdicional.setVisible(false);
        if (frmBuscadorClientes == null) frmBuscadorClientes = new FrmBuscadorClientes(this, clienteBO);
        frmBuscadorClientes.cargarTodos();
        frmBuscadorClientes.setVisible(true);
        frmBuscadorClientes.toFront();
    }
 
    // Éxito 
    /**
     * Muestra la pantalla de éxito con un mensaje personalizado.
     * @param mensaje texto a mostrar
     * @param origen clave del flujo: "registro_cliente", "eliminar_cliente", "modificar_cliente"
     */
    public void mostrarExito(String mensaje, String origen) {
        if (frmExito == null) frmExito = new FrmExito(this);
        frmExito.configurar(mensaje, origen);
        frmExito.setVisible(true);
        frmExito.toFront();
    }
 
    /**
     * Maneja la navegación al aceptar la pantalla de éxito.
     * @param origen la clave que indica de dónde venía el flujo
     */
    public void regresarDesdeExito(String origen) {
        if (frmExito != null) frmExito.setVisible(false);
        switch (origen) {
            case "registro_cliente" -> {
                if (frmRegistrarCliente != null) frmRegistrarCliente.setVisible(false);
                if (frmModuloClientes == null) frmModuloClientes = new FrmModuloClientes(this);
                frmModuloClientes.setVisible(true);
                frmModuloClientes.toFront();
            }
            case "eliminar_cliente" -> {
                if (frmSeleccionarId != null) frmSeleccionarId.setVisible(false);
                if (frmModuloClientes == null) frmModuloClientes = new FrmModuloClientes(this);
                frmModuloClientes.setVisible(true);
                frmModuloClientes.toFront();
            }
            case "modificar_cliente" -> {
                if (frmModificarCliente != null) frmModificarCliente.setVisible(false);
                if (frmModuloClientes == null) frmModuloClientes = new FrmModuloClientes(this);
                frmModuloClientes.setVisible(true);
                frmModuloClientes.toFront();
            }
            default -> {
                if (frmModuloClientes == null) frmModuloClientes = new FrmModuloClientes(this);
                frmModuloClientes.setVisible(true);
                frmModuloClientes.toFront();
            }
        }
    }
 

    // Cliente general
    /**
     * Registra el cliente general predeterminado desde cualquier parte del sistema.
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
}
    

