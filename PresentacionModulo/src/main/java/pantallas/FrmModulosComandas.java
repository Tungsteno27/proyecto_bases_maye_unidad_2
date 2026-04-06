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

public class FrmModulosComandas extends JFrame {

    private final Coordinador coordinador;

    public FrmModulosComandas(Coordinador coordinador) {
        this.coordinador = coordinador;
        iniciar();
    }

    private void iniciar() {
        setTitle("Modulo Comandas");
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

        JPanel card = UI.card();
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(36, 44, 40, 44));

        JLabel lblTitulo = new JLabel("Modulo de Comandas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 19));
        lblTitulo.setForeground(UI.TEXTO_OSCURO);
        card.add(lblTitulo, BorderLayout.NORTH);

        JPanel columna = new JPanel();
        columna.setOpaque(false);
        columna.setLayout(new BoxLayout(columna, BoxLayout.Y_AXIS));
        columna.setBorder(new EmptyBorder(26, 0, 0, 0));

        String[] opciones = {"Registrar", "Modificar", "Buscar"};
        for (String opcion : opciones) {
            JButton btn = UI.botonPrimario(opcion);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(260, 46));

            switch (opcion) {
                case "Registrar" ->
                    btn.addActionListener(e -> coordinador.abrirModuloProductos());
                case "Modificar" ->
                    btn.addActionListener(e -> coordinador.abrirModuloProductos());
                case "Buscar" ->
                    btn.addActionListener(e -> coordinador.abrirModuloProductos());
            }
            columna.add(btn);
            columna.add(Box.createVerticalStrut(12));
        }

        card.add(columna, BorderLayout.CENTER);
        UI.centrar(fondo, card);
    }
}
