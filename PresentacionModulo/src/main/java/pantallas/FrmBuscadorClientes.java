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
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * Pantalla buscador de clientes frecuentes. Muestra una tabla con ID, Nombres,
 * ApellidoPaterno, ApellidoMaterno, Correo, Teléfono. Permite filtrar por
 * nombre, teléfono y correo. Al seleccionar un cliente y presionar "Ver
 * información adicional" abre FrmInfoAdicional.
 *
 * @author Noelia
 */
public class FrmBuscadorClientes extends JFrame {

    private final Coordinador coordinador;
    private final ClienteBO clienteBO;

    private JTextField txtFiltroNombre;
    private JTextField txtFiltroTelefono;
    private JTextField txtFiltroCorreo;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    /**
     * Guarda los DTOs actuales para poder recuperar el seleccionado.
     */
    private List<ClienteFrecuenteDTO> clientesActuales;

    public FrmBuscadorClientes(Coordinador coordinador, ClienteBO clienteBO) {
        this.coordinador = coordinador;
        this.clienteBO = clienteBO;
        initUI();
    }

    private void initUI() {
        setTitle("Buscador de Clientes");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setSize(860, 500);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(720, 420));

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.regresarDesdeBuscadorClientes();
            }
        });

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = new JPanel(new BorderLayout(12, 0));
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(22, 22, 18, 22));

        // Título
        JLabel lblTitulo = new JLabel("Buscador de Clientes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 24));
        lblTitulo.setForeground(UI.TEXTO_OSCURO);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 16, 0));
        card.add(lblTitulo, BorderLayout.NORTH);

        // Tabla 
        String[] columnas = {"ID", "Nombres", "ApellidoPaterno", "ApellidoMaterno", "Correo", "Teléfono"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Georgia", Font.PLAIN, 13));
        tabla.setRowHeight(28);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setBackground(new Color(0xFDF6E3));
        tabla.setGridColor(UI.BORDE_ORO);
        tabla.setShowGrid(true);
        tabla.setFocusable(false);

        // Encabezado de la tabla con el azul del sistema
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Georgia", Font.BOLD, 13));
        header.setBackground(UI.AZUL_NORMAL);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 32));

        // Centrar contenido de la tabla
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columnas.length; i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }

        // Ancho de columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(110);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(110);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(160);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(110);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(UI.BORDE_ORO, 1));
        scroll.getViewport().setBackground(new Color(0xFDF6E3));
        card.add(scroll, BorderLayout.CENTER);

        JPanel lateral = new JPanel();
        lateral.setOpaque(false);
        lateral.setLayout(new BoxLayout(lateral, BoxLayout.Y_AXIS));
        lateral.setBorder(new EmptyBorder(0, 14, 0, 0));
        lateral.setPreferredSize(new Dimension(210, 0));

        JLabel lblFiltros = new JLabel("Opciones de Filtrado");
        lblFiltros.setFont(new Font("Georgia", Font.BOLD, 14));
        lblFiltros.setForeground(UI.TEXTO_OSCURO);
        lblFiltros.setAlignmentX(Component.LEFT_ALIGNMENT);
        lateral.add(lblFiltros);
        lateral.add(Box.createVerticalStrut(14));

        txtFiltroNombre = agregarFiltro(lateral, "Nombre:");
        lateral.add(Box.createVerticalStrut(10));
        txtFiltroTelefono = agregarFiltro(lateral, "Teléfono:");
        lateral.add(Box.createVerticalStrut(10));
        txtFiltroCorreo = agregarFiltro(lateral, "Correo:");

        lateral.add(Box.createVerticalStrut(20));

        JButton btnFiltrar = UI.botonPrimario("Buscar");
        btnFiltrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnFiltrar.setMaximumSize(new Dimension(180, 40));
        btnFiltrar.addActionListener(e -> buscar());
        lateral.add(btnFiltrar);

        lateral.add(Box.createVerticalStrut(10));

        JButton btnVerInfo = UI.botonAccion("Ver información adicional");
        btnVerInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnVerInfo.setMaximumSize(new Dimension(180, 40));
        btnVerInfo.setFont(new Font("Georgia", Font.PLAIN, 13));
        btnVerInfo.addActionListener(e -> verInfoAdicional());
        lateral.add(btnVerInfo);

        card.add(lateral, BorderLayout.EAST);

        fondo.add(card, BorderLayout.CENTER);

        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(4, 0, 10, 0));

        JButton btnAtras = botonRojo("Atrás");
        btnAtras.addActionListener(e -> coordinador.regresarDesdeBuscadorClientes());
        footer.add(btnAtras);

        fondo.add(footer, BorderLayout.SOUTH);
    }

    /**
     * Agrega una etiqueta + campo de texto al panel lateral.
     */
    private JTextField agregarFiltro(JPanel panel, String etiqueta) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Georgia", Font.PLAIN, 13));
        lbl.setForeground(UI.TEXTO_OSCURO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lbl);

        JTextField tf = new JTextField();
        tf.setFont(new Font("Georgia", Font.PLAIN, 13));
        tf.setBackground(UI.AZUL_NORMAL.brighter());
        tf.setMaximumSize(new Dimension(180, 30));
        tf.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(tf);
        return tf;
    }

    /**
     * Carga todos los clientes frecuentes al abrir la pantalla.
     */
    public void cargarTodos() {
        buscarConFiltros("", "", "");
    }

    /**
     * Lee filtros y lanza la búsqueda.
     */
    private void buscar() {
        String nombre = txtFiltroNombre.getText().trim();
        String telefono = txtFiltroTelefono.getText().trim();
        String correo = txtFiltroCorreo.getText().trim();
        buscarConFiltros(nombre, telefono, correo);
    }

    private void buscarConFiltros(String nombre, String telefono, String correo) {
        try {
            clientesActuales = clienteBO.buscarFrecuentesPorFiltros(
                    nombre.isEmpty() ? null : nombre,
                    telefono.isEmpty() ? null : telefono,
                    correo.isEmpty() ? null : correo);
            refrescarTabla();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Rellena la tabla con la lista actual de clientes.
     */
    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        if (clientesActuales == null) {
            return;
        }
        for (ClienteFrecuenteDTO c : clientesActuales) {
            modeloTabla.addRow(new Object[]{
                c.getId(),
                c.getNombres(),
                c.getApellidoPaterno(),
                c.getApellidoMaterno() != null ? c.getApellidoMaterno() : "",
                c.getCorreo() != null ? c.getCorreo() : "",
                c.getTelefono()
            });
        }
    }

    /**
     * Abre la pantalla de información adicional del cliente seleccionado.
     */
    private void verInfoAdicional() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un cliente de la tabla primero.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ClienteFrecuenteDTO seleccionado = clientesActuales.get(fila);
        coordinador.abrirInfoAdicionalCliente(seleccionado);
    }

    private JButton botonRojo(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Georgia", Font.BOLD, 15));
        btn.setForeground(java.awt.Color.WHITE);
        btn.setBackground(new java.awt.Color(0xC0392B));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 42));
        return btn;
    }
}
