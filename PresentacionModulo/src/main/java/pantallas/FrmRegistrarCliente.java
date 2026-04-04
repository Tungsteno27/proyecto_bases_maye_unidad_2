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
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Formulario para registrar un nuevo cliente frecuente. Campos obligatorios:
 * Nombres, Apellido Paterno, Teléfono. Campos opcionales: Apellido Materno,
 * Correo Electrónico.
 *
 * @author Noelia
 */
public class FrmRegistrarCliente extends JFrame {

    private final Coordinador coordinador;
    private final ClienteBO clienteBO;

    private JTextField txtNombres;
    private JTextField txtApellidoPaterno;
    private JTextField txtApellidoMaterno;
    private JTextField txtCorreo;
    private JTextField txtTelefono;

    public FrmRegistrarCliente(Coordinador coordinador, ClienteBO clienteBO) {
        this.coordinador = coordinador;
        this.clienteBO = clienteBO;
        initUI();
    }

    private void initUI() {
        setTitle("Registrar Cliente");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.regresarDesdeRegistrarCliente();
            }
        });

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card();

        GridBagConstraints gbc = UI.gbcBase(0, 0);
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblTitulo = UI.tituloGrande("Registrar Cliente");
        card.add(lblTitulo, gbc);

        gbc.gridy++;
        JLabel lblSub = UI.titulo("Ingrese los datos del cliente");
        lblSub.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(lblSub, gbc);

        gbc.gridy++;
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
        btnAtras.addActionListener(e -> coordinador.regresarDesdeRegistrarCliente());

        JButton btnRegistrar = UI.boton("Registrar", new Color(0x27AE60), new Color(0x1E8449));
        btnRegistrar.setPreferredSize(new Dimension(150, 40));
        btnRegistrar.addActionListener(e -> registrar());

        botones.add(btnAtras);
        botones.add(btnRegistrar);

        card.add(botones, gbc);

        UI.centrar(fondo, card);
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

    private void registrar() {
        String nombres = txtNombres.getText().trim();
        String apPat = txtApellidoPaterno.getText().trim();
        String apMat = txtApellidoMaterno.getText().trim();
        String correo = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();

        ClienteFrecuenteDTO dto = new ClienteFrecuenteDTO();
        dto.setNombres(nombres);
        dto.setApellidoPaterno(apPat);
        dto.setApellidoMaterno(apMat.isEmpty() ? null : apMat);
        dto.setCorreo(correo.isEmpty() ? null : correo);
        dto.setTelefono(telefono);
        dto.setFechaRegistro(LocalDate.now());

        try {
            clienteBO.registrar(dto);
            coordinador.mostrarExito(
                "El cliente fue registrado con éxito",
                "registro_cliente"
            );
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Error de validación",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    public void limpiar() {
        txtNombres.setText("");
        txtApellidoPaterno.setText("");
        txtApellidoMaterno.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
    }
}
