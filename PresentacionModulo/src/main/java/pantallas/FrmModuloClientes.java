/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import EstilosGUI.UI;
import coordinador.Coordinador;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
 * @author Dell PC
 */
public class FrmModuloClientes extends JFrame {

    private final Coordinador coordinador;

    public FrmModuloClientes(Coordinador coordinador) {
        this.coordinador = coordinador;
        iniciar();
    }

    private void iniciar() {
        setTitle("Módulo de clientes");
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
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblTitulo = UI.tituloGrande("Módulo de clientes");
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 20, 10);
        card.add(lblTitulo, gbc);

        gbc.gridy++;
        JPanel columna = new JPanel();
        columna.setOpaque(false);
        columna.setLayout(new BoxLayout(columna, BoxLayout.Y_AXIS));

        String[] opciones = {
            "Registrar Cliente",
            "Modificar Cliente",
            "Eliminar Cliente",
            "Buscador de Clientes"
        };

        for (String opcion : opciones) {
            JButton btn = UI.botonPrimario(opcion);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(260, 46));

            switch (opcion) {
                case "Registrar Cliente" ->
                    btn.addActionListener(e -> coordinador.abrirRegistrarCliente());

                case "Modificar Cliente" ->
                    btn.addActionListener(e -> coordinador.abrirSeleccionarIdCliente("modificar"));

                case "Eliminar Cliente" ->
                    btn.addActionListener(e -> coordinador.abrirSeleccionarIdCliente("eliminar"));

                case "Buscador de Clientes" ->
                    btn.addActionListener(e -> coordinador.abrirBuscadorClientes());
            }

            columna.add(btn);
            columna.add(Box.createVerticalStrut(12));
        }

        card.add(columna, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;

        JButton btnVolver = UI.boton("Volver", UI.AZUL_OSCURO, UI.AZUL_OSCURO_HOVER);
        btnVolver.setPreferredSize(new Dimension(140, 40));
        btnVolver.addActionListener(e -> coordinador.regresarDesdeModuloClientes());

        card.add(btnVolver, gbc);

        UI.centrar(fondo, card);
    }

}
