/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import BOs.ProductoBO;
import DTOs.ProductoDTO;
import EstilosGUI.UI;
import coordinador.Coordinador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Tungs
 */
public class FrmBuscadorProductos extends JFrame{
    private final Coordinador coordinador;

    private JTextField txtFiltroNombre;
    private JTextField txtFiltroTipo;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public FrmBuscadorProductos(Coordinador coordinador) {
        this.coordinador = coordinador;
        initUI();
        cargarTodos();
    }

    /**
     * Método que inicializa la interfaz
     */
    private void initUI() {
        setTitle("Buscador de Productos");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.regresarDesdeBuscadorProductos();
            }
        });

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card();
        card.setLayout(new BorderLayout(30, 15));
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = UI.tituloGrande("Buscador de Productos");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(lblTitulo, BorderLayout.NORTH);

        String[] columnas = {"Imagen", "ID", "Nombre", "Precio", "Tipo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return ImageIcon.class; 
                return String.class;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Georgia", Font.PLAIN, 14));
        tabla.setRowHeight(50); 
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setFillsViewportHeight(true);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);


        tabla.getColumnModel().getColumn(0).setPreferredWidth(60);  
        tabla.getColumnModel().getColumn(1).setPreferredWidth(80);  
        tabla.getColumnModel().getColumn(2).setPreferredWidth(200); 
        tabla.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(120); 
        tabla.getColumnModel().getColumn(5).setPreferredWidth(100); 

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Georgia", Font.BOLD, 14));
        header.setBackground(UI.AZUL_NORMAL);
        header.setForeground(Color.WHITE);

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 1; i < columnas.length; i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(UI.BORDE_ORO, 1));
        scroll.getViewport().setBackground(new Color(0xFDF6E3));
        card.add(scroll, BorderLayout.CENTER);


        JPanel lateral = new JPanel();
        lateral.setOpaque(false);
        lateral.setLayout(new BoxLayout(lateral, BoxLayout.Y_AXIS));
        lateral.setBorder(new EmptyBorder(20, 10, 20, 10));
        lateral.setPreferredSize(new Dimension(260, 0));

        lateral.add(Box.createVerticalGlue());

        JPanel contenedor = new JPanel();
        contenedor.setOpaque(false);
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));

        JLabel lblFiltros = UI.titulo("Filtros");
        lblFiltros.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(lblFiltros);
        contenedor.add(Box.createVerticalStrut(15));

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(lblNombre);

        txtFiltroNombre = new JTextField();
        txtFiltroNombre.setMaximumSize(new Dimension(220, 30));
        txtFiltroNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(txtFiltroNombre);
        contenedor.add(Box.createVerticalStrut(12));

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(lblTipo);

        txtFiltroTipo = new JTextField();
        txtFiltroTipo.setMaximumSize(new Dimension(220, 30));
        txtFiltroTipo.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(txtFiltroTipo);
        contenedor.add(Box.createVerticalStrut(25));

        JLabel lblAcciones = UI.titulo("Acciones");
        lblAcciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(lblAcciones);
        contenedor.add(Box.createVerticalStrut(15));

        Dimension sizeBtn = new Dimension(220, 40);

        JButton btnAgregar = UI.botonPrimario("Agregar producto");
        btnAgregar.setMaximumSize(sizeBtn);
        btnAgregar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAgregar.addActionListener(e -> coordinador.abrirRegistrarProducto());
        contenedor.add(btnAgregar);
        contenedor.add(Box.createVerticalStrut(10));

        JButton btnModificar = UI.botonAccion("Modificar producto");
        btnModificar.setMaximumSize(sizeBtn);
        btnModificar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnModificar.addActionListener(e -> accionModificar());
        contenedor.add(btnModificar);
        contenedor.add(Box.createVerticalStrut(10));

        JButton btnEliminar = UI.boton("Eliminar producto",
                new Color(0xC0392B), new Color(0x922B21));
        btnEliminar.setMaximumSize(sizeBtn);
        btnEliminar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEliminar.addActionListener(e -> accionEliminar());
        contenedor.add(btnEliminar);

        lateral.add(contenedor);
        lateral.add(Box.createVerticalGlue());

        card.add(lateral, BorderLayout.EAST);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);

        JButton btnAtras = UI.boton("Atrás", new Color(0xC0392B), new Color(0x922B21));
        btnAtras.setPreferredSize(new Dimension(140, 40));
        btnAtras.addActionListener(e -> coordinador.regresarDesdeBuscadorProductos());
        footer.add(btnAtras);

        card.add(footer, BorderLayout.SOUTH);
        fondo.add(card, BorderLayout.CENTER);

        txtFiltroNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                accionBuscar();
            }
        });

        txtFiltroTipo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                accionBuscar();
            }
        });
    }

    /**
     * Método que carga todos los productos 
     */
    private void cargarTodos() {
        actualizarTabla(coordinador.obtenerProductos());
    }

    /**
     * Método que llama al coordinador para buscar según los filtros
     */
    private void accionBuscar() {
        String nombre = txtFiltroNombre.getText().trim();
        String tipo = txtFiltroTipo.getText().trim();
        String tipoFiltro = tipo.isEmpty() ? null : tipo;
        String nombreFiltro = nombre.isEmpty() ? null : nombre;
        actualizarTabla(coordinador.buscarProductos(nombreFiltro, tipoFiltro));
    }
    
    /**
     * Método que refresca la tabla
     * @param lista la lista de los productos 
     */
    private void actualizarTabla(List<ProductoDTO> lista) {
        modeloTabla.setRowCount(0);
        if (lista == null) return;
        for (ProductoDTO p : lista) {
            ImageIcon icon = null;
            if (p.getImagenUrl() != null) {
                java.net.URL imgUrl = getClass().getResource("/imagenes/" + p.getImagenUrl());
                if (imgUrl != null) {
                    Image img = new ImageIcon(imgUrl).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(img);
                } else {
                    File archivo = new File(p.getImagenUrl());
                    if (archivo.exists()) {
                        Image img = new ImageIcon(archivo.getAbsolutePath()).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                        icon = new ImageIcon(img);
                    }
                }
            }
            modeloTabla.addRow(new Object[]{
                icon,              
                p.getId(),         
                p.getNombre(),
                "$" + p.getPrecio(),
                p.getTipo(),
                p.getEstado()
            });
        }
        
    }

    /**
     * Método que llama al coordinador para eliminar (o cambiar el estado a inactivo)
     */
    private void accionEliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un producto primero.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        Long id = (Long) modeloTabla.getValueAt(fila, 1); 
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este producto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = coordinador.eliminarProducto(id);

            if (eliminado) {
                JOptionPane.showMessageDialog(this,
                        "Producto eliminado correctamente.");
                cargarTodos();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al eliminar el producto.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }

    /**
     * Método que llama al coordinador para modificar
     */
    private void accionModificar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un producto primero.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        Long id = (Long) modeloTabla.getValueAt(fila, 1);
        coordinador.abrirModificarProducto(id);
    }
}
