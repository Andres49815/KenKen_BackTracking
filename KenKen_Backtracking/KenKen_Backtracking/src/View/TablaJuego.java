/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;


import java.awt.Button;
import java.awt.Color;
import java.awt.Label;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.SimpleAttributeSet;

/**
 *
 * @author jose pablo
 */
public class TablaJuego {
    
    public void ver_tabla(JTable pTabla,int tamaño ){
        
        pTabla.setDefaultRenderer(Object.class, new Render());
        DefaultTableModel tablaPredeterminada = new DefaultTableModel(){
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        for(int a=0; a<tamaño; a++){
            tablaPredeterminada.addColumn(a);
        }   
        Object fila[] = new Object[tamaño];
        for(int i=0;i<tamaño;i++)
        {
            for (int j=0;j<tamaño;j++)
            {
                JTextPane text = new JTextPane();
                text.setContentType("text/html"); 
                text.setText(5+"+"+"\n"+"<html><center><b>9</h1></b></html>");
                text.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
                fila[j] = text;
            }
            tablaPredeterminada.addRow(fila);
        }
        pTabla.setModel(tablaPredeterminada);
        pTabla.setRowHeight(pTabla.getWidth()/tamaño);
    }
}
