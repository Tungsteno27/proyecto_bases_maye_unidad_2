/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import DTOs.IngredienteDTO;
import DTOs.ProductoDTO;
import DTOs.ProductoIngredienteDTO;
import EstilosGUI.UI;
import coordinador.Coordinador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Tungs
 */
public class FrmRegistrarProducto extends JFrame {
    private final Coordinador coordinador;

    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtTipo;
    private JTextArea txtDescripcion;

    private JTable tablaIngredientes;
    private DefaultTableModel modeloTabla;

    private List<ProductoIngredienteDTO> ingredientesSeleccionados = new ArrayList<>();

    private String imagenUrl;
    private JLabel lblPreviewImagen;
    
    private JComboBox<String> cmbEstado;

    private boolean modoEdicion = false;
    private Long idProducto;

    public FrmRegistrarProducto(Coordinador coordinador) {
        this.coordinador = coordinador;
        initUI();
    }

    private void initUI() {
        setTitle("Registrar / Editar Producto");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                coordinador.regresarDesdeRegistrarProducto();
            }
        });

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);
        
        JPanel contenido = new JPanel(new BorderLayout(14, 0));
        contenido.setOpaque(false);
        contenido.setBorder(new EmptyBorder(24, 28, 16, 28));
        
        JLabel titulo = UI.tituloGrande("Registrar / Editar Producto");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(new EmptyBorder(0, 0, 18, 0));
        contenido.add(titulo, BorderLayout.NORTH);

        String[] columnas = {"Nombre", "Unidad", "Cantidad"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return c == 2; }
        };
        
        tablaIngredientes = new JTable(modeloTabla);
        tablaIngredientes.setFont(new Font("Georgia", Font.PLAIN, 13));
        tablaIngredientes.setRowHeight(28);
        tablaIngredientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaIngredientes.setBackground(new Color(0xFDF6E3));
        tablaIngredientes.setGridColor(UI.BORDE_ORO);
        tablaIngredientes.setShowGrid(true);
        tablaIngredientes.setFillsViewportHeight(true);

        JTableHeader header = tablaIngredientes.getTableHeader();
        header.setFont(new Font("Georgia", Font.BOLD, 13));
        header.setBackground(UI.AZUL_NORMAL);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 32));
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columnas.length; i++) {
            tablaIngredientes.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
        tablaIngredientes.getColumnModel().getColumn(0).setPreferredWidth(220);
        tablaIngredientes.getColumnModel().getColumn(1).setPreferredWidth(120);
        tablaIngredientes.getColumnModel().getColumn(2).setPreferredWidth(80);

        JScrollPane scrollTabla = new JScrollPane(tablaIngredientes);
        scrollTabla.setBorder(BorderFactory.createLineBorder(UI.BORDE_ORO, 1));
        scrollTabla.getViewport().setBackground(new Color(0xFDF6E3));
        contenido.add(scrollTabla, BorderLayout.CENTER);

        JPanel lateral = new JPanel();
        lateral.setOpaque(false);
        lateral.setLayout(new BoxLayout(lateral, BoxLayout.Y_AXIS));
        lateral.setBorder(new EmptyBorder(0, 0, 0, 16));
        lateral.setPreferredSize(new Dimension(300, 0));

        JLabel lblDatos = UI.titulo("Datos del Producto");
        lblDatos.setAlignmentX(Component.LEFT_ALIGNMENT);
        lateral.add(lblDatos);
        lateral.add(Box.createVerticalStrut(12));

        Font fuenteLabel = new Font("Georgia", Font.PLAIN, 13);
        Dimension dimCampo = new Dimension(280, 30);

        

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(fuenteLabel);
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        lateral.add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setFont(fuenteLabel);
        txtNombre.setMaximumSize(dimCampo);
        txtNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtNombre.setBackground(UI.AZUL_NORMAL.brighter());
        lateral.add(txtNombre);
        lateral.add(Box.createVerticalStrut(8));

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setFont(fuenteLabel);
        lblPrecio.setAlignmentX(Component.LEFT_ALIGNMENT);
        lateral.add(lblPrecio);
        txtPrecio = new JTextField();
        txtPrecio.setFont(fuenteLabel);
        txtPrecio.setMaximumSize(dimCampo);
        txtPrecio.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtPrecio.setBackground(UI.AZUL_NORMAL.brighter());
        lateral.add(txtPrecio);
        lateral.add(Box.createVerticalStrut(8));

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setFont(fuenteLabel);
        lblTipo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lateral.add(lblTipo);
        txtTipo = new JTextField();
        txtTipo.setFont(fuenteLabel);
        txtTipo.setMaximumSize(dimCampo);
        txtTipo.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtTipo.setBackground(UI.AZUL_NORMAL.brighter());
        lateral.add(txtTipo);
        lateral.add(Box.createVerticalStrut(8));
        
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(fuenteLabel);
        lblEstado.setAlignmentX(Component.LEFT_ALIGNMENT);
        lateral.add(lblEstado);

        cmbEstado = new JComboBox<>(new String[]{"ACTIVO", "INACTIVO"});
        cmbEstado.setFont(fuenteLabel);
        cmbEstado.setMaximumSize(dimCampo);
        cmbEstado.setAlignmentX(Component.LEFT_ALIGNMENT);
        cmbEstado.setBackground(UI.AZUL_NORMAL.brighter());
        lateral.add(cmbEstado);
        lateral.add(Box.createVerticalStrut(8));

        JLabel lblDesc = new JLabel("Descripción:");
        lblDesc.setFont(fuenteLabel);
        lblDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        lateral.add(lblDesc);
        txtDescripcion = new JTextArea(3, 20);
        txtDescripcion.setFont(fuenteLabel);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBackground(UI.AZUL_NORMAL.brighter());
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        scrollDesc.setMaximumSize(new Dimension(280, 70));
        scrollDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollDesc.setBorder(BorderFactory.createLineBorder(UI.BORDE_ORO, 1));
        lateral.add(scrollDesc);
        lateral.add(Box.createVerticalStrut(16));

        JLabel lblImagenTitulo = UI.titulo("Imagen");
        lblImagenTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lateral.add(lblImagenTitulo);
        lateral.add(Box.createVerticalStrut(8));

        lblPreviewImagen = new JLabel("", SwingConstants.CENTER);
        lblPreviewImagen.setPreferredSize(new Dimension(280, 200));
        lblPreviewImagen.setMinimumSize(new Dimension(280, 200));
        lblPreviewImagen.setMaximumSize(new Dimension(280, 200));
        lblPreviewImagen.setOpaque(true);
        lblPreviewImagen.setBackground(new Color(0xFDF6E3));
        lblPreviewImagen.setBorder(BorderFactory.createLineBorder(UI.BORDE_ORO, 1));
        lblPreviewImagen.setAlignmentX(Component.LEFT_ALIGNMENT);
        lateral.add(lblPreviewImagen);
        lateral.add(Box.createVerticalStrut(8));

        JButton btnImagen = UI.boton("Seleccionar imagen",
                new Color(0x5DADE2), new Color(0x2E86C1));
        btnImagen.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnImagen.setMaximumSize(new Dimension(280, 40));
        btnImagen.addActionListener(e -> seleccionarImagen());
        lateral.add(btnImagen);

        contenido.add(lateral, BorderLayout.WEST);
        fondo.add(contenido, BorderLayout.CENTER);

        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(4, 28, 12, 28));
        footer.setLayout(new BoxLayout(footer, BoxLayout.X_AXIS));

        JButton btnGuardar = UI.botonPrimario("Guardar");
        btnGuardar.setPreferredSize(new Dimension(130, 42));
        btnGuardar.setMaximumSize(new Dimension(130, 42));
        btnGuardar.addActionListener(e -> guardarProducto());

        JButton btnAtras = UI.boton("Atrás",
                new Color(0xC0392B), new Color(0x922B21));
        btnAtras.setPreferredSize(new Dimension(130, 42));
        btnAtras.setMaximumSize(new Dimension(130, 42));
        btnAtras.addActionListener(e -> coordinador.regresarDesdeRegistrarProducto());

        JButton btnAgregar = UI.botonPrimario("Agregar ingrediente");
        btnAgregar.setPreferredSize(new Dimension(180, 42));
        btnAgregar.setMaximumSize(new Dimension(180, 42));
        btnAgregar.addActionListener(e -> abrirBuscadorIngredientes());

        JButton btnEliminar = UI.boton("Eliminar",
                new Color(0xC0392B), new Color(0x922B21));
        btnEliminar.setPreferredSize(new Dimension(130, 42));
        btnEliminar.setMaximumSize(new Dimension(130, 42));
        btnEliminar.addActionListener(e -> eliminarIngrediente());

        footer.add(btnGuardar);
        footer.add(Box.createHorizontalStrut(10));
        footer.add(btnAtras);
        footer.add(Box.createHorizontalGlue());
        footer.add(btnAgregar);
        footer.add(Box.createHorizontalStrut(10));
        footer.add(btnEliminar);

        fondo.add(footer, BorderLayout.SOUTH);
    }
    
    /**
     * Método que crea el fileChooser para poderle asignar una imagen al producto
     */
    private void seleccionarImagen() {
        JFileChooser chooser = new JFileChooser();
        int res = chooser.showOpenDialog(this);

        if (res == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            imagenUrl = archivo.getAbsolutePath();
            mostrarImagen(imagenUrl);
        }
    }
    
    /**
     * Método que muestra la imagen del producto, diferenciando entre imágenes del filechooser e imagenes precargadas (inserts masivos)
     * @param path la ruta del archivo de imagen
     */
    private void mostrarImagen(String path) {
        try {
            ImageIcon icono;

            File file = new File(path);

            if (file.exists()) {
                icono = new ImageIcon(path);
            } else {
                icono = new ImageIcon(getClass().getResource("/imagenes/" + path));
            }

            Image img = icono.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
            lblPreviewImagen.setIcon(new ImageIcon(img));
            lblPreviewImagen.setText(null);

        } catch (Exception e) {
            lblPreviewImagen.setIcon(null);
            lblPreviewImagen.setText("Sin imagen");
        }
    }
    /**
     * Método que abre el buscador de ingredientes
     */
    private void abrirBuscadorIngredientes() {
        coordinador.abrirBuscadorIngredientesSeleccion(dto -> {

            ProductoIngredienteDTO pi = new ProductoIngredienteDTO();
            pi.setIdIngrediente(dto.getId());
            pi.setNombreIngrediente(dto.getNombre());
            pi.setUnidad(dto.getUnidadMedida());
            pi.setCantidad(1.0);

            ingredientesSeleccionados.add(pi);

            modeloTabla.addRow(new Object[]{
                dto.getNombre(),
                dto.getUnidadMedida(),
                1.0
            });
        });
    }
    
    /**
     * Método que
     */
    private void eliminarIngrediente() {
        int fila = tablaIngredientes.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un ingrediente");
            return;
        }

        ingredientesSeleccionados.remove(fila);
        modeloTabla.removeRow(fila);
    }

    private void guardarProducto() {
        try {
            if (txtNombre.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre obligatorio");
                return;
            }

            if (txtPrecio.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Precio obligatorio");
                return;
            }

            if (ingredientesSeleccionados.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debe agregar al menos un ingrediente");
                return;
            }

            ProductoDTO dto = new ProductoDTO();

            if (modoEdicion) {
                dto.setId(idProducto);
                dto.setEstado((String) cmbEstado.getSelectedItem());
            }

            dto.setNombre(txtNombre.getText());
            dto.setPrecio(Double.parseDouble(txtPrecio.getText()));
            dto.setTipo(txtTipo.getText());
            dto.setDescripcion(txtDescripcion.getText());
            dto.setImagenUrl(imagenUrl);
            dto.setIngredientes(ingredientesSeleccionados);

            String error = modoEdicion
                    ? coordinador.actualizarProducto(dto)
                    : coordinador.registrarProducto(dto);

            if (error == null) {
                JOptionPane.showMessageDialog(this,
                        modoEdicion ? "Producto actualizado" : "Producto registrado");
                coordinador.regresarDesdeRegistrarProducto();
            } else {
                JOptionPane.showMessageDialog(this, error);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en datos");
        }
    }

    public void cargarProducto(ProductoDTO dto) {
        modoEdicion = true;
        idProducto = dto.getId();

        txtNombre.setText(dto.getNombre());
        txtPrecio.setText(String.valueOf(dto.getPrecio()));
        txtTipo.setText(dto.getTipo());
        txtDescripcion.setText(dto.getDescripcion());
        
        if (dto.getEstado() != null) {
            cmbEstado.setSelectedItem(dto.getEstado());
        }
        imagenUrl = dto.getImagenUrl();
        if (imagenUrl != null) {
            mostrarImagen(imagenUrl);
        }

        modeloTabla.setRowCount(0);
        ingredientesSeleccionados.clear();

        for (ProductoIngredienteDTO pi : dto.getIngredientes()) {
            ingredientesSeleccionados.add(pi);

            modeloTabla.addRow(new Object[]{
                pi.getNombreIngrediente(),
                pi.getUnidad(),
                pi.getCantidad()
            });
        }
    }

    public void limpiar() {
        modoEdicion = false;
        idProducto = null;

        txtNombre.setText("");
        txtPrecio.setText("");
        txtTipo.setText("");
        txtDescripcion.setText("");
        cmbEstado.setSelectedIndex(0); 
        imagenUrl = null;
        lblPreviewImagen.setIcon(null);
        lblPreviewImagen.setText("Sin imagen");

        ingredientesSeleccionados.clear();
        modeloTabla.setRowCount(0);
    }
}
