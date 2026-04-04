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

    private void initUI() {
        setTitle("Maye's Family Diner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card();

        GridBagConstraints gbc = UI.gbcBase(0, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblNombre = new JLabel("Maye's Family Diner", SwingConstants.CENTER);
        lblNombre.setFont(new Font("Georgia", Font.BOLD, 24));
        lblNombre.setForeground(UI.BORDE_ORO);

        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 20, 10);
        card.add(lblNombre, gbc);

        JLabel lblRol = UI.titulo("Indique su rol");
        lblRol.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy++;
        gbc.insets = new Insets(10, 10, 20, 10);
        card.add(lblRol, gbc);
        
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;

        JButton btnMesero = UI.botonPrimario("Mesero");
        btnMesero.setPreferredSize(new Dimension(220, 45));
        btnMesero.addActionListener(e -> coordinador.rolMeseroSeleccionado());
        card.add(btnMesero, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton btnAdmin = UI.botonPrimario("Administrador");
        btnAdmin.setPreferredSize(new Dimension(220, 45));
        btnAdmin.addActionListener(e -> coordinador.rolAdministradorSeleccionado());
        card.add(btnAdmin, gbc);

        UI.centrar(fondo, card);
    }
}
