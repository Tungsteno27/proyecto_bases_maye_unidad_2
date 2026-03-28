/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pantallas;

import EstilosGUI.UI;
import DTOs.ClienteFrecuenteDTO;
import coordinador.Coordinador;
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
        setResizable(false);
        setSize(400, 300);
        setLocationRelativeTo(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.regresarDesdeInfoAdicional();
            }
        });

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card(330, 240);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(28, 40, 24, 40));

        lblNombreUsuario = etiquetaGrande(" ");
        lblNombreUsuario.setFont(new Font("Georgia", Font.BOLD, 18));
        card.add(lblNombreUsuario);

        card.add(Box.createVerticalStrut(18));

        lblVisitas = etiquetaInfo(" ");
        card.add(lblVisitas);
        card.add(Box.createVerticalStrut(8));

        lblGasto = etiquetaInfo(" ");
        card.add(lblGasto);
        card.add(Box.createVerticalStrut(8));

        lblPuntos = etiquetaInfo(" ");
        card.add(lblPuntos);

        card.add(Box.createVerticalStrut(22));

        JButton btnAtras = botonRojo("Atrás");
        btnAtras.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAtras.setMaximumSize(new Dimension(140, 40));
        btnAtras.addActionListener(e -> coordinador.regresarDesdeInfoAdicional());
        card.add(btnAtras);

        fondo.add(card);
    }

    /**
     * Carga los datos del DTO en las etiquetas de la pantalla.
     *
     * @param dto el cliente frecuente con estadísticas calculadas
     */
    public void cargarDatos(ClienteFrecuenteDTO dto) {
        String nombre = dto.getNombres()
                + (dto.getApellidoPaterno() != null ? " " + dto.getApellidoPaterno() : "");
        lblNombreUsuario.setText("Usuario: " + nombre);
        lblVisitas.setText("Visitas totales:  " + (dto.getTotalVisitas() != null ? dto.getTotalVisitas() : 0));
        lblGasto.setText(String.format("Monto total Gastado:  $%,.2f",
                dto.getTotalGastado() != null ? dto.getTotalGastado() : 0.0));
        lblPuntos.setText("Puntos acumulados:  " + (dto.getPuntos() != null ? dto.getPuntos() : 0));
    }

    private JLabel etiquetaGrande(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(new Font("Georgia", Font.BOLD, 17));
        lbl.setForeground(UI.TEXTO_OSCURO);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    private JLabel etiquetaInfo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Georgia", Font.PLAIN, 15));
        lbl.setForeground(UI.TEXTO_OSCURO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JButton botonRojo(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Georgia", Font.BOLD, 14));
        btn.setForeground(java.awt.Color.WHITE);
        btn.setBackground(new java.awt.Color(0xC0392B));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return btn;
    }
}
