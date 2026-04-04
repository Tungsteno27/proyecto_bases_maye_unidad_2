/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import EstilosGUI.UI;
import coordinador.Coordinador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
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
public class FrmModulos extends JFrame {
    private final Coordinador coordinador;

    public FrmModulos(Coordinador coordinador) {
        this.coordinador = coordinador;
        initUI();
    }

    private void initUI() {
        setTitle("Maye's Family Diner — Módulos");
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

        JLabel lblTitulo = UI.tituloGrande("Elija un módulo");
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 20, 10);
        card.add(lblTitulo, gbc);

        gbc.gridy++;
        JPanel grid = new JPanel(new GridLayout(2, 2, 20, 20));
        grid.setOpaque(false);

        String[] modulos = {
            "Ingredientes",
            "Productos",
            "Reportes",
            "Clientes frecuentes"
        };

        for (String nombre : modulos) {
            JButton btn = UI.botonPrimario(nombre);
            btn.setFont(new Font("Georgia", Font.PLAIN, 15));

            switch (nombre) {
                case "Ingredientes" ->
                    btn.addActionListener(e -> coordinador.abrirModuloIngredientes());

                case "Productos" ->
                    btn.addActionListener(e -> coordinador.abrirModuloProductos());

                case "Reportes" ->
                    btn.addActionListener(e -> coordinador.abrirModuloReportes());

                case "Clientes frecuentes" ->
                    btn.addActionListener(e -> coordinador.abrirModuloClientes());
            }

            grid.add(btn);
        }

        card.add(grid, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;

        JButton btnSalir = UI.boton("Cerrar sesión", new Color(0x7F8C8D), new Color(0x626567));
        btnSalir.setPreferredSize(new Dimension(160, 40));
        btnSalir.addActionListener(e -> coordinador.cerrarSesion());

        card.add(btnSalir, gbc);

        UI.centrar(fondo, card);
    }
}
