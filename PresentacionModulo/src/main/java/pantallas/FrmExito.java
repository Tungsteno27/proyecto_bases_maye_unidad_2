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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
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
    
    // Indica qué acción disparó esta pantalla para regresar al lugar correcto.
    private String origen;

    public FrmExito(Coordinador coordinador) {
        this.coordinador = coordinador;
        lblMensaje = new JLabel(" ", SwingConstants.CENTER);
        initUI();
    }

    private void initUI() {
        setTitle("Éxito");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setSize(380, 280);
        setLocationRelativeTo(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                aceptar();
            }
        });

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card(310, 220);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(28, 36, 28, 36));

        // Mensaje de éxito (puede tener \n que se transforma en HTML)
        lblMensaje.setFont(new Font("Georgia", Font.BOLD, 15));
        lblMensaje.setForeground(UI.TEXTO_OSCURO);
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblMensaje);

        card.add(Box.createVerticalStrut(18));

        // Ícono de palomita dibujado
        JPanel palomita = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Círculo verde
                g2.setColor(new Color(0x27AE60));
                g2.fill(new Ellipse2D.Double(10, 5, 56, 56));
                // Palomita blanca
                g2.setColor(Color.WHITE);
                g2.setStroke(new java.awt.BasicStroke(4f, java.awt.BasicStroke.CAP_ROUND, java.awt.BasicStroke.JOIN_ROUND));
                // Trazo izquierdo de la paloma
                g2.drawLine(20, 35, 30, 46);
                // Trazo derecho de la paloma
                g2.drawLine(30, 46, 52, 24);
                g2.dispose();
            }
        };
        palomita.setOpaque(false);
        palomita.setPreferredSize(new Dimension(76, 66));
        palomita.setMaximumSize(new Dimension(76, 66));
        palomita.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(palomita);

        card.add(Box.createVerticalStrut(18));

        JButton btnAceptar = botonVerde("Aceptar");
        btnAceptar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAceptar.setMaximumSize(new Dimension(160, 42));
        btnAceptar.addActionListener(e -> aceptar());
        card.add(btnAceptar);

        fondo.add(card);
    }

    /**
     * Configura el mensaje y el origen antes de mostrar la pantalla.
     *
     * @param mensaje texto a mostrar (usa HTML automáticamente para saltos de
     * línea)
     * @param origen clave que indica de dónde viene: "registro_cliente",
     * "eliminar_cliente", "modificar_cliente"
     */
    public void configurar(String mensaje, String origen) {
        this.origen = origen;
        // Convertimos saltos de línea a HTML
        String html = "<html><div style='text-align:center'>"
                + mensaje.replace("\n", "<br>") + "</div></html>";
        lblMensaje.setText(html);
    }

    private void aceptar() {
        coordinador.regresarDesdeExito(origen);
    }

    private JButton botonVerde(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Georgia", Font.BOLD, 15));
        btn.setForeground(java.awt.Color.WHITE);
        btn.setBackground(new java.awt.Color(0x27AE60));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return btn;
    }
}
