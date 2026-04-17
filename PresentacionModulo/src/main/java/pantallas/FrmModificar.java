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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
public class FrmModificar extends JFrame{
    private final Coordinador coordinador;
    private MesaDTO mesaA;
    private ClienteFrecuenteDTO cliente;
    private ComandaDTO comanda;
    private JTextField textFolio;
    private JTextField textMesa;
    private JTextField textCliente;
    private JTextField textProducto;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> estado;

    public FrmModificar(Coordinador coordinador) {
        this.coordinador = coordinador;
        initUI();
    }
    
    private void initUI(){
        setTitle("Modificar comanda");
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

        JLabel lblTitulo = UI.tituloGrande("Modificar comanda");
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
        
        JPanel filaProducto = new JPanel(new BorderLayout());
        filaProducto.setOpaque(false);
        
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelEstado.setOpaque(false);
        panelEstado.add(new JLabel("Estado: "));
        
        String[] estados = {"ABIERTA", "ENTREGADA", "CANCELADA"};
        estado = new JComboBox<>(estados);
        estado.setBackground(Color.WHITE);
        estado.setFont(new Font("Georgia", Font.PLAIN, 14));
        estado.setBorder(BorderFactory.createLineBorder(new Color(0xD4AF37)));
        panelEstado.add(estado);
        
        JButton btnBuscar = UI.boton("buscar producto", UI.AZUL_OSCURO, UI.AZUL_OSCURO_HOVER);
        btnBuscar.setPreferredSize(new Dimension(200, 35));
        btnBuscar.addActionListener(e -> coordinador.abrirBuscadorProductosComanda());
        
        filaProducto.add(panelEstado, BorderLayout.WEST);
        filaProducto.add(btnBuscar, BorderLayout.EAST);
        
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
        btnRegistrar.addActionListener(e -> comandaModificada());
        
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
    
    public void cargarDatos(ComandaDTO dto){
        this.comanda = dto;
        this.mesaA = dto.getMesa();
        this.cliente = (ClienteFrecuenteDTO) dto.getCliente();
        
        textFolio.setText(dto.getFolio());
        textMesa.setText(String.valueOf(dto.getMesa().getNuemro()));
        
        if (dto.getCliente() != null) {
            textCliente.setText(dto.getCliente().getNombres());
        } else {
            textCliente.setText("Publico general");
        }
        estado.setSelectedItem(dto.getEstado().toString());
        
        actualizar(dto.getComandaProductos());
        
        textFolio.setEditable(false);
        textMesa.setEditable(false);
    }
    
    private void actualizar(List<ComandaProductoDTO> producto) {
        modeloTabla.setRowCount(0);
        if(producto != null){
            for(ComandaProductoDTO c : producto){
                modeloTabla.addRow(new Object[]{
                    c.getProducto(),
                    c.getProducto().getDescripcion(),
                    c.getProducto().getPrecio(),
                    c.getProducto().getTipo(),
                    c.getComentario(),
                    "Eliminar"
                });
            }
        }
    }
    
    private void comandaModificada(){
        try{
            if(tablaProductos.getRowCount() == 0){
                JOptionPane.showMessageDialog(this, "Debes agregar al menos un producto");
                return;
            }
            ComandaDTO cambiada = new ComandaDTO();
            cambiada.setId(this.comanda.getId());
            cambiada.setFolio(textFolio.getText());
            cambiada.setMesa(this.mesaA);
            cambiada.setCliente(this.cliente);
            cambiada.setFechaHora(LocalDateTime.now());
            String estado = (String) this.estado.getSelectedItem();
            cambiada.setEstado(EstadoComandaDTO.valueOf(estado));
            
            List<ComandaProductoDTO> detalles = new ArrayList<>();
            DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
            
            double total = 0;
            for(int i = 0; i < modelo.getRowCount(); i++){
                ComandaProductoDTO detalle = new ComandaProductoDTO();
                ProductoDTO prod = (ProductoDTO) modelo.getValueAt(i, 0);
                
                String espe = (modelo.getValueAt(i, 4) != null) ? modelo.getValueAt(i, 4).toString() : "";
                
                detalle.setProducto(prod);
                detalle.setCantidad(1D);
                detalle.setComentario(espe);
                
                detalles.add(detalle);
                total += prod.getPrecio();
            }
            cambiada.setComandaProductos(detalles);
            cambiada.setTotalComanda(total);
            coordinador.actualizarComanda(cambiada);
            JOptionPane.showMessageDialog(this, "¡Comanda modificada!");
            this.dispose();
            coordinador.rolMeseroSeleccionado();
        }catch(Exception e){
            e.printStackTrace(); // Esto imprimirá el error en la consola
            JOptionPane.showMessageDialog(this, "Error interno: " + e.getMessage());
        }
    }
}
