/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;


import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jose pablo
 */
public class TablaJuego {
    
    public void ver_tabla(JTable pTabla,Model.KenKen_Board KenKen ){
        
        pTabla.setDefaultRenderer(Object.class, new Render());
        DefaultTableModel tablaPredeterminada = new DefaultTableModel(){
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        for(int a=3; a<KenKen.group.length-3; a++){
            tablaPredeterminada.addColumn(a);
        }   
        Object fila[] = new Object[KenKen.group.length];
        for(int i=3;i<KenKen.group.length-3;i++)
        {
            int contador=0;
            for (int j=3;j<KenKen.group.length-3 ;j++)
            {          
                int number = KenKen.board.get(i-3).get(j-3);
                JTextPane text = new JTextPane();
                text.setContentType("text/html");
                String operation = 5+"+";
                text.setText("<html><small>"+operation+"</small>"+"<center><b>"+number+"</b></center>"+"</html>");
                borders(text,i,j,KenKen.group);
                fila[contador++] = text;
            }
            tablaPredeterminada.addRow(fila);
        }
        pTabla.setModel(tablaPredeterminada);
        pTabla.setRowHeight(pTabla.getWidth()/KenKen.size);
    }

    private void borders(JTextPane text, int i, int j, int[][] group)
    {
        int up , down,left,right;
        up = down = left = right = 2;
        int number = group[i][j];
        if(group[i+1][j]==number)
            down=0;
        if(group[i-1][j]==number)
            up=0;
        if(group[i][j+1]==number)
            right=0;
        if(group[i][j-1]==number)
            left=0;
        text.setBorder(BorderFactory.createMatteBorder(up, left, down, right, Color.BLACK));
    }
}
