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
    private final Coordinador coordinador;
    private final JPasswordField txtPassword;
    private final JLabel lblError;

    public FrmPassword(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.txtPassword = UI.passwordField();
        this.lblError = new JLabel("", SwingConstants.CENTER);
        initUI();
    }

    private void initUI() {
        setTitle("Maye's Family Diner — Contraseña");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.regresarDesdePassword();
            }
        });

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card();

        GridBagConstraints gbc = UI.gbcBase(0, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblTitulo = UI.tituloGrande("Ingrese la contraseña");
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 20, 10);
        card.add(lblTitulo, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtPassword.setPreferredSize(new Dimension(280, 45));

        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) aceptar();
            }
        });

        card.add(txtPassword, gbc);

        gbc.gridy++;
        lblError.setFont(new Font("Georgia", Font.PLAIN, 13));
        lblError.setForeground(new Color(0xC0392B));
        card.add(lblError, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(15, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;

        JButton btnAceptar = UI.botonAccion("Aceptar");
        btnAceptar.setPreferredSize(new Dimension(180, 45));
        btnAceptar.addActionListener(e -> aceptar());
        card.add(btnAceptar, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton btnVolver = UI.boton("Volver", UI.AZUL_OSCURO, UI.AZUL_OSCURO_HOVER);
        btnVolver.setPreferredSize(new Dimension(140, 40));
        btnVolver.addActionListener(e -> coordinador.regresarDesdePassword());

        card.add(btnVolver, gbc);

        UI.centrar(fondo, card);
    }

    private void aceptar() {
        String pass = new String(txtPassword.getPassword()).trim();

        if (pass.isEmpty()) {
            lblError.setText("Ingrese una contraseña.");
            return;
        }

        lblError.setText("");
        coordinador.validarPassword(pass);
    }

    public void limpiar() {
        txtPassword.setText("");
        lblError.setText("");
    }
}
