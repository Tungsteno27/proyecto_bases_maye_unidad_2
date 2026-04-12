/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import EstilosGUI.UI;
import coordinador.Coordinador;
import excepciones.PersistenciaException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Tungs
 */
public class FrmModuloReportes extends JFrame {
    private final Coordinador coordinador;

    public FrmModuloReportes(Coordinador coordinador) {
        this.coordinador = coordinador;
        initUI();
    }

    private void initUI() {
        setTitle("Módulo de Reportes");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.regresarDesdeModuloReportes();
            }
        });

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel contenedor = UI.card();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setBorder(new EmptyBorder(40, 60, 40, 60));

        JLabel titulo = UI.tituloGrande("Reportes");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnClientes = UI.botonPrimario("Reporte de Clientes Frecuentes");
        btnClientes.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnClientes.setMaximumSize(new Dimension(300, 50));

        JButton btnComandas = UI.botonAccion("Reporte de Comandas");
        btnComandas.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnComandas.setMaximumSize(new Dimension(300, 50));

        JButton btnAtras = UI.boton("Atrás", new Color(0xC0392B), new Color(0x922B21));
        btnAtras.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAtras.setMaximumSize(new Dimension(200, 40));

        btnClientes.addActionListener(e -> coordinador.abrirReporteClientes());
        btnComandas.addActionListener(e -> {
            try {
                coordinador.abrirReporteComandas();
            } catch (PersistenciaException ex) {
                Logger.getLogger(FrmModuloReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnAtras.addActionListener(e -> coordinador.regresarDesdeModuloReportes());

        contenedor.add(titulo);
        contenedor.add(Box.createVerticalStrut(30));
        contenedor.add(btnClientes);
        contenedor.add(Box.createVerticalStrut(20));
        contenedor.add(btnComandas);
        contenedor.add(Box.createVerticalStrut(40));
        contenedor.add(btnAtras);

        fondo.add(contenedor);
    }
    
}
