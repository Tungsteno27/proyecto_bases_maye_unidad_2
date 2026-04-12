/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import DTOs.ComandaDTO;
import EstilosGUI.UI;
import coordinador.Coordinador;
import excepciones.PersistenciaException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
 *
 * @author Dayanara Peralta G
 */
public class FrmModificarComanda extends JFrame{
    private final Coordinador coordinador;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    
    private JTextField txtFiltroMesa;

    public FrmModificarComanda(Coordinador coordinador) throws PersistenciaException {
        this.coordinador = coordinador;
        initUI();
        cargarTodos();
    }
    
    private void initUI() {
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
        card.setLayout(new BorderLayout(30, 15));
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = UI.tituloGrande("Comandas");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(lblTitulo, BorderLayout.NORTH);

        String[] columnas = {"Folio", "Estado", "Mesa", "Fecha y hora", "Productos", "Total"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };
        
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Georgia", Font.PLAIN, 14));
        tabla.setRowHeight(50);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setFillsViewportHeight(true);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        
        tabla.getColumnModel().getColumn(0).setPreferredWidth(150);  
        tabla.getColumnModel().getColumn(1).setPreferredWidth(100);  
        tabla.getColumnModel().getColumn(2).setPreferredWidth(200); 
        tabla.getColumnModel().getColumn(3).setPreferredWidth(300);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(400); 
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
        
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);
        
        JButton btnSeleccionar = UI.boton("Modificar", UI.AZUL_OSCURO, UI.AZUL_OSCURO_HOVER);
        btnSeleccionar.setPreferredSize(new Dimension(140, 40));
        btnSeleccionar.addActionListener(e -> {
            accionModificar();
        });
        footer.add(btnSeleccionar);
        
        JButton btnAtras = UI.boton("Atrás", new Color(0xC0392B), new Color(0x922B21));
        btnAtras.setPreferredSize(new Dimension(140, 40));
        btnAtras.addActionListener(e -> coordinador.rolMeseroSeleccionado());
        footer.add(btnAtras);
        
        card.add(footer, BorderLayout.SOUTH);
        fondo.add(card, BorderLayout.CENTER);

    }
    
    private void cargarTodos() throws PersistenciaException {
        actualizarTabla(coordinador.obtenerComandasAbiertas());
    }
    
    private void actualizarTabla(List<ComandaDTO> lista) {
        modeloTabla.setRowCount(0);
        if (lista == null) return;
        
        for (ComandaDTO c : lista) {
            StringBuilder prods = new StringBuilder();
            if(c.getComandaProductos() != null){
                c.getComandaProductos().forEach(
                        p -> prods.append("- ")
                                .append(p.getCantidad())
                                .append(" ")
                                .append(p.getProducto().getNombre())
                                .append("\n")
                );
            }
            
            modeloTabla.addRow(new Object[]{
                c.getFolio(),
                c.getEstado().toString(),
                c.getMesa(),
                c.getFechaHora().toString(),
                prods.toString(),
                "$" + c.getTotalComanda()
            });
        }
    }
    
    public void cargarTabla(List<ComandaDTO> lista) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0); // Borra lo viejo
        for (ComandaDTO c : lista) {
            StringBuilder prods = new StringBuilder();
            if (c.getComandaProductos() != null) {
                c.getComandaProductos().forEach(
                        p -> prods.append("- ")
                                .append(p.getCantidad())
                                .append(" ")
                                .append(p.getProducto().getNombre())
                                .append("\n")
                );
            }
            modelo.addRow(new Object[]{
                c.getFolio(),
                c.getEstado().toString(),
                c.getMesa(),
                c.getFechaHora().toString(),
                prods.toString(),
                "$" + c.getTotalComanda()
            });
        }
    }

    private void accionModificar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un comanda primero.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        String folio = (String) modeloTabla.getValueAt(fila, 0);
        coordinador.abrirEdicionComanda(folio);
        this.dispose();
    }
}
