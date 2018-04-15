/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andres Obando Alfaro
 */
public class GameTable {
    public void setTable(JTable t, Model.KenKen_Board KKB) {
        DefaultTableModel defaultTable;
        Object[] row;
        JTextPane text;
        String operation;
        int counter, number;
        
        t.setDefaultRenderer(Object.class, new Render());
        defaultTable = new DefaultTableModel() {
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (int i = 3; i < KKB.group.length - 3; i++)
            defaultTable.addColumn(i);
        row = new Object[KKB.group.length];
        for (int i = 3; i < KKB.group.length - 3; i++) {
            counter = 0;
            for (int j = 3; j < KKB.group.length - 3; j++) {
                number = KKB.board.get(i - 3).get(j - 3);
                text = new JTextPane();
                text.setContentType("text/html");
                operation = KKB.results[i - 3][j - 3] + KKB.getOperations().get(KKB.group[i][j]);
                text.setText("<html><small>" + operation + "</small>" + "<center><b>" + number + "</b></center>" + "</html>");
                borders(text, i, j, KKB.group);
                row[counter++] = text;
            }
            defaultTable.addRow(row);
        }
        t.setModel(defaultTable);
        t.setRowHeight(t.getWidth() / KKB.size);
    }
    
    private void borders(JTextPane text, int i, int j, int[][] group) {
        int up , down,left,right;
        
        up = down = left = right = 2;
        int number = group[i][j];
        if (group[i + 1][j] == number)
            down = 0;
        if (group[i - 1][j] == number)
            up = 0;
        if (group[i][j + 1] == number)
            right = 0;
        if (group[i][j - 1] == number)
            left = 0;
        text.setBorder(BorderFactory.createMatteBorder(up, left, down, right, Color.BLACK));
    }
}
