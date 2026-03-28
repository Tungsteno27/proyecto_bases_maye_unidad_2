/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import EstilosGUI.UI;
import coordinador.Coordinador;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Pantalla que solicita el ID del cliente para luego proceder a modificarlo o
 * eliminarlo según la acción indicada.
 *
 * @author Noelia
 */
public class FrmSeleccionarId extends JFrame {

    private final Coordinador coordinador;
    /**
     * "modificar" o "eliminar"
     */
    private String accion;
    private JTextField txtId;

    public FrmSeleccionarId(Coordinador coordinador) {
        this.coordinador = coordinador;
        initUI();
    }

    private void initUI() {
        setTitle("Seleccionar ID");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setSize(380, 280);
        setLocationRelativeTo(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.regresarDesdeSeleccionarId();
            }
        });

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card(300, 210);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(36, 46, 36, 46));

        JLabel lblTitulo = new JLabel("Ingrese el id del Cliente:", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Georgia", Font.PLAIN, 16));
        lblTitulo.setForeground(UI.TEXTO_OSCURO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblTitulo);

        card.add(Box.createVerticalStrut(20));

        txtId = new JTextField(10);
        txtId.setFont(new Font("Georgia", Font.PLAIN, 18));
        txtId.setForeground(UI.TEXTO_OSCURO);
        txtId.setBackground(UI.AZUL_NORMAL.brighter());
        txtId.setHorizontalAlignment(JTextField.CENTER);
        txtId.setMaximumSize(new Dimension(160, 38));
        txtId.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(txtId);

        card.add(Box.createVerticalStrut(24));

        JPanel botones = new JPanel(new GridLayout(1, 2, 14, 0));
        botones.setOpaque(false);
        botones.setMaximumSize(new Dimension(260, 44));
        botones.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnAtras = botonRojo("Atrás");
        btnAtras.addActionListener(e -> coordinador.regresarDesdeSeleccionarId());

        JButton btnAceptar = botonVerde("Aceptar");
        btnAceptar.addActionListener(e -> aceptar());

        botones.add(btnAtras);
        botones.add(btnAceptar);
        card.add(botones);

        fondo.add(card);
    }

    private void aceptar() {
        String texto = txtId.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Long id;
        try {
            id = Long.parseLong(texto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if ("modificar".equals(accion)) {
            coordinador.abrirModificarCliente(id);
        } else if ("eliminar".equals(accion)) {
            coordinador.eliminarCliente(id);
        }
    }

    /**
     * Configura la acción (modificar / eliminar) y limpia el campo.
     */
    public void setAccion(String accion) {
        this.accion = accion;
        txtId.setText("");
    }

    private JButton botonRojo(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Georgia", Font.BOLD, 14));
        btn.setForeground(java.awt.Color.WHITE);
        btn.setBackground(new java.awt.Color(0xC0392B));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton botonVerde(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Georgia", Font.BOLD, 14));
        btn.setForeground(java.awt.Color.WHITE);
        btn.setBackground(new java.awt.Color(0x27AE60));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return btn;
    }

}
