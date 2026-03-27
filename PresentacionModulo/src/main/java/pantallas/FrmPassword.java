/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import EstilosGUI.UI;
import coordinador.Coordinador;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Tungs
 */
public class FrmPassword extends JFrame{
     private final Coordinador    coordinador;
    private final JPasswordField txtPassword;
    private final JLabel         lblError;
 
    public FrmPassword(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.txtPassword = UI.passwordField();
        this.lblError    = new JLabel(" ", SwingConstants.CENTER);
        initUI();
    }
    
    /**
     * Método que 
     */
    private void initUI() {
        setTitle("Maye's Family Diner — Contraseña");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setSize(460, 360);
        setLocationRelativeTo(null);
 
        //regresa a la pantalla anterior usando al cordinador
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.regresarDesdePassword();
            }
        });
 
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);
 
        JPanel card = UI.card(360, 280);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(40, 46, 40, 46));
 
        //El título del frame
        JLabel lblTitulo = new JLabel("Ingrese la contraseña:", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Georgia", Font.PLAIN, 19));
        lblTitulo.setForeground(UI.TEXTO_OSCURO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblTitulo);
 
        card.add(Box.createVerticalStrut(24));
 
        // Campo de la contraseña
        txtPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtPassword.setMaximumSize(new Dimension(280, 46));
        // Enter también acepta
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) aceptar();
            }
        });
        card.add(txtPassword);
 
        card.add(Box.createVerticalStrut(6));
 
        // Etiqueta de error (oculta por defecto)
        lblError.setFont(new Font("Georgia", Font.PLAIN, 12));
        lblError.setForeground(new java.awt.Color(0xA83232));
        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblError);
 
        card.add(Box.createVerticalStrut(18));
 
        // Botón Aceptar
        JButton btnAceptar = UI.botonAccion("Aceptar");
        btnAceptar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAceptar.setMaximumSize(new Dimension(280, 50));
        btnAceptar.addActionListener(e -> aceptar());
        card.add(btnAceptar);
 
        card.add(Box.createVerticalStrut(12));
 
        // botón cancelar
        JButton btnCancelar = new JButton("← Volver");
        btnCancelar.setFont(new Font("Georgia", Font.PLAIN, 13));
        btnCancelar.setForeground(UI.TEXTO_OSCURO);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancelar.addActionListener(e -> coordinador.regresarDesdePassword());
        card.add(btnCancelar);
 
        fondo.add(card);
    }
    //Por lo menos revisa si se ingresó algo
    private void aceptar() {
        String pass = new String(txtPassword.getPassword()).trim();
        if (pass.isEmpty()) {
            lblError.setText("Ingrese una contraseña.");
            return;
        }
        lblError.setText(" ");
        coordinador.validarPassword(pass);
    }
 
    //Limpia el campo de contraseña
    public void limpiar() {
        txtPassword.setText("");
        lblError.setText(" ");
    }
}
