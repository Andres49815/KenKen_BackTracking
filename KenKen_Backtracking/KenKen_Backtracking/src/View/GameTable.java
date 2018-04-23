package View;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import Model.*;

/**
 *
 * @author Ruben Gonzalez Villanueva
 */
public class GameTable {
    
    public void setTable(JTable t) {
        DefaultTableModel defaultTable;
        Object[] row;
        JTextPane text;
        String operation;
        int counter, number;
        int[][] group;
        
        
        t.setDefaultRenderer(Object.class, new Render());
        defaultTable = new DefaultTableModel() {
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        group = KenKen_Board.getGroup();
        for (int i = 0; i < group.length; i++)
            defaultTable.addColumn(i);
        row = new Object[group.length];
        for (int i = 0; i < group.length; i++) {
            for (int j = 0; j < group.length; j++) {
                Cage cage = KenKen_Board.cages[i][j];
                number = KenKen_Board.getBoard().get(i).get(j);
                text = new JTextPane();
                text.setContentType("text/html");
                operation = cage.result + cage.operation;
                text.setText("<html><small>" + operation + "</small>" + "<center><b>" + number + "</b></center>" + "</html>");
                borders(text, i, j, KenKen_Board.cages);
                putColor(text,cage.operation);
                row[j] = text;
            }
            defaultTable.addRow(row);
        }
        t.setModel(defaultTable);
        t.setRowHeight(t.getWidth() / KenKen_Board.getSize());
    }
    public void actualizar(JTable t) {
        
        JTextPane text;
        String operation;
        int  number;
        int[][] group, results;
        
        DefaultTableModel defaultTable = (DefaultTableModel) t.getModel();
        
        group = KenKen_Board.getGroup();
        for (int i = 0; i < group.length; i++) {
            for (int j = 0; j < group.length; j++) {
                Cage cage = KenKen_Board.cages[i][j];
                text =  (JTextPane) defaultTable.getValueAt(i, j);
                number = KenKen_Board.getBoard().get(i).get(j);
                text.setContentType("text/html");
                operation = cage.result + cage.operation;
                text.setText("<html><small>" + operation + "</small>" + "<center><b>" + number + "</b></center>" + "</html>");
                borders(text, i, j, KenKen_Board.cages);
                putColor(text,cage.operation);
                defaultTable.setValueAt(text, i, j);
            }
        }
        t.setModel(defaultTable);
    }
    
    private void borders(JTextPane text, int i, int j, Cage[][] group) {
        int up , down, left, right;
        
        up = down = left = right = 2;
        int number = group[i][j].id;
        try {
            if (group[i + 1][j].id == number)
                down = 0;
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try {
            if (group[i - 1][j].id == number)
                up = 0;
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try {
            if (group[i][j + 1].id == number)
                right = 0;
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try {
            if (group[i][j - 1].id == number)
                left = 0;
        }
        catch (ArrayIndexOutOfBoundsException e){}
        text.setBorder(BorderFactory.createMatteBorder(up, left, down, right, Color.BLACK));
    }

    private void putColor(JTextPane text, String get) {
        switch(get) {
            case "+":
                text.setOpaque(true);
                text.setBackground(new Color(241, 148, 138));
                break;
            case "-":
                text.setOpaque(true);
                text.setBackground(new Color(229, 152, 102));
                break;
            case "*":
                text.setOpaque(true);
                text.setBackground(new Color(249, 231, 159));  
                break;
            case "/":
                text.setOpaque(true);
                text.setBackground(new Color(171, 235, 198));  
                break;
            case "%":
                text.setOpaque(true);
                text.setBackground(new Color(133, 193, 233)); 
                break;
            case "^":
                text.setOpaque(true);
                text.setBackground(new Color(210, 180, 222)); 
                break;
            default:
                text.setOpaque(true);
                text.setBackground(new Color(213, 216, 220)); 
                break;
        }
    }
}
