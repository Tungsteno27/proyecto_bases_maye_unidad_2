/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import DTOs.MesaDTO;
import EstilosGUI.UI;
import Interfaces.MesaObserver;
import coordinador.Coordinador;
import entidades.EstadoMesa;
import excepciones.NegocioException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Dayanara Peralta G
 */
public class FrmSeleccionarMesa extends JFrame implements MesaObserver{
    private final Coordinador coordinador;
    private MesaDTO mesa;
    private JPanel panelMesas;

    public FrmSeleccionarMesa(Coordinador coordinador) throws NegocioException {
        this.coordinador = coordinador;
        this.coordinador.agregarObserver(this);
        initUI();
        cargarMesas();
    }
    
    private void initUI(){
        setTitle("Seleccionar mesa");
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
        
        JLabel lblTitulo = UI.tituloGrande("Mesas");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(lblTitulo, BorderLayout.NORTH);
        
        panelMesas = new JPanel(new GridLayout(2, 5, 25, 25));
        panelMesas.setOpaque(false);
        fondo.add(panelMesas, BorderLayout.CENTER);
        
        JButton btnConfirmar = UI.botonPrimario("Confirmar mesa");
        btnConfirmar.addActionListener(e -> {
            if (mesa != null) {
                coordinador.abrirSeleccionarCliente(mesa);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una mesa.");
            }
        });
        JButton btnVolver = UI.boton("Volver", UI.AZUL_OSCURO, UI.AZUL_OSCURO_HOVER);
        btnVolver.addActionListener(e -> coordinador.rolMeseroSeleccionado());
        
        JPanel pnlSur = new JPanel();
        pnlSur.setOpaque(false);
        pnlSur.add(btnVolver);
        pnlSur.add(btnConfirmar);
        fondo.add(pnlSur, BorderLayout.SOUTH);
    }
    
    @Override
    public void actualizarMesas() throws NegocioException{
        try {
           panelMesas.removeAll();
           this.cargarMesas();
           panelMesas.revalidate();
           panelMesas.repaint();
           System.out.println("Mesa actualizada");

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar las mesas: "+ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void cargarMesas() throws NegocioException {
        try{
        panelMesas.removeAll();
        List<MesaDTO> mesas = coordinador.obtenerMesas();
        
        if(mesas == null || mesas.isEmpty()){
            System.out.println("No mesas");
            return;
        }

        for (MesaDTO mesota : mesas) {
            JButton btnMesa = botonMesa(mesota);
            panelMesas.add(btnMesa);
        }
        panelMesas.revalidate();
        panelMesas.repaint();
        }catch(NegocioException e){
            JOptionPane.showMessageDialog(this, "Error al cargar las mesas: " + e.getMessage());
        }
    }
    
    private JButton botonMesa (MesaDTO mesita){
        JButton boton = new JButton(String.valueOf(mesita.getNuemro())){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int diametro = 100;
                int x = (getWidth() - diametro)/2;
                int y = (getHeight() - diametro)/2;
                
                String estadoActual = (mesita.getEstado() != null) ? mesita.getEstado().toString().trim().toUpperCase() : "";
                System.out.println("Dibujando mesa " + mesita.getNuemro() + " con estado: [" + estadoActual + "]");
                // Color según estado (AZUL si disponible, ROJO si ocupada)
                if ("OCUPADA".equals(estadoActual)) {
                    g2.setColor(new Color(0xE74C3C)); // Rojo
                } else if (mesita.equals(mesa)) {
                    g2.setColor(UI.AZUL_NORMAL.darker()); // Resaltar selección
                } else {
                    g2.setColor(new Color(0x7FB3D5)); // Azul claro
                }
                
                g2.fillOval(x, y, diametro, diametro);
                g2.setColor(Color.BLACK);
                g2.drawOval(x, y, diametro, diametro);
                
                g2.setColor(Color.BLACK);
                FontMetrics fm = g2.getFontMetrics();
                String texto = String.valueOf(mesita.getNuemro());
                int tx = (getWidth() - fm.stringWidth(texto)) / 2;
                int ty = (getHeight() + fm.getAscent()) / 2 - 2;
                g2.drawString(texto, tx, ty);
                
                //super.paintComponent(g);
                g2.dispose();
            }
        };
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 18));
        
        boton.addActionListener(e ->{
            if("OCUPADA".equals(mesita.getEstado())){
                JOptionPane.showMessageDialog(this, "Mesa ocupada");
            }else{
                this.mesa = mesita;
                repaint();
            }
        });
        return boton;
    }
    
}
