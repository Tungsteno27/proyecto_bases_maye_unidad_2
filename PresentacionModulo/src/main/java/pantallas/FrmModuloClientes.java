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
import java.awt.GridBagLayout;
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
        setResizable(false);
        setSize(520, 420);
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

        JPanel card = UI.card(440, 340);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(36, 44, 40, 44));

        // Titulo
        JLabel lblTitulo = new JLabel("Modulo de clientes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 19));
        lblTitulo.setForeground(UI.TEXTO_OSCURO);
        card.add(lblTitulo, BorderLayout.NORTH);

        // Botones de opciones en columna
        JPanel columna = new JPanel();
        columna.setOpaque(false);
        columna.setLayout(new BoxLayout(columna, BoxLayout.Y_AXIS));
        columna.setBorder(new EmptyBorder(26, 0, 0, 0));

        String[] opciones = {"Registrar Cliente", "Modificar Cliente", "Eliminar Cliente", "Buscador de Clientes"};
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

        card.add(columna, BorderLayout.CENTER);

        // Boton volver pantalla modulos
        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Georgia", Font.PLAIN, 13));
        btnVolver.setForeground(UI.TEXTO_OSCURO);
        btnVolver.setContentAreaFilled(false);
        btnVolver.setBorderPainted(false);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver.addActionListener(e -> coordinador.regresarDesdeModuloClientes());

        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(10, 0, 0, 0));
        footer.add(btnVolver);
        card.add(footer, BorderLayout.SOUTH);

        fondo.add(card);

    }

}
