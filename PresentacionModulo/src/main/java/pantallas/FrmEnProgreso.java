/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import EstilosGUI.UI;
import coordinador.Coordinador;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
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
        setTitle("En mantenimiento");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setSize(400, 300);
        setLocationRelativeTo(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.cerrarSesion();
            }
        });

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card(320, 200);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Texto principal
        JLabel lblMensaje = new JLabel("En progreso", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Georgia", Font.BOLD, 20));
        lblMensaje.setForeground(UI.TEXTO_OSCURO);
        card.add(lblMensaje, BorderLayout.CENTER);

        // Botón volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Georgia", Font.PLAIN, 13));
        btnVolver.setForeground(UI.TEXTO_OSCURO);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnVolver.addActionListener(e -> coordinador.iniciarSistema());

        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(10, 0, 0, 0));
        footer.add(btnVolver);

        card.add(footer, BorderLayout.SOUTH);

        fondo.add(card);
    }
}
