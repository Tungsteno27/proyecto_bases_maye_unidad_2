/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidadesBO;

import excepciones.NegocioException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * el Encriptador es una clase que encripta cosas (y las desencripta también)
 * @author Tungs
 */
public class Encriptador {
    
    //Evidentemente saqué esto del gepeto, aunque reconozco que es un algoritmo simetrico porque nomás maneja 
    //una sola clave para encriptar y desencriptar como se vió en la clase del profe carlos, lógicamente la clave no se
    //debe guardar tal cual en el código, podría guardarla en una variable de entorno desde el powerShell (muy sencillo)
    //pero ocuparía que cualquier persona que corra el código haga lo mismo
    private static final String ALGORITMO = "AES/ECB/PKCS5Padding";
    private static final String CLAVE = "MiClaveSecreta16";

    /**
     * Método que encripta un texto plano
     * @param texto el texto a encriptar
     * @return el texto encriptado
     * @throws NegocioException si no se pudo encriptar
     */
    public static String encriptar(String texto) throws NegocioException {
        try {
            SecretKeySpec clave = new SecretKeySpec(CLAVE.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.ENCRYPT_MODE, clave);
            byte[] textoCifrado = cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(textoCifrado);
        } catch (Exception e) {
            throw new NegocioException("Error al encriptar: " + e.getMessage());
        }
    }
    
    /**
    * Método que desencripta un texto cifrado
    * @param textoCifrado el texto cifrado a desencriptar
    * @return una cadena con eltexto plano
    * @throws NegocioException si no se pudo desencriptar
    */
    public static String desencriptar(String textoCifrado) throws NegocioException {
        try {
            SecretKeySpec clave = new SecretKeySpec(CLAVE.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, clave);
            byte[] bytes = Base64.getDecoder().decode(textoCifrado);
            byte[] textoDescifrado = cipher.doFinal(bytes);
            return new String(textoDescifrado, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new NegocioException("Error al desencriptar: " + e.getMessage());
        }
    }
   
    
}
