/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import EstilosGUI.UI;
import coordinador.Coordinador;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Pantalla genérica de éxito que muestra un mensaje personalizable y un ícono
 * de palomita verde. Se reutiliza para registro, eliminación y modificación de
 * clientes.
 *
 * @author Noelia
 */
public class FrmExito extends JFrame {
    private final Coordinador coordinador;
    private final JLabel lblMensaje;

    private String origen;

    public FrmExito(Coordinador coordinador) {
        this.coordinador = coordinador;
        lblMensaje = new JLabel("", SwingConstants.CENTER);
        initUI();
    }

   private void initUI() {
        setTitle("Éxito");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                aceptar();
            }
        });

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card();
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        JPanel palomita = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int size = Math.min(getWidth(), getHeight()) - 10;
                g2.setColor(new Color(0x27AE60));
                g2.fillOval(5, 5, size, size);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(size / 4, size / 2, size / 2, (int)(size * 0.75));
                g2.drawLine(size / 2, (int)(size * 0.75), (int)(size * 0.8), size / 3);
                g2.dispose();
            }
        };
        palomita.setOpaque(false);
        palomita.setPreferredSize(new Dimension(80, 80));
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 20, 10);
        card.add(palomita, gbc);

        lblMensaje.setFont(new Font("Georgia", Font.BOLD, 16));
        lblMensaje.setForeground(UI.TEXTO_OSCURO);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 20, 20);
        card.add(lblMensaje, gbc);

        JButton btnAceptar = UI.boton("Aceptar", new Color(0x27AE60), new Color(0x1E8449));
        btnAceptar.setPreferredSize(new Dimension(160, 45));
        btnAceptar.addActionListener(e -> aceptar());
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 10, 10);
        card.add(btnAceptar, gbc);

        UI.centrar(fondo, card);
    }

    public void configurar(String mensaje, String origen) {
        this.origen = origen;
        lblMensaje.setText(mensaje);
    }

    private void aceptar() {
        coordinador.regresarDesdeExito(origen);
    }
}
