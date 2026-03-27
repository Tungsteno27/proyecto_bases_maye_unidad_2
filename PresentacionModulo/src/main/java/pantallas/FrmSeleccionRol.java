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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Tungs
 */
public class FrmSeleccionRol extends JFrame {
    private final Coordinador coordinador;
 
    public FrmSeleccionRol(Coordinador coordinador) {
        this.coordinador = coordinador;
        initUI();
    }
    /**
     * Método que crea la pantalla, aquí aún no hay lógica de navegación ni nada por ahora
     */
    private void initUI() {
        setTitle("Maye's Family Diner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(480, 420);
        setLocationRelativeTo(null);
 
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);
 
        JPanel card = UI.card(380, 340);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(44, 52, 48, 52));
 
        JLabel lblNombre = new JLabel("Maye's Family Diner", SwingConstants.CENTER);
        lblNombre.setFont(new Font("Georgia", Font.BOLD, 20));
        lblNombre.setForeground(UI.BORDE_ORO);
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblNombre);
 
        card.add(Box.createVerticalStrut(30));
 
        JLabel lblRol = new JLabel("Indique su rol", SwingConstants.CENTER);
        lblRol.setFont(new Font("Georgia", Font.PLAIN, 19));
        lblRol.setForeground(UI.TEXTO_OSCURO);
        lblRol.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblRol);
 
        card.add(Box.createVerticalStrut(26));
 
        JButton btnMesero = UI.botonPrimario("Mesero");
        btnMesero.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMesero.setMaximumSize(new Dimension(280, 50));
        btnMesero.addActionListener(e -> coordinador.rolMeseroSeleccionado());
        card.add(btnMesero);
 
        card.add(Box.createVerticalStrut(14));
 
        JButton btnAdmin = UI.botonPrimario("Administrador");
        btnAdmin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAdmin.setMaximumSize(new Dimension(280, 50));
        btnAdmin.addActionListener(e -> coordinador.rolAdministradorSeleccionado());
        card.add(btnAdmin);
 
        fondo.add(card);
    }
}
