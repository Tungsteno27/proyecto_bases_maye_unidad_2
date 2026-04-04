/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import EstilosGUI.UI;
import coordinador.Coordinador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Dayanara Peralta G
 */
public class FrmSeleccionadorMesa extends JFrame{
    private final Coordinador coordinador;
    private ButtonGroup tables;
    private int mesaSeleccionada = -1;

    public FrmSeleccionadorMesa(Coordinador coordinador){
        this.coordinador = coordinador;
        iniciar();
    }
    
    private void iniciar(){
        setTitle("Selección de mesas");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                coordinador.cerrarSesion();//ponerle otra cosa
            }
        });
        
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(UI.FONDO);
        setContentPane(fondo);

        JPanel card = UI.card(440, 340);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(36, 44, 40, 44));
        
        // Titulo
        JLabel lblTitulo = new JLabel("Mesas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 19));
        lblTitulo.setForeground(UI.TEXTO_OSCURO);
        card.add(lblTitulo, BorderLayout.NORTH);
        
    }
    
}
