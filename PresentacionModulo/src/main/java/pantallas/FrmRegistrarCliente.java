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
        setResizable(false);
        setSize(480, 420);
        setLocationRelativeTo(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.regresarDesdeRegistrarCliente();
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

        // Formulario con GridBagLayout
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(18, 0, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 4, 6, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Fila 0: Nombres
        agregarFila(form, gbc, 0, "Nombres *:", txtNombres = campoTexto());

        // Fila 1: Apellido Paterno
        agregarFila(form, gbc, 1, "ApellidoPaterno *:", txtApellidoPaterno = campoTexto());

        // Fila 2: Apellido Materno
        agregarFila(form, gbc, 2, "ApellidoMaterno:", txtApellidoMaterno = campoTexto());

        // Fila 3: Correo Electrónico
        agregarFila(form, gbc, 3, "Correo Electrónico:", txtCorreo = campoTexto());

        // Fila 4: Teléfono
        agregarFila(form, gbc, 4, "Teléfono *:", txtTelefono = campoTexto());

        card.add(form, BorderLayout.CENTER);

        // Botones Atrás / Registrar
        JPanel botones = new JPanel(new GridLayout(1, 2, 16, 0));
        botones.setOpaque(false);
        botones.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton btnAtras = botonRojo("Atrás");
        btnAtras.addActionListener(e -> coordinador.regresarDesdeRegistrarCliente());

        JButton btnRegistrar = botonVerde("Registrar");
        btnRegistrar.addActionListener(e -> registrar());

        botones.add(btnAtras);
        botones.add(btnRegistrar);
        card.add(botones, BorderLayout.SOUTH);

        fondo.add(card);
    }

    /**
     * Crea un campo de texto con el estilo del sistema.
     */
    private JTextField campoTexto() {
        JTextField tf = new JTextField(18);
        tf.setFont(new Font("Georgia", Font.PLAIN, 14));
        tf.setForeground(UI.TEXTO_OSCURO);
        tf.setBackground(UI.AZUL_NORMAL.brighter());
        tf.setPreferredSize(new Dimension(180, 32));
        return tf;
    }

    /**
     * Agrega una fila de etiqueta + campo al formulario.
     */
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

    /**
     * Botón rojo para "Atrás" / "Cancelar".
     */
    private JButton botonRojo(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Georgia", Font.BOLD, 15));
        btn.setForeground(java.awt.Color.WHITE);
        btn.setBackground(new java.awt.Color(0xC0392B));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 42));
        return btn;
    }

    /**
     * Botón verde para acciones de confirmación.
     */
    private JButton botonVerde(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Georgia", Font.BOLD, 15));
        btn.setForeground(java.awt.Color.WHITE);
        btn.setBackground(new java.awt.Color(0x27AE60));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 42));
        return btn;
    }

    /**
     * Recoge los datos, llama al BO y muestra resultado
     */
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
            coordinador.mostrarExito("El cliente fue registrado con éxito", "registro_cliente");
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Limpia todos los campos del formulario.
     */
    public void limpiar() {
        txtNombres.setText("");
        txtApellidoPaterno.setText("");
        txtApellidoMaterno.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
    }
}
