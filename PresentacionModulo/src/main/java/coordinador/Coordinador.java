/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coordinador;

import pantallas.FrmSeleccionRol;
import pantallas.FrmModulos;
import pantallas.FrmPassword;

/**
 * Clase que coordina el flujo entre pantallas y la lógica de negocio
 * @author Tungs
 */
public class Coordinador {

    //aquí se ponen los frames y objetos a utilizar como los BO's
    private FrmSeleccionRol  frmSeleccionRol;
    private FrmPassword      frmPassword;
    private FrmModulos       frmModulos;
 
    //Método que arranca el sistema
    public void iniciarSistema() {
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
}
