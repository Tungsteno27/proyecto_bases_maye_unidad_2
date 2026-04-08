/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import BOs.IngredienteBO;
import DTOs.IngredienteDTO;
import EstilosGUI.UI;
import coordinador.Coordinador;
import excepciones.NegocioException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * Buscador de ingredientes con doble modo de uso: Standalone: se abre desde el
 * módulo de ingredientes para consulta Seleccion: se abre desde el módulo de
 * productos; al confirmar, devuelve el {@link IngredienteDTO} seleccionado
 * mediante un callback
 *
 * @author Noelia E.N.
 */
public class FrmBuscadorIngredientes extends JFrame {

    private final Coordinador coordinador;
    private final IngredienteBO ingredienteBO;

    private JTextField txtFiltroNombre;
    private JComboBox<String> cmbFiltroUnidad;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    /**
     * Lista actual de ingredientes mostrados en la tabla.
     */
    private List<IngredienteDTO> ingredientesActuales;

    /**
     * Callback que se invoca al seleccionar un ingrediente en modo selección.
     * Es null cuando el buscador se usa en modo standalone.
     */
    private Consumer<IngredienteDTO> callbackSeleccion;

    /**
     * Constructor del buscador de ingredientes.
     *
     * @param coordinador el coordinador de navegación
     * @param ingredienteBO el BO de ingredientes
     */
    public FrmBuscadorIngredientes(Coordinador coordinador, IngredienteBO ingredienteBO) {
        this.coordinador = coordinador;
        this.ingredienteBO = ingredienteBO;
        initUI();
    }

    private void initUI() {
        setTitle("Maye's Family Diner — Buscador de Ingredientes");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(720, 440));

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                cerrar();
            }
        });

        JPanel fondo = new JPanel(new BorderLayout(0, 0));
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        // Panel principal
        JPanel contenido = new JPanel(new BorderLayout(14, 0));
        contenido.setOpaque(false);
        contenido.setBorder(new EmptyBorder(24, 28, 16, 28));

        // Título
        JLabel lblTitulo = UI.tituloGrande("Buscador de Ingredientes");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 18, 0));
        contenido.add(lblTitulo, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"ID", "Nombre", "Unidad de Medida", "Stock"};
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

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Georgia", Font.BOLD, 13));
        header.setBackground(UI.AZUL_NORMAL);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 32));

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columnas.length; i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(220);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(160);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(100);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(UI.BORDE_ORO, 1));
        scroll.getViewport().setBackground(new Color(0xFDF6E3));
        contenido.add(scroll, BorderLayout.CENTER);

        // ── Panel lateral de filtros ───────────────────────────────────────
        JPanel lateral = new JPanel();
        lateral.setOpaque(false);
        lateral.setLayout(new BoxLayout(lateral, BoxLayout.Y_AXIS));
        lateral.setBorder(new EmptyBorder(0, 16, 0, 0));
        lateral.setPreferredSize(new Dimension(200, 0));

        JLabel lblFiltros = UI.titulo("Opciones de Filtrado");
        lblFiltros.setAlignmentX(Component.LEFT_ALIGNMENT);
        lateral.add(lblFiltros);
        lateral.add(Box.createVerticalStrut(12));

        // Filtro por nombre
        JLabel lblNombre = UI.titulo("Nombre:");
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        lateral.add(lblNombre);
        txtFiltroNombre = new JTextField();
        txtFiltroNombre.setFont(new Font("Georgia", Font.PLAIN, 13));
        txtFiltroNombre.setBackground(UI.AZUL_NORMAL.brighter());
        txtFiltroNombre.setMaximumSize(new Dimension(180, 30));
        txtFiltroNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        lateral.add(txtFiltroNombre);
        lateral.add(Box.createVerticalStrut(10));

        // Filtro por unidad
        JLabel lblUnidad = UI.titulo("Unidad de medida:");
        lblUnidad.setAlignmentX(Component.LEFT_ALIGNMENT);
        lateral.add(lblUnidad);
        cmbFiltroUnidad = new JComboBox<>(new String[]{"Todas", "PIEZAS", "GRAMOS", "MILILITROS"});
        cmbFiltroUnidad.setFont(new Font("Georgia", Font.PLAIN, 13));
        cmbFiltroUnidad.setMaximumSize(new Dimension(180, 30));
        cmbFiltroUnidad.setAlignmentX(Component.LEFT_ALIGNMENT);
        lateral.add(cmbFiltroUnidad);
        lateral.add(Box.createVerticalStrut(16));

        JButton btnBuscar = UI.botonPrimario("Buscar");
        btnBuscar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnBuscar.setMaximumSize(new Dimension(180, 40));
        btnBuscar.addActionListener(e -> buscar());
        lateral.add(btnBuscar);

        contenido.add(lateral, BorderLayout.EAST);
        fondo.add(contenido, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(4, 0, 12, 0));
        footer.setLayout(new BoxLayout(footer, BoxLayout.X_AXIS));

        footer.add(Box.createHorizontalGlue());

        JButton btnCerrar = UI.boton("Atrás", new Color(0xC0392B), new Color(0x922B21));
        btnCerrar.setPreferredSize(new Dimension(130, 42));
        btnCerrar.setMaximumSize(new Dimension(130, 42));
        btnCerrar.addActionListener(e -> cerrar());
        footer.add(btnCerrar);

        // Botón "Seleccionar" solo visible en modo selección
        JButton btnSeleccionar = UI.boton("Seleccionar", new Color(0x27AE60), new Color(0x1E8449));
        btnSeleccionar.setPreferredSize(new Dimension(140, 42));
        btnSeleccionar.setMaximumSize(new Dimension(140, 42));
        btnSeleccionar.setVisible(false); // se activa en modo selección
        btnSeleccionar.addActionListener(e -> confirmarSeleccion());
        footer.add(Box.createHorizontalStrut(12));
        footer.add(btnSeleccionar);

        footer.add(Box.createHorizontalGlue());
        fondo.add(footer, BorderLayout.SOUTH);

        // Guardamos referencia al botón seleccionar para activarlo si aplica
        this.btnSeleccionarRef = btnSeleccionar;
    }

    /**
     * Referencia interna al botón seleccionar para mostrarlo/ocultarlo.
     */
    private JButton btnSeleccionarRef;

    /**
     * Abre el buscador en modo standalone (solo consulta). Carga todos los
     * ingredientes al iniciar.
     */
    public void abrirModoConsulta() {
        callbackSeleccion = null;
        btnSeleccionarRef.setVisible(false);
        cargarTodos();
    }

    /**
     * Abre el buscador en modo selección. Al presionar "Seleccionar",
     * invoca el callback con el DTO elegido.
     *
     * @param callback función que recibe el IngredienteDTO seleccionado
     */
    public void abrirModoSeleccion(Consumer<IngredienteDTO> callback) {
        this.callbackSeleccion = callback;
        btnSeleccionarRef.setVisible(true);
        cargarTodos();
    }

    // Lógica interna
    /**
     * Carga todos los ingredientes sin filtros.
     */
    public void cargarTodos() {
        buscarConFiltros(null, null);
    }

    private void buscar() {
        String nombre = txtFiltroNombre.getText().trim();
        String unidad = (String) cmbFiltroUnidad.getSelectedItem();
        buscarConFiltros(
                nombre.isEmpty() ? null : nombre,
                "Todas".equals(unidad) ? null : unidad
        );
    }

    private void buscarConFiltros(String nombre, String unidad) {
        try {
            ingredientesActuales = ingredienteBO.buscarPorFiltros(nombre, unidad);
            refrescarTabla();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        if (ingredientesActuales == null) {
            return;
        }
        for (IngredienteDTO ing : ingredientesActuales) {
            modeloTabla.addRow(new Object[]{
                ing.getId(),
                ing.getNombre(),
                ing.getUnidadMedida(),
                ing.getStock() != null ? String.format("%.2f", ing.getStock()) : "0.00"
            });
        }
    }

    private void confirmarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un ingrediente de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        IngredienteDTO seleccionado = ingredientesActuales.get(fila);
        if (callbackSeleccion != null) {
            callbackSeleccion.accept(seleccionado);
        }
        setVisible(false);
    }

    private void cerrar() {
        if (callbackSeleccion != null) {
            // Modo selección: solo ocultamos, no navegamos
            setVisible(false);
        } else {
            // Modo standalone: regresamos al módulo de ingredientes
            coordinador.regresarDesdeBuscadorIngredientes();
        }
    }
}
