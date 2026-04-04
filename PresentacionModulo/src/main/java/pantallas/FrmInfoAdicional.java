/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pantallas;

import EstilosGUI.UI;
import DTOs.ClienteFrecuenteDTO;
import coordinador.Coordinador;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
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
 * Muestra la información adicional de un cliente frecuente: visitas totales,
 * monto gastado y puntos acumulados.
 *
 * @author Noelia
 */
public class FrmInfoAdicional extends JFrame {

     private final Coordinador coordinador;
    private JLabel lblNombreUsuario;
    private JLabel lblVisitas;
    private JLabel lblGasto;
    private JLabel lblPuntos;

    public FrmInfoAdicional(Coordinador coordinador) {
        this.coordinador = coordinador;
        initUI();
    }

    private void initUI() {
        setTitle("Información Adicional");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.regresarDesdeInfoAdicional();
            }
        });

        // 🔹 Fondo
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card();

        GridBagConstraints gbc = UI.gbcBase(0, 0);
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        lblNombreUsuario = UI.tituloGrande("");
        card.add(lblNombreUsuario, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy++;
        card.add(UI.titulo("Visitas totales:"), gbc);
        lblVisitas = UI.valor();
        gbc.gridx = 1;
        card.add(lblVisitas, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        card.add(UI.titulo("Monto total gastado:"), gbc);
        lblGasto = UI.valor();
        gbc.gridx = 1;
        card.add(lblGasto, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        card.add(UI.titulo("Puntos acumulados:"), gbc);
        lblPuntos = UI.valor();
        gbc.gridx = 1;
        card.add(lblPuntos, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        JButton btnAtras = UI.boton("Atrás", new Color(0xC0392B), new Color(0x922B21));
        btnAtras.setPreferredSize(new Dimension(150, 40));
        btnAtras.addActionListener(e -> coordinador.regresarDesdeInfoAdicional());

        card.add(btnAtras, gbc);

        UI.centrar(fondo, card);
    }

    public void cargarDatos(ClienteFrecuenteDTO dto) {
        String nombre = dto.getNombres()
                + (dto.getApellidoPaterno() != null ? " " + dto.getApellidoPaterno() : "");

        lblNombreUsuario.setText("Usuario: " + nombre);
        lblVisitas.setText(String.valueOf(dto.getTotalVisitas() != null ? dto.getTotalVisitas() : 0));
        lblGasto.setText(String.format("$%,.2f",
                dto.getTotalGastado() != null ? dto.getTotalGastado() : 0.0));
        lblPuntos.setText(String.valueOf(dto.getPuntos() != null ? dto.getPuntos() : 0));
    }
}
