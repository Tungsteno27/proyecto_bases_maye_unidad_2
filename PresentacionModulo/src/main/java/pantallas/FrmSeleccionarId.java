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
import java.awt.FlowLayout;
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
    private String accion;
    private JTextField txtId;

    public FrmSeleccionarId(Coordinador coordinador) {
        this.coordinador = coordinador;
        initUI();
    }

    private void initUI() {
        setTitle("Seleccionar ID");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.regresarDesdeSeleccionarId();
            }
        });

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card();

        GridBagConstraints gbc = UI.gbcBase(0, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblTitulo = UI.tituloGrande("Ingrese el ID del cliente");
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 20, 10);
        card.add(lblTitulo, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField();
        txtId.setFont(new Font("Georgia", Font.PLAIN, 18));
        txtId.setForeground(UI.TEXTO_OSCURO);
        txtId.setBackground(UI.AZUL_NORMAL.brighter());
        txtId.setHorizontalAlignment(JTextField.CENTER);
        txtId.setPreferredSize(new Dimension(200, 45));

        txtId.addActionListener(e -> aceptar());

        card.add(txtId, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        botones.setOpaque(false);

        JButton btnAtras = UI.boton("Atrás", new Color(0xC0392B), new Color(0x922B21));
        btnAtras.setPreferredSize(new Dimension(140, 40));
        btnAtras.addActionListener(e -> coordinador.regresarDesdeSeleccionarId());

        JButton btnAceptar = UI.boton("Aceptar", new Color(0x27AE60), new Color(0x1E8449));
        btnAceptar.setPreferredSize(new Dimension(140, 40));
        btnAceptar.addActionListener(e -> aceptar());

        botones.add(btnAtras);
        botones.add(btnAceptar);

        card.add(botones, gbc);

        UI.centrar(fondo, card);
    }

    private void aceptar() {
        String texto = txtId.getText().trim();

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese un ID.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Long id;
        try {
            id = Long.parseLong(texto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "El ID debe ser un número.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if ("modificar".equals(accion)) {
            coordinador.abrirModificarCliente(id);
        } else if ("eliminar".equals(accion)) {
            coordinador.eliminarCliente(id);
        } else if ("modificar_ingrediente".equals(accion)) {
            coordinador.abrirModificarIngrediente(id);
        }
        
    }

    public void setAccion(String accion) {
        this.accion = accion;
        txtId.setText("");
    }
}
