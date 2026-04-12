/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import DTOs.ComandaDTO;
import DTOs.EstadoComandaDTO;
import DTOs.ReporteClienteFrecuenteDTO;
import EstilosGUI.UI;
import Interfaces.ComandaObserver;
import coordinador.Coordinador;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Dayanara Peralta G
 */
public class FrmReporteComandas extends JFrame implements ComandaObserver{
    private final Coordinador coordinador;
    private JTextField txtFiltroMesa;
    private JTextField txtFiltrofechas1;
    private JTextField txtFiltrofechas2;
    private JTextField txtFiltroCliente;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> estado;


    public FrmReporteComandas(Coordinador coordinador) throws PersistenciaException {
        this.coordinador = coordinador;
        this.coordinador.comandaObserver(this);
        initUI();
        cargarTodos();
    }
    
    private void initUI(){
        setTitle("Buscar comanda");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.regresarDesdeReporteComandas();
            }
        });
        
        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);
        
        JPanel card = UI.card();
        card.setLayout(new BorderLayout(15, 15));
        card.setBorder(new EmptyBorder(25, 30, 30, 30));

        JLabel lblTitulo = UI.tituloGrande("Buscar comanda");
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
        
        JLabel lblMesa = UI.titulo("Buscar por numero de mesa: ");
        lblMesa.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(lblMesa);
        contenedor.add(Box.createVerticalStrut(15));

        JLabel lblNombre = new JLabel("Mesa:");
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(lblNombre);

        txtFiltroMesa = new JTextField();
        txtFiltroMesa.setMaximumSize(new Dimension(220, 30));
        txtFiltroMesa.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(txtFiltroMesa);
        contenedor.add(Box.createVerticalStrut(12));
        
        JLabel lblEstado = UI.titulo("Buscar por estado: ");
        lblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(lblEstado);
        contenedor.add(Box.createVerticalStrut(15));
        
        Dimension tam = new Dimension(220, 30);
        
        String[] estados = {"TODAS", "ABIERTA", "ENTREGADA", "CANCELADA"};
        estado = new JComboBox<>(estados);
        estado.setBackground(Color.WHITE);
        estado.setFont(new Font("Georgia", Font.PLAIN, 14));
        estado.setBorder(BorderFactory.createLineBorder(new Color(0xD4AF37)));
        
        estado.setPreferredSize(tam);
        estado.setMaximumSize(tam);
        estado.setMinimumSize(tam);
        estado.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(estado);
        
        JLabel es = UI.titulo(" ");
        es.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(es);
        contenedor.add(Box.createVerticalStrut(15));
        
        JLabel lblRangoFechas = UI.titulo("\nBuscar por rango de fechas");
        lblRangoFechas.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(lblRangoFechas);
        contenedor.add(Box.createVerticalStrut(15));
        
        JLabel lblFormatoFechas = UI.titulo("(AAAA-MM-DD): ");
        lblFormatoFechas.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(lblFormatoFechas);
        contenedor.add(Box.createVerticalStrut(15));
        
        JLabel lblInicio = new JLabel("Fecha inicio:");
        lblInicio.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(lblInicio);

        txtFiltrofechas1 = new JTextField();
        txtFiltrofechas1.setMaximumSize(new Dimension(220, 30));
        txtFiltrofechas1.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(txtFiltrofechas1);
        contenedor.add(Box.createVerticalStrut(25));
        
        JLabel lblFinal = new JLabel("Fecha final:");
        lblFinal.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(lblFinal);

        txtFiltrofechas2 = new JTextField();
        txtFiltrofechas2.setMaximumSize(new Dimension(220, 30));
        txtFiltrofechas2.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(txtFiltrofechas2);
        contenedor.add(Box.createVerticalStrut(25));
        
        JLabel lblClientes = UI.titulo("Clientes");
        lblClientes.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(lblClientes);
        contenedor.add(Box.createVerticalStrut(15));
        
        JLabel lblNomCliente = new JLabel("Cliente:");
        lblNomCliente.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(lblNomCliente);

        txtFiltroCliente = new JTextField();
        txtFiltroCliente.setMaximumSize(new Dimension(220, 30));
        txtFiltroCliente.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(txtFiltroCliente);
        contenedor.add(Box.createVerticalStrut(25));

        lateral.add(contenedor);
        lateral.add(Box.createVerticalGlue());

        card.add(lateral, BorderLayout.EAST);
        
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);
        
        JButton btnPDF = UI.botonPrimario("Generar PDF");
        btnPDF.setPreferredSize(new Dimension(140, 40));
        btnPDF.addActionListener(e -> {
            try {
                generarPDF();
            } catch (JRException ex) {
                System.getLogger(FrmReporteClientesFrecuentes.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        );
        
        JButton btnAtras = UI.boton("Atrás", new Color(0xC0392B), new Color(0x922B21));
        btnAtras.setPreferredSize(new Dimension(140, 40));
        btnAtras.addActionListener(e -> coordinador.regresarDesdeReporteComandas());
        footer.add(btnAtras);
        footer.add(btnPDF);
        card.add(footer, BorderLayout.SOUTH);
        fondo.add(card, BorderLayout.CENTER);

        txtFiltroMesa.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    accionBuscar();
                } catch (NegocioException ex) {
                    Logger.getLogger(FrmBuscadorComanda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        txtFiltrofechas1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    accionBuscar();
                } catch (NegocioException ex) {
                    Logger.getLogger(FrmBuscadorComanda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }); 
        
        txtFiltrofechas2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    accionBuscar();
                } catch (NegocioException ex) {
                    Logger.getLogger(FrmBuscadorComanda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        estado.addActionListener(e -> {
            try {
                accionBuscar();
            } catch (NegocioException ex) {
                Logger.getLogger(FrmBuscadorComanda.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        txtFiltroCliente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    accionBuscar();
                } catch (NegocioException ex) {
                    Logger.getLogger(FrmBuscadorComanda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void cargarTodos() throws PersistenciaException {
        actualizarTabla(coordinador.obtenerComandas());
    }
    
    private void accionBuscar() throws NegocioException {
        String mesa = txtFiltroMesa.getText().trim();
        String est = (String) estado.getSelectedItem();
        String inicio = txtFiltrofechas1.getText().trim();
        String fin = txtFiltrofechas2.getText().trim();
        String cli = txtFiltroCliente.getText().trim();
        
        Integer mesaFiltro = null;
        if (!mesa.isEmpty()) {
            try {
                mesaFiltro = Integer.valueOf(mesa);
            } catch (NumberFormatException e) {
                throw new NegocioException(e.getMessage());
            }
        }
        EstadoComandaDTO estadoFiltro = null;
        if(est != null && !"TODAS".equals(est)){
            estadoFiltro = EstadoComandaDTO.valueOf(est);
        }
        
        LocalDateTime iniFiltro = null;
        LocalDateTime finFiltro = null;
        
        try{
            if(!inicio.isEmpty() && inicio.length() == 10){
                iniFiltro = LocalDate.parse(inicio).atStartOfDay();
            }else if(!inicio.isEmpty()){
                return;
            }
            if(!fin.isEmpty() && fin.length() == 10){
                finFiltro = LocalDate.parse(fin).atTime(LocalTime.MAX);
            }else if(!fin.isEmpty()){
                return;
            }
        }catch(Exception e){
            throw new NegocioException(e.getMessage());
        }
        
        String cliente = cli.isEmpty() ? null : cli;
        
        List<ComandaDTO> fil = coordinador.buscarComandas(mesaFiltro, estadoFiltro, iniFiltro, finFiltro, cliente);
        actualizarTabla(fil);
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
    
    @Override
    public void comandaObserver() throws NegocioException{
        accionBuscar();
        System.out.println("Buscador actualizado");
    }
    
    /*
    private JTextField txtFiltroMesa;
    private JTextField txtFiltrofechas1;
    private JTextField txtFiltrofechas2;
    private JTextField txtFiltroCliente;
    */
    
    private void generarPDF() throws JRException {
        try {
            String mesa = txtFiltroMesa.getText().trim();
            String est = (String) estado.getSelectedItem();
            String inicio = txtFiltrofechas1.getText().trim();
            String fin = txtFiltrofechas2.getText().trim();
            String cli = txtFiltroCliente.getText().trim();

            Integer filMesa = mesa.isEmpty() ? null : Integer.valueOf(mesa);

            EstadoComandaDTO filEstado = (est != null && !"TODAS".equals(est))
                    ? EstadoComandaDTO.valueOf(est) : null;

            LocalDateTime iniFiltro = null;
            LocalDateTime finFiltro = null;

            try {
                if (!inicio.isEmpty() && inicio.length() == 10) {
                    iniFiltro = LocalDate.parse(inicio).atStartOfDay();
                } else if (!inicio.isEmpty()) {
                    return;
                }
                if (!fin.isEmpty() && fin.length() == 10) {
                    finFiltro = LocalDate.parse(fin).atTime(LocalTime.MAX);
                } else if (!fin.isEmpty()) {
                    return;
                }
            } catch (Exception e) {
                throw new NegocioException(e.getMessage());
            }
                    
            String filCliente = cli.isEmpty() ? null : cli;
            
            List<ComandaDTO> datos = coordinador.buscarComandas(filMesa, filEstado, iniFiltro, finFiltro, filCliente);
                    
            if(datos.isEmpty()){
                JOptionPane.showMessageDialog(this, "No hay datos");
            }
            JasperPrint jasperPrint = coordinador.generarReporteComandas(datos);

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar reporte PDF");
            fileChooser.setSelectedFile(new File("reporte_comandas.pdf"));

            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                JasperExportManager.exportReportToPdfFile(jasperPrint, archivo.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Reporte guardado en:\n" + archivo.getAbsolutePath());
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido en visitas.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}