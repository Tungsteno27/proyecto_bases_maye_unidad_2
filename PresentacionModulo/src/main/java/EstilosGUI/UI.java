/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EstilosGUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Esta clase define los estilos de la GUI
 * @author Tungs
 */
public class UI {

    public static final Color FONDO        = new Color(0xF5EDD6);
    public static final Color BORDE_ORO    = new Color(0xD4A832);
    public static final Color AZUL_NORMAL  = new Color(0x7BB8D4);
    public static final Color AZUL_HOVER   = new Color(0x5A9BBF);
    public static final Color AZUL_OSCURO  = new Color(0x2C6E8A);
    public static final Color AZUL_OSCURO_HOVER = new Color(0x1E5470);
    public static final Color TEXTO_OSCURO = new Color(0x3D2B0A);
    public static final Color TEXTO_BLANCO = Color.WHITE;


    public static JButton boton(String texto, Color normal, Color hover) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getModel().isRollover() ? hover : normal);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 18, 18));

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Georgia", Font.PLAIN, 17));
        btn.setForeground(TEXTO_BLANCO);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.repaint(); }
            @Override public void mouseExited(MouseEvent e)  { btn.repaint(); }
        });

        return btn;
    }

    public static JButton botonPrimario(String texto) {
        return boton(texto, AZUL_NORMAL, AZUL_HOVER);
    }

    public static JButton botonAccion(String texto) {
        return boton(texto, AZUL_OSCURO, AZUL_OSCURO_HOVER);
    }

    public static JPasswordField passwordField() {
        JPasswordField field = new JPasswordField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(AZUL_NORMAL.brighter());
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 14, 14));

                g2.dispose();
                super.paintComponent(g);
            }
        };

        field.setOpaque(false);
        field.setBorder(new EmptyBorder(8, 14, 8, 14));
        field.setFont(new Font("Monospaced", Font.PLAIN, 18));
        field.setForeground(TEXTO_OSCURO);
        field.setCaretColor(TEXTO_OSCURO);

        return field;
    }

    public static JPanel card() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(FONDO);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 28, 28));

                g2.setColor(BORDE_ORO);
                g2.setStroke(new BasicStroke(2.5f));
                g2.draw(new RoundRectangle2D.Double(1.5, 1.5, getWidth() - 3, getHeight() - 3, 26, 26));

                g2.dispose();
                super.paintComponent(g);
            }
        };

        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));

        return panel;
    }


    public static void centrar(JPanel fondo, JPanel contenido) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        fondo.add(contenido, gbc);
    }

    public static GridBagConstraints gbcBase(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        return gbc;
    }

    public static JLabel titulo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Georgia", Font.BOLD, 15));
        lbl.setForeground(TEXTO_OSCURO);
        return lbl;
    }

    public static JLabel valor() {
        JLabel lbl = new JLabel();
        lbl.setFont(new Font("Georgia", Font.PLAIN, 15));
        lbl.setForeground(TEXTO_OSCURO);
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        return lbl;
    }

    public static JLabel tituloGrande(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(new Font("Georgia", Font.BOLD, 20));
        lbl.setForeground(TEXTO_OSCURO);
        return lbl;
    }
    public static JTextField texto() {
    JTextField field = new JTextField(20) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 14, 14));
            g2.setColor(new Color(0xDCDCDC)); 
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 14, 14));

            g2.dispose();
            super.paintComponent(g);
        }
    };

    field.setOpaque(false);
    field.setBorder(new EmptyBorder(8, 14, 8, 14));
    field.setFont(new Font("Georgia", Font.PLAIN, 15));
    field.setForeground(TEXTO_OSCURO);
    field.setCaretColor(TEXTO_OSCURO);

    return field;
}
}
