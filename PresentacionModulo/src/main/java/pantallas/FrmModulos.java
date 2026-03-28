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
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
 
        // Título
        JLabel lblTitulo = new JLabel("Elija un módulo", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Georgia", Font.PLAIN, 20));
        lblTitulo.setForeground(UI.TEXTO_OSCURO);
        card.add(lblTitulo, BorderLayout.NORTH);
 
        // Grid 2x2 de módulos
        JPanel grid = new JPanel(new GridLayout(2, 2, 16, 16));
        grid.setOpaque(false);
        grid.setBorder(new EmptyBorder(26, 0, 0, 0));
 
        String[] modulos = {"Ingredientes", "Productos", "Reportes", "Clientes frecuentes"};
        for (String nombre : modulos) {
            JButton btn = UI.botonPrimario(nombre);
            btn.setFont(new Font("Georgia", Font.PLAIN, 15));
            //luego se implementará la navegación hacia el módulo de clientes
            grid.add(btn);
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
        }

        
 
        card.add(grid, BorderLayout.CENTER);
 
        // Botón de cerrado de sesión
        JButton btnSalir = new JButton("Cerrar sesión");
        btnSalir.setFont(new Font("Georgia", Font.PLAIN, 13));
        btnSalir.setForeground(UI.TEXTO_OSCURO);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setFocusPainted(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setHorizontalAlignment(SwingConstants.RIGHT);
        btnSalir.addActionListener(e -> coordinador.cerrarSesion());
 
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(14, 0, 0, 0));
        footer.add(btnSalir);
        card.add(footer, BorderLayout.SOUTH);
 
        fondo.add(card);
    }
}
