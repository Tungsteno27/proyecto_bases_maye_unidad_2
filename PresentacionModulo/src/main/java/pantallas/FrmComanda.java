/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import DTOs.ClienteFrecuenteDTO;
import DTOs.ComandaDTO;
import DTOs.ComandaProductoDTO;
import DTOs.EstadoComandaDTO;
import DTOs.MesaDTO;
import DTOs.ProductoDTO;
import EstilosGUI.UI;
import coordinador.Coordinador;
import excepciones.NegocioException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dayanara Peralta G
 */
public class FrmComanda extends JFrame{
    private final Coordinador coordinador;
    private MesaDTO mesaA;
    private ClienteFrecuenteDTO cliente;
    private JTextField textFolio;
    private JTextField textMesa;
    private JTextField textCliente;
    private JTextField textProducto;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    public FrmComanda(Coordinador coordinador) {
        this.coordinador = coordinador;
        initUI();
    }
    
    private void initUI(){
        setTitle("Registrar comanda");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.rolMeseroSeleccionado();
            }
        });
        
        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);
        
        JPanel card = UI.card();
        card.setLayout(new BorderLayout(15, 15));
        card.setBorder(new EmptyBorder(25, 30, 30, 30));

        JLabel lblTitulo = UI.tituloGrande("Registrar comanda");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(lblTitulo, BorderLayout.NORTH);
        
        JPanel superior = new JPanel();
        superior.setLayout(new javax.swing.BoxLayout(superior, javax.swing.BoxLayout.Y_AXIS));
        superior.setOpaque(false);
        
        JPanel folioYmesa = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        folioYmesa.setOpaque(false);
        folioYmesa.add(new JLabel("Folio:"));
        textFolio = UI.texto();
        textFolio.setEditable(false);
        textFolio.setPreferredSize(new Dimension(300, 35));
        folioYmesa.add(textFolio);
        
        folioYmesa.add(new JLabel("Mesa:"));
        textMesa = UI.texto();
        textMesa.setEditable(false);
        textMesa.setPreferredSize(new Dimension(80, 35));
        folioYmesa.add(textMesa);
        
        JPanel filaCliente =new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filaCliente.setOpaque(false);
        filaCliente.add(new JLabel("Cliente:"));
        textCliente = UI.texto();
        textCliente.setEditable(false);
        textCliente.setPreferredSize(new Dimension(450, 35));
        filaCliente.add(textCliente);
        
        JPanel filaProducto = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filaProducto.setOpaque(false);
        
        JButton btnBuscar = UI.boton("buscar producto", UI.AZUL_OSCURO, UI.AZUL_OSCURO_HOVER);
        btnBuscar.setPreferredSize(new Dimension(200, 35));
        btnBuscar.addActionListener(e -> coordinador.abrirBuscadorProductosComanda());
        filaProducto.add(btnBuscar);
        
        superior.add(folioYmesa);
        superior.add(javax.swing.Box.createVerticalStrut(10));
        superior.add(filaCliente);
        superior.add(javax.swing.Box.createVerticalStrut(10));
        superior.add(filaProducto);
        
        String[] columnas = {"Nombre", "Descripción", "Precio", "Tipo", "Especificaciones", ""};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaProductos = new JTable(modeloTabla);
        configurarTabla();
        
        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBorder(BorderFactory.createLineBorder(UI.BORDE_ORO, 2));
        
        JPanel centro = new JPanel(new BorderLayout(0, 20));
        centro.setOpaque(false);
        centro.add(superior, BorderLayout.NORTH);
        centro.add(scroll, BorderLayout.CENTER);
        
        card.add(centro, BorderLayout.CENTER);
        
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        footer.setOpaque(false);
        
        JButton btnCancelar = UI.boton("Cancelar", UI.AZUL_OSCURO, UI.AZUL_OSCURO_HOVER);
        btnCancelar.setPreferredSize(new Dimension(160, 45));
        btnCancelar.addActionListener(e -> coordinador.rolMeseroSeleccionado());
        
        JButton btnRegistrar = UI.boton("Aceptar", UI.AZUL_OSCURO, UI.AZUL_OSCURO_HOVER);
        btnRegistrar.setPreferredSize(new Dimension(160, 45));
        btnRegistrar.addActionListener(e -> registrarComanda());
        
        footer.add(btnCancelar);
        footer.add(btnRegistrar);
        card.add(footer, BorderLayout.SOUTH);

        fondo.add(card, BorderLayout.CENTER);

    }
    
    private void configurarTabla() {
        tablaProductos.setRowHeight(35);
        tablaProductos.setShowGrid(false);
        
        DefaultTableCellRenderer rendererCebreado = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(0xF4F7F9));
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        };
        for (int i = 0; i < tablaProductos.getColumnCount(); i++) {
            tablaProductos.getColumnModel().getColumn(i).setCellRenderer(rendererCebreado);
        }
        
        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e){
                int fila = tablaProductos.rowAtPoint(e.getPoint());
                int colum = tablaProductos.columnAtPoint(e.getPoint());
                if(colum == 5 && fila >= 0){
                    eliminarFila(fila);
                }
            }
        });
    }
    
    public void cargarDatos(MesaDTO mesa, ClienteFrecuenteDTO cliente) throws NegocioException {
        limpiarFormulario();
        this.mesaA = mesa;
        this.cliente = cliente;
        String folio = coordinador.obtenerFolio();
        textFolio.setText(folio);
        
        textMesa.setText(String.valueOf(mesa.getNuemro()));
        
        if (cliente != null) {
            textCliente.setText(cliente.getNombres() + " " + cliente.getApellidoPaterno());
        } else {
            textCliente.setText("Público en General");
        }
    }
    
    public void agregarProducto(ProductoDTO prod){
        DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
        
        modelo.addRow(new Object[]{
            prod, prod.getDescripcion(), prod.getPrecio(), prod.getTipo(),
            "", "Eliminar"
        });
        
    }
    
    private void eliminarFila(int fila){
        DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
        int resp = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea eliminar?", "Eliminar producto", javax.swing.JOptionPane.YES_NO_OPTION);
        if(resp == javax.swing.JOptionPane.YES_OPTION){
            modelo.removeRow(fila);
        }
    }
    
    private void registrarComanda(){
        try{
            if(tablaProductos.getRowCount() == 0){
                JOptionPane.showMessageDialog(this, "Debes agregar al menos un producto");
                return;
            }
            ComandaDTO newComanda = new ComandaDTO();
            newComanda.setFolio(textFolio.getText());
            if(this.mesaA == null){
                JOptionPane.showMessageDialog(this, "Error: no hay ninguna mesa");
                return;
            }
            newComanda.setMesa(this.mesaA);
            newComanda.setCliente(this.cliente);
            newComanda.setFechaHora(LocalDateTime.now());
            newComanda.setEstado(EstadoComandaDTO.ABIERTA);
            
            List<ComandaProductoDTO> detalles = new ArrayList<>();
            DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
            
            for(int i = 0; i < modelo.getRowCount(); i++){
                ComandaProductoDTO detalle = new ComandaProductoDTO();
                ProductoDTO prod = (ProductoDTO) modelo.getValueAt(i, 0);
                
                String espe = modelo.getValueAt(i, 4).toString();
                detalle.setProducto(prod);
                detalle.setCantidad(1D);
                detalle.setComentario(espe);
                detalles.add(detalle);
            }
            newComanda.setComandaProductos(detalles);
            coordinador.guardarComanda(newComanda);
            limpiarFormulario();
            this.dispose();
        }catch(Exception e){
            e.printStackTrace(); // Esto imprimirá el error en la consola
            JOptionPane.showMessageDialog(this, "Error interno: " + e.getMessage());
        }
    }
    
    public void limpiarFormulario() {
        textFolio.setText("");
        textMesa.setText("");
        textCliente.setText("");

        if (modeloTabla != null) {
            modeloTabla.setRowCount(0);
        }
        this.mesaA = null;
        this.cliente = null;
    }
    
}
