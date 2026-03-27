/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidadesBO;

/**
 * Esta clase tiene métodos que validan entradas
 * @author Tungs
 */
public class Validadores {
    
    //Expresión regular para los telefonos, tienen que ser de 10 dígitos
    public static final String regexTelefono = "\\d{10}";
    
    //Expresión regular para los nombres, todas las letras del abecedario con un mínimo de dos letras
    public static final String regexNombre = "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{2,}";

    //regex del correo, acepta varios símbolos y tiene que llevar el arroba entre el texto
    public static final String regexCorreo = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

    
    /**
     * Método que valida un teléfono
     * @param telefono el telefono a validar
     * @return verdadero si es válido, falso en caso contrario
     */
    public static boolean validarTelefono(String telefono) {
        if (telefono == null) {
            return false;
        }
        return telefono.matches(regexTelefono);
    }

    /**
     * Método que valida un nombre
     * @param nombre el nombre a validar
     * @return  verdadero si es valido, falso en caso contrario
     */
    public static boolean validarNombre(String nombre) {
        if (nombre == null) {
            return false;
        }
        return nombre.matches(regexNombre);
    }

    /**
     * Método que valida un correo
     * @param correo el correo a validar
     * @return verdadero si es válido, falso en caso contrario
     */
    public static boolean validarCorreo(String correo) {
        if (correo == null) {
            return false;
        }
        return correo.matches(regexCorreo);
    }

    
}
