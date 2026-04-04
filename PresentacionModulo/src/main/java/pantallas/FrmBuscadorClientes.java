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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

    private List<ClienteFrecuenteDTO> clientesActuales;

    public FrmBuscadorClientes(Coordinador coordinador, ClienteBO clienteBO) {
        this.coordinador = coordinador;
        this.clienteBO = clienteBO;
        initUI();
    }

    private void initUI() {
        setTitle("Buscador de Clientes");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.regresarDesdeBuscadorClientes();
            }
        });

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card();
        card.setLayout(new BorderLayout(30, 15));
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = UI.tituloGrande("Buscador de Clientes");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(lblTitulo, BorderLayout.NORTH);

        String[] columnas = {"ID", "Nombres", "Apellido Paterno", "Apellido Materno", "Correo", "Teléfono"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Georgia", Font.PLAIN, 14));
        tabla.setRowHeight(30);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setFillsViewportHeight(true);

        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Georgia", Font.BOLD, 14));
        header.setBackground(UI.AZUL_NORMAL);
        header.setForeground(Color.WHITE);

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < columnas.length; i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }

        tabla.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(180);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(160);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(160);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(260);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBorder(BorderFactory.createLineBorder(UI.BORDE_ORO, 1));
        scroll.getViewport().setBackground(new Color(0xFDF6E3));

        card.add(scroll, BorderLayout.CENTER);

        JPanel lateral = new JPanel();
        lateral.setOpaque(false);
        lateral.setLayout(new BoxLayout(lateral, BoxLayout.Y_AXIS));
        lateral.setBorder(new EmptyBorder(10, 10, 10, 10));
        lateral.setPreferredSize(new Dimension(260, 0));

        JLabel lblFiltros = UI.titulo("Filtros");
        lateral.add(lblFiltros);
        lateral.add(Box.createVerticalStrut(12));

        txtFiltroNombre = agregarFiltro(lateral, "Nombre:");
        txtFiltroTelefono = agregarFiltro(lateral, "Teléfono:");
        txtFiltroCorreo = agregarFiltro(lateral, "Correo:");

        lateral.add(Box.createVerticalStrut(20));

        JButton btnBuscar = UI.botonPrimario("Buscar");
        btnBuscar.setMaximumSize(new Dimension(220, 40));
        btnBuscar.addActionListener(e -> buscar());
        lateral.add(btnBuscar);

        lateral.add(Box.createVerticalStrut(10));

        JButton btnInfo = UI.botonAccion("Ver info adicional");
        btnInfo.setMaximumSize(new Dimension(220, 40));
        btnInfo.addActionListener(e -> verInfoAdicional());
        lateral.add(btnInfo);

        card.add(lateral, BorderLayout.EAST);
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);

        JButton btnAtras = UI.boton("Atrás", new Color(0xC0392B), new Color(0x922B21));
        btnAtras.setPreferredSize(new Dimension(140, 40));
        btnAtras.addActionListener(e -> coordinador.regresarDesdeBuscadorClientes());

        footer.add(btnAtras);
        card.add(footer, BorderLayout.SOUTH);

        fondo.add(card, BorderLayout.CENTER);
    }

    private JTextField agregarFiltro(JPanel panel, String etiqueta) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Georgia", Font.PLAIN, 13));
        lbl.setForeground(UI.TEXTO_OSCURO);
        panel.add(lbl);

        JTextField tf = new JTextField();
        tf.setFont(new Font("Georgia", Font.PLAIN, 13));
        tf.setBackground(UI.AZUL_NORMAL.brighter());
        tf.setMaximumSize(new Dimension(220, 30));

        panel.add(tf);
        panel.add(Box.createVerticalStrut(10));

        return tf;
    }

    public void cargarTodos() {
        buscarConFiltros("", "", "");
    }

    private void buscar() {
        buscarConFiltros(
            txtFiltroNombre.getText().trim(),
            txtFiltroTelefono.getText().trim(),
            txtFiltroCorreo.getText().trim()
        );
    }

    private void buscarConFiltros(String nombre, String telefono, String correo) {
        try {
            clientesActuales = clienteBO.buscarFrecuentesPorFiltros(
                nombre.isEmpty() ? null : nombre,
                telefono.isEmpty() ? null : telefono,
                correo.isEmpty() ? null : correo
            );
            refrescarTabla();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refrescarTabla() {
        modeloTabla.setRowCount(0);

        if (clientesActuales == null) return;

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

    private void verInfoAdicional() {
        int fila = tabla.getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un cliente primero.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        ClienteFrecuenteDTO seleccionado = clientesActuales.get(fila);
        coordinador.abrirInfoAdicionalCliente(seleccionado);
    }
}
