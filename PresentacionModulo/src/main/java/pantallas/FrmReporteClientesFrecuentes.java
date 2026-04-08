/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import DTOs.ReporteClienteFrecuenteDTO;
import EstilosGUI.UI;
import coordinador.Coordinador;
import excepciones.NegocioException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Tungs
 */
public class FrmReporteClientesFrecuentes extends JFrame {
    private final Coordinador coordinador;

    private JTextField txtNombre;
    private JTextField txtMinVisitas;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public FrmReporteClientesFrecuentes(Coordinador coordinador) {
        this.coordinador = coordinador;
        initUI();
        cargarTodos();
    }

    private void initUI()  {
        setTitle("Reporte de Clientes Frecuentes");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.regresarDesdeReporteClientes();
            }
        });

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card();
        card.setLayout(new BorderLayout(30, 15));
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = UI.tituloGrande("Reporte de Clientes Frecuentes");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(titulo, BorderLayout.NORTH);

        String[] columnas = {"Nombre", "Visitas", "Total Gastado", "Última Comanda"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Georgia", Font.PLAIN, 14));
        tabla.setRowHeight(30);
        tabla.setFillsViewportHeight(true);

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Georgia", Font.BOLD, 14));
        header.setBackground(UI.AZUL_NORMAL);
        header.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(UI.BORDE_ORO, 1));
        card.add(scroll, BorderLayout.CENTER);

        JPanel lateral = new JPanel();
        lateral.setOpaque(false);
        lateral.setLayout(new BoxLayout(lateral, BoxLayout.Y_AXIS));
        lateral.setBorder(new EmptyBorder(10, 10, 10, 10));
        lateral.setPreferredSize(new Dimension(260, 0));

        JLabel lblFiltros = UI.titulo("Filtros");
        lateral.add(lblFiltros);
        lateral.add(Box.createVerticalStrut(10));

        lateral.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        txtNombre.setMaximumSize(new Dimension(220, 30));
        lateral.add(txtNombre);
        lateral.add(Box.createVerticalStrut(10));

        lateral.add(new JLabel("Mín. Visitas:"));
        txtMinVisitas = new JTextField();
        txtMinVisitas.setMaximumSize(new Dimension(220, 30));
        lateral.add(txtMinVisitas);
        lateral.add(Box.createVerticalStrut(20));

        JButton btnPDF = UI.botonPrimario("Generar PDF");
        btnPDF.setMaximumSize(new Dimension(220, 40));
        btnPDF.addActionListener(e ->
            {
            try {
                generarPDF();
            } catch (JRException ex) {
                System.getLogger(FrmReporteClientesFrecuentes.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        );

        lateral.add(btnPDF);

        card.add(lateral, BorderLayout.EAST);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);

        JButton btnAtras = UI.boton("Atrás",
                new Color(0xC0392B), new Color(0x922B21));

        btnAtras.setPreferredSize(new Dimension(140, 40));
        btnAtras.addActionListener(e -> coordinador.regresarDesdeReporteClientes());

        footer.add(btnAtras);
        card.add(footer, BorderLayout.SOUTH);

        fondo.add(card, BorderLayout.CENTER);

        KeyAdapter filtroListener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscar();
            }
        };

        txtNombre.addKeyListener(filtroListener);
        txtMinVisitas.addKeyListener(filtroListener);
    }

    private void cargarTodos() {
        actualizarTabla(coordinador.obtenerReporteClientes(null, null));
    }

    private void buscar() {
        String nombre = txtNombre.getText().trim();

        Integer minVisitas = null;
        try {
            if (!txtMinVisitas.getText().trim().isEmpty()) {
                minVisitas = Integer.parseInt(txtMinVisitas.getText().trim());
            }
        } catch (NumberFormatException e) {
            // ignorar errores de escritura
        }

        actualizarTabla(coordinador.obtenerReporteClientes(
                nombre.isEmpty() ? null : nombre,
                minVisitas
        ));
    }

    private void actualizarTabla(List<ReporteClienteFrecuenteDTO> lista) {
        modeloTabla.setRowCount(0);

        if (lista == null) return;

        for (ReporteClienteFrecuenteDTO dto : lista) {
            modeloTabla.addRow(new Object[]{
                dto.getNombres(),
                dto.getVisitas(),
                "$" + dto.getTotalGastado(),
                dto.getUltimaComanda()
            });
        }
    }
    
    private void generarPDF() throws JRException {
        try {
            String nombre = txtNombre.getText().trim();
            String visitasTxt = txtMinVisitas.getText().trim();

            Integer visitas = visitasTxt.isEmpty() ? null : Integer.parseInt(visitasTxt);
            String nombreFiltro = nombre.isEmpty() ? null : nombre;

            List<ReporteClienteFrecuenteDTO> datos = coordinador.obtenerReporteClientes(nombreFiltro, visitas);
            JasperPrint jasperPrint = coordinador.generarReporteClientesFrecuentes(datos);

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar reporte PDF");
            fileChooser.setSelectedFile(new File("reporte_clientes.pdf"));

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
