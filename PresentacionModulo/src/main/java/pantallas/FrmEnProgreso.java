/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import EstilosGUI.UI;
import coordinador.Coordinador;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Dayanara Peralta G
 */
public class FrmEnProgreso extends JFrame{
    private final Coordinador coordinador;

    public FrmEnProgreso(Coordinador coordinador) {
        this.coordinador = coordinador;
        iniciar();
    }

    private void iniciar() {
        setTitle("En progreso");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.cerrarSesion();
            }
        });

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card();

        GridBagConstraints gbc = UI.gbcBase(0, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblMensaje = UI.tituloGrande("En progreso");
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 20, 10);
        card.add(lblMensaje, gbc);

        JButton btnVolver = UI.botonAccion("Volver");
        btnVolver.setPreferredSize(new Dimension(140, 40));
        btnVolver.addActionListener(e -> coordinador.iniciarSistema());

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;
        card.add(btnVolver, gbc);

        UI.centrar(fondo, card);
    }
}
