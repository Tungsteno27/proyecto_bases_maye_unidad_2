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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Formulario para modificar un ingrediente existente. Se pre-llena con los
 * datos actuales del ingrediente al abrirse.
 *
 * @author Noelia E.N.
 */
public class FrmModificarIngrediente extends JFrame {

    private final Coordinador coordinador;
    private final IngredienteBO ingredienteBO;

    private JTextField txtNombre;
    private JComboBox<String> cmbUnidad;
    private JTextField txtCantidad;
    private JLabel lblImagenPreview;
    private File imagenSeleccionada;
    private Long idIngrediente;

    /**
     * Constructor del formulario de modificación de ingredientes.
     *
     * @param coordinador el coordinador de navegación
     * @param ingredienteBO el BO de ingredientes
     */
    public FrmModificarIngrediente(Coordinador coordinador, IngredienteBO ingredienteBO) {
        this.coordinador = coordinador;
        this.ingredienteBO = ingredienteBO;
        initUI();
    }

    private void initUI() {
        setTitle("Maye's Family Diner — Modificar Ingrediente");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.regresarDesdeModificarIngrediente();
            }
        });

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(36, 60, 36, 60));

        JLabel lblTitulo = UI.tituloGrande("Modificar ingrediente");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(26));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setAlignmentX(Component.CENTER_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 6, 8, 6);
        gbc.anchor = GridBagConstraints.WEST;

        txtNombre = campoTexto();
        agregarFila(form, gbc, 0, "Nombre:*", txtNombre);

        cmbUnidad = new JComboBox<>(new String[]{"PIEZAS", "GRAMOS", "MILILITROS"});
        cmbUnidad.setFont(new Font("Georgia", Font.PLAIN, 14));
        cmbUnidad.setBackground(UI.AZUL_NORMAL.brighter());
        cmbUnidad.setPreferredSize(new Dimension(200, 32));
        agregarFilaComponente(form, gbc, 1, "Unidad medida:*", cmbUnidad);

        txtCantidad = campoTexto();
        agregarFila(form, gbc, 2, "Cantidad:*", txtCantidad);

        // Imagen opcional
        JPanel panelImagen = new JPanel(new GridBagLayout());
        panelImagen.setOpaque(false);
        GridBagConstraints gbcImg = new GridBagConstraints();
        gbcImg.insets = new Insets(0, 0, 0, 8);

        lblImagenPreview = new JLabel("Sin imagen");
        lblImagenPreview.setFont(new Font("Georgia", Font.PLAIN, 12));
        lblImagenPreview.setForeground(UI.TEXTO_OSCURO);
        lblImagenPreview.setPreferredSize(new Dimension(60, 60));
        lblImagenPreview.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagenPreview.setBorder(BorderFactory.createLineBorder(UI.BORDE_ORO));
        gbcImg.gridx = 0;
        gbcImg.gridy = 0;
        panelImagen.add(lblImagenPreview, gbcImg);

        JButton btnSeleccionarImg = UI.botonPrimario("Seleccionar");
        btnSeleccionarImg.setFont(new Font("Georgia", Font.PLAIN, 13));
        btnSeleccionarImg.setPreferredSize(new Dimension(130, 32));
        btnSeleccionarImg.addActionListener(e -> seleccionarImagen());
        gbcImg.gridx = 1;
        gbcImg.insets = new Insets(0, 8, 0, 0);
        panelImagen.add(btnSeleccionarImg, gbcImg);

        agregarFilaComponente(form, gbc, 3, "Imagen:", panelImagen);

        card.add(form);
        card.add(Box.createVerticalStrut(28));

        JPanel botones = new JPanel();
        botones.setOpaque(false);
        botones.setLayout(new BoxLayout(botones, BoxLayout.X_AXIS));
        botones.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnRegresar = UI.boton("Regresar", new java.awt.Color(0x2C6E8A), new java.awt.Color(0x1E5470));
        btnRegresar.setPreferredSize(new Dimension(140, 44));
        btnRegresar.setMaximumSize(new Dimension(140, 44));
        btnRegresar.addActionListener(e -> coordinador.regresarDesdeModificarIngrediente());

        JButton btnAceptar = UI.boton("Aceptar", new java.awt.Color(0x27AE60), new java.awt.Color(0x1E8449));
        btnAceptar.setPreferredSize(new Dimension(140, 44));
        btnAceptar.setMaximumSize(new Dimension(140, 44));
        btnAceptar.addActionListener(e -> guardar());

        botones.add(btnRegresar);
        botones.add(Box.createHorizontalStrut(20));
        botones.add(btnAceptar);
        card.add(botones);

        UI.centrar(fondo, card);
    }

    /**
     * Pre-llena el formulario con los datos del ingrediente a modificar.
     *
     * @param dto el DTO con los datos actuales del ingrediente
     */
    public void cargarDatos(IngredienteDTO dto) {
        this.idIngrediente = dto.getId();
        txtNombre.setText(dto.getNombre() != null ? dto.getNombre() : "");
        txtCantidad.setText(dto.getStock() != null ? String.valueOf(dto.getStock()) : "");
        if (dto.getUnidadMedida() != null) {
            cmbUnidad.setSelectedItem(dto.getUnidadMedida().toUpperCase());
        }
        lblImagenPreview.setIcon(null);
        lblImagenPreview.setText("Sin imagen");
        imagenSeleccionada = null;
    }

    private void seleccionarImagen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Imágenes (jpg, png, gif)", "jpg", "jpeg", "png", "gif"));
        int resultado = chooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            imagenSeleccionada = chooser.getSelectedFile();
            ImageIcon icono = new ImageIcon(
                    new ImageIcon(imagenSeleccionada.getAbsolutePath())
                            .getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
            lblImagenPreview.setIcon(icono);
            lblImagenPreview.setText("");
        }
    }

    private void guardar() {
        String nombre = txtNombre.getText().trim();
        String unidad = (String) cmbUnidad.getSelectedItem();
        String cantidadTexto = txtCantidad.getText().trim();

        Double stock = null;
        if (!cantidadTexto.isEmpty()) {
            try {
                stock = Double.parseDouble(cantidadTexto);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "La cantidad debe ser un número válido.",
                        "Error de validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        IngredienteDTO dto = new IngredienteDTO();
        dto.setId(idIngrediente);
        dto.setNombre(nombre);
        dto.setUnidadMedida(unidad);
        dto.setStock(stock);

        try {
            ingredienteBO.actualizar(dto);
            coordinador.mostrarExito("Ingrediente actualizado con éxito", "modificar_ingrediente");
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error de validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Helpers
    private JTextField campoTexto() {
        JTextField tf = new JTextField(18);
        tf.setFont(new Font("Georgia", Font.PLAIN, 14));
        tf.setForeground(UI.TEXTO_OSCURO);
        tf.setBackground(UI.AZUL_NORMAL.brighter());
        tf.setPreferredSize(new Dimension(200, 32));
        return tf;
    }

    private void agregarFila(JPanel form, GridBagConstraints gbc, int fila, String etiqueta, JTextField campo) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0;
        JLabel lbl = UI.titulo(etiqueta);
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        form.add(lbl, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        form.add(campo, gbc);
    }

    private void agregarFilaComponente(JPanel form, GridBagConstraints gbc, int fila, String etiqueta, Component comp) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0;
        JLabel lbl = UI.titulo(etiqueta);
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        form.add(lbl, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        form.add(comp, gbc);
    }
}
