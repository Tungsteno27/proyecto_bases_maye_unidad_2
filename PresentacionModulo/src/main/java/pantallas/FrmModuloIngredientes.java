/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import EstilosGUI.UI;
import coordinador.Coordinador;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Pantalla principal del módulo de Ingredientes. Permite navegar a registrar,
 * modificar ingredientes o abrir el buscador.
 *
 * @author Noelia E.N.
 */
public class FrmModuloIngredientes extends JFrame {

    private final Coordinador coordinador;

    /**
     * Constructor que recibe el coordinador de navegación.
     *
     * @param coordinador el coordinador del sistema
     */
    public FrmModuloIngredientes(Coordinador coordinador) {
        this.coordinador = coordinador;
        initUI();
    }

    private void initUI() {
        setTitle("Maye's Family Diner — Ingredientes");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.regresarDesdeModuloIngredientes();
            }
        });

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(40, 60, 40, 60));

        JLabel lblTitulo = UI.tituloGrande("Ingredientes");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(30));

        String[] opciones = {"Registrar", "Modificar"};
        for (String opcion : opciones) {
            JButton btn = UI.botonPrimario(opcion);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(220, 46));

            switch (opcion) {
                case "Registrar" ->
                    btn.addActionListener(e -> coordinador.abrirRegistrarIngrediente());
                case "Modificar" ->
                    btn.addActionListener(e -> coordinador.abrirSeleccionarIdIngrediente());
            }

            card.add(btn);
            card.add(Box.createVerticalStrut(14));
        }

        card.add(Box.createVerticalStrut(10));

        JButton btnRegresar = UI.botonAccion("Regresar");
        btnRegresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegresar.setMaximumSize(new Dimension(220, 46));
        btnRegresar.addActionListener(e -> coordinador.regresarDesdeModuloIngredientes());
        card.add(btnRegresar);

        UI.centrar(fondo, card);
    }
}
