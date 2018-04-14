/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;


import java.awt.Button;
import java.awt.Color;
import java.awt.Label;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

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
                PanelRA panel  = new PanelRA(10,"+",5,true,5,5,5,5);
                fila[j] = panel;
            }
            tablaPredeterminada.addRow(fila);
        }
        pTabla.setModel(tablaPredeterminada);
        pTabla.setRowHeight(column.getWidth());
    }
}
