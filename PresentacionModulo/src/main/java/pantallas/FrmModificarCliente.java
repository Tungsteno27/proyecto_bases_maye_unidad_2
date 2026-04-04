/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import EstilosGUI.UI;
import BOs.ClienteBO;
import DTOs.ClienteFrecuenteDTO;
import coordinador.Coordinador;
import excepciones.NegocioException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Formulario para modificar los datos de un cliente frecuente existente. Se
 * pre-llena con los datos actuales del cliente al abrirse.
 *
 * @author Noelia
 */
public class FrmModificarCliente extends JFrame {

    private final Coordinador coordinador;
    private final ClienteBO clienteBO;

    private JTextField txtNombres;
    private JTextField txtApellidoPaterno;
    private JTextField txtApellidoMaterno;
    private JTextField txtCorreo;
    private JTextField txtTelefono;

    private Long idCliente;

    public FrmModificarCliente(Coordinador coordinador, ClienteBO clienteBO) {
        this.coordinador = coordinador;
        this.clienteBO = clienteBO;
        initUI();
    }

    private void initUI() {
        setTitle("Modificar Cliente");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.regresarDesdeModificarCliente();
            }
        });

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card();

        GridBagConstraints gbc = UI.gbcBase(0, 0);
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblTitulo = UI.tituloGrande("Modificar Cliente");
        card.add(lblTitulo, gbc);

        gbc.gridy++;
        JLabel lblSub = UI.titulo("Ingrese los datos del cliente");
        lblSub.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(lblSub, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        card.add(form, gbc);

        GridBagConstraints fgbc = UI.gbcBase(0, 0);
        fgbc.anchor = GridBagConstraints.WEST;

        agregarFila(form, fgbc, 0, "Nombres *:", txtNombres = campoTexto());
        agregarFila(form, fgbc, 1, "Apellido Paterno *:", txtApellidoPaterno = campoTexto());
        agregarFila(form, fgbc, 2, "Apellido Materno:", txtApellidoMaterno = campoTexto());
        agregarFila(form, fgbc, 3, "Correo Electrónico:", txtCorreo = campoTexto());
        agregarFila(form, fgbc, 4, "Teléfono *:", txtTelefono = campoTexto());

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        botones.setOpaque(false);

        JButton btnAtras = UI.boton("Atrás", new Color(0xC0392B), new Color(0x922B21));
        btnAtras.setPreferredSize(new Dimension(150, 40));
        btnAtras.addActionListener(e -> coordinador.regresarDesdeModificarCliente());

        JButton btnGuardar = UI.boton("Guardar Cambios", new Color(0x27AE60), new Color(0x1E8449));
        btnGuardar.setPreferredSize(new Dimension(180, 40));
        btnGuardar.addActionListener(e -> guardar());

        botones.add(btnAtras);
        botones.add(btnGuardar);

        card.add(botones, gbc);

        UI.centrar(fondo, card);
    }

    public void cargarDatos(ClienteFrecuenteDTO dto) {
        this.idCliente = dto.getId();
        txtNombres.setText(dto.getNombres() != null ? dto.getNombres() : "");
        txtApellidoPaterno.setText(dto.getApellidoPaterno() != null ? dto.getApellidoPaterno() : "");
        txtApellidoMaterno.setText(dto.getApellidoMaterno() != null ? dto.getApellidoMaterno() : "");
        txtCorreo.setText(dto.getCorreo() != null ? dto.getCorreo() : "");
        txtTelefono.setText(dto.getTelefono() != null ? dto.getTelefono() : "");
    }

    private void guardar() {
        String nombres = txtNombres.getText().trim();
        String apPat = txtApellidoPaterno.getText().trim();
        String apMat = txtApellidoMaterno.getText().trim();
        String correo = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();

        ClienteFrecuenteDTO dto = new ClienteFrecuenteDTO();
        dto.setId(idCliente);
        dto.setNombres(nombres);
        dto.setApellidoPaterno(apPat);
        dto.setApellidoMaterno(apMat.isEmpty() ? null : apMat);
        dto.setCorreo(correo.isEmpty() ? null : correo);
        dto.setTelefono(telefono);

        try {
            clienteBO.actualizar(dto);
            coordinador.mostrarExito(
                "El Cliente con ID: " + idCliente + " fue actualizado con éxito",
                "modificar_cliente"
            );
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private JTextField campoTexto() {
        JTextField tf = new JTextField(18);
        tf.setFont(new Font("Georgia", Font.PLAIN, 14));
        tf.setForeground(UI.TEXTO_OSCURO);
        tf.setBackground(UI.AZUL_NORMAL.brighter());
        return tf;
    }

    private void agregarFila(JPanel form, GridBagConstraints gbc, int fila, String etiqueta, JTextField campo) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.4;

        JLabel lbl = UI.titulo(etiqueta);
        form.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.6;
        form.add(campo, gbc);
    }
}
