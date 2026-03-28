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
import java.awt.Dimension;
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

    /**
     * ID del cliente que se está editando.
     */
    private Long idCliente;

    public FrmModificarCliente(Coordinador coordinador, ClienteBO clienteBO) {
        this.coordinador = coordinador;
        this.clienteBO = clienteBO;
        initUI();
    }

    private void initUI() {
        setTitle("Modificar Cliente");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setSize(480, 420);
        setLocationRelativeTo(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.regresarDesdeModificarCliente();
            }
        });

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card(400, 350);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(28, 44, 28, 44));

        // Título
        JLabel lblTitulo = new JLabel("Ingrese los datos del cliente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Georgia", Font.PLAIN, 17));
        lblTitulo.setForeground(UI.TEXTO_OSCURO);
        card.add(lblTitulo, BorderLayout.NORTH);

        // Formulario
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(18, 0, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 4, 6, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        agregarFila(form, gbc, 0, "Nombres *:", txtNombres = campoTexto());
        agregarFila(form, gbc, 1, "ApellidoPaterno *:", txtApellidoPaterno = campoTexto());
        agregarFila(form, gbc, 2, "ApellidoMaterno:", txtApellidoMaterno = campoTexto());
        agregarFila(form, gbc, 3, "Correo Electrónico:", txtCorreo = campoTexto());
        agregarFila(form, gbc, 4, "Teléfono *:", txtTelefono = campoTexto());

        card.add(form, BorderLayout.CENTER);

        // Botones
        JPanel botones = new JPanel(new GridLayout(1, 2, 16, 0));
        botones.setOpaque(false);
        botones.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton btnAtras = botonRojo("Atrás");
        btnAtras.addActionListener(e -> coordinador.regresarDesdeModificarCliente());

        JButton btnGuardar = botonVerde("Guardar Cambios");
        btnGuardar.addActionListener(e -> guardar());

        botones.add(btnAtras);
        botones.add(btnGuardar);
        card.add(botones, BorderLayout.SOUTH);

        fondo.add(card);
    }

    /**
     * Pre-llena el formulario con los datos del cliente a modificar. Llamar
     * desde el Coordinador después de recuperar el DTO del BO.
     *
     * @param dto el DTO con los datos actuales (teléfono ya desencriptado)
     */
    public void cargarDatos(ClienteFrecuenteDTO dto) {
        this.idCliente = dto.getId();
        txtNombres.setText(dto.getNombres() != null ? dto.getNombres() : "");
        txtApellidoPaterno.setText(dto.getApellidoPaterno() != null ? dto.getApellidoPaterno() : "");
        txtApellidoMaterno.setText(dto.getApellidoMaterno() != null ? dto.getApellidoMaterno() : "");
        txtCorreo.setText(dto.getCorreo() != null ? dto.getCorreo() : "");
        txtTelefono.setText(dto.getTelefono() != null ? dto.getTelefono() : "");
    }

    /**
     * Recoge datos, llama al BO y navega a pantalla de éxito.
     */
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
        // Conservamos la fecha de registro original (no la actualizamos)
        // El BO recuperará la entidad completa con merge

        try {
            clienteBO.actualizar(dto);
            coordinador.mostrarExito("El Cliente con ID: " + idCliente + " fue\nactualizado con éxito", "modificar_cliente");
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private JTextField campoTexto() {
        JTextField tf = new JTextField(18);
        tf.setFont(new Font("Georgia", Font.PLAIN, 14));
        tf.setForeground(UI.TEXTO_OSCURO);
        tf.setBackground(UI.AZUL_NORMAL.brighter());
        tf.setPreferredSize(new Dimension(180, 32));
        return tf;
    }

    private void agregarFila(JPanel form, GridBagConstraints gbc, int fila, String etiqueta, JTextField campo) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.35;
        JLabel lbl = new JLabel(etiqueta, SwingConstants.RIGHT);
        lbl.setFont(new Font("Georgia", Font.PLAIN, 14));
        lbl.setForeground(UI.TEXTO_OSCURO);
        form.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        form.add(campo, gbc);
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

    private JButton botonVerde(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Georgia", Font.BOLD, 13));
        btn.setForeground(java.awt.Color.WHITE);
        btn.setBackground(new java.awt.Color(0x27AE60));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return btn;
    }
}
