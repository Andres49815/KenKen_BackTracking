package View;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ruben Gonzalez Villanueva
 */
public class GameTable {
    
    public void setTable(JTable t, Model.KenKen_Board KKB) {
        DefaultTableModel defaultTable;
        Object[] row;
        JTextPane text;
        String operation;
        int counter, number;
        int[][] group, results;
        
        t.setDefaultRenderer(Object.class, new Render());
        defaultTable = new DefaultTableModel() {
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        group = KKB.getGroup();
        results = KKB.getResults();
        for (int i = 0; i < group.length; i++)
            defaultTable.addColumn(i);
        row = new Object[group.length];
        for (int i = 0; i < group.length; i++) {
            counter = 0;
            for (int j = 0; j < group.length; j++) {
                number = KKB.getBoard().get(i).get(j);
                text = new JTextPane();
                text.setContentType("text/html");
                operation = results[i][j] + KKB.getOperations().get(group[i][j]);
                text.setText("<html><small>" + operation + "</small>" + "<center><b>" + number + "</b></center>" + "</html>");
                borders(text, i, j, group);
                putColor(text,KKB.getOperations().get(group[i][j]));
                row[counter++] = text;
            }
            defaultTable.addRow(row);
        }
        t.setModel(defaultTable);
        t.setRowHeight(t.getWidth() / KKB.getSize());
    }
    
    private void borders(JTextPane text, int i, int j, int[][] group) {
        int up , down, left, right;
        
        up = down = left = right = 2;
        int number = group[i][j];
        try {
            if (group[i + 1][j] == number)
                down = 0;
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try {
            if (group[i - 1][j] == number)
                up = 0;
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try {
            if (group[i][j + 1] == number)
                right = 0;
        }
        catch (ArrayIndexOutOfBoundsException e){}
        try {
            if (group[i][j - 1] == number)
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
