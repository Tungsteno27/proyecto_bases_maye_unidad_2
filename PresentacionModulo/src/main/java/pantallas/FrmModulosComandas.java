/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import EstilosGUI.UI;
import coordinador.Coordinador;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                    btn.addActionListener(e -> {
                try {
                    coordinador.abrirSeleccionadorMesa();
                } catch (NegocioException ex) {
                    Logger.getLogger(FrmModulosComandas.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
                case "Modificar" ->
                    btn.addActionListener(e -> {
                try {
                    coordinador.abrirModificadorComanda();
                } catch (PersistenciaException ex) {
                    Logger.getLogger(FrmModulosComandas.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
                case "Buscar" ->
                    btn.addActionListener(e -> {
                try {
                    coordinador.abrirBuscadorComanda();
                } catch (PersistenciaException ex) {
                    Logger.getLogger(FrmModulosComandas.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            }
            columna.add(btn);
            columna.add(Box.createVerticalStrut(12));
        }
        
        JButton btnAtras = UI.boton("Atrás", UI.AZUL_OSCURO, UI.AZUL_OSCURO_HOVER);
        btnAtras.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAtras.setMaximumSize(new Dimension(140, 40));
        btnAtras.addActionListener(e -> coordinador.iniciarSistema());

        columna.add(btnAtras);
        card.add(columna, BorderLayout.CENTER);
        UI.centrar(fondo, card);
    }
}
