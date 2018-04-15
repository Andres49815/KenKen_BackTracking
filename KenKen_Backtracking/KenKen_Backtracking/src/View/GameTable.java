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
}
