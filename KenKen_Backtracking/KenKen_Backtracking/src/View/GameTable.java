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
        int number;

        t.setDefaultRenderer(Object.class, new Render());
        defaultTable = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (int i = 0; i < KenKen_Board.size; i++) {
            defaultTable.addColumn(i);
        }
        row = new Object[KenKen_Board.size];
        for (int i = 0; i < KenKen_Board.size; i++) {
            for (int j = 0; j < KenKen_Board.size; j++) {
                Cage cage = KenKen_Board.cages[i][j];
                number = KenKen_Board.board.get(i).get(j);

                text = new JTextPane();
                text.setContentType("text/html");
                operation = cage.result + cage.operation;
                if (number == 100) {
                    text.setText("<html><small>" + operation + "</small>" + "</html>");
                } else {
                    text.setText("<html><small>" + operation + "</small>" + "<center><b>" + number + "</b></center>" + "</html>");
                }

                borders(text, i, j, KenKen_Board.cages);
                putColor(text, cage.operation);
                row[j] = text;
            }
            defaultTable.addRow(row);
        }
        t.setModel(defaultTable);
        t.setRowHeight(t.getWidth() / KenKen_Board.size);
    }

    public void actualizar(JTable t) {

        JTextPane text;
        String operation;
        int number;

        DefaultTableModel defaultTable = (DefaultTableModel) t.getModel();

        for (int i = 0; i < KenKen_Board.size; i++) {
            for (int j = 0; j < KenKen_Board.size; j++) {
                Cage cage = KenKen_Board.cages[i][j];
                text = (JTextPane) defaultTable.getValueAt(i, j);
                number = KenKen_Board.board.get(i).get(j);
                text.setContentType("text/html");
                operation = cage.result + cage.operation;
                if (number == 100) {
                    text.setText("<html><small>" + operation + "</small>" + "</html>");
                } else {
                    text.setText("<html><small>" + operation + "</small>" + "<center><b>" + number + "</b></center>" + "</html>");
                }
                borders(text, i, j, KenKen_Board.cages);
                putColor(text, cage.operation);
                defaultTable.setValueAt(text, i, j);
            }
        }
        t.setModel(defaultTable);
    }

    private void borders(JTextPane text, int i, int j, Cage[][] group) {
        int up, down, left, right;

        up = down = left = right = 2;
        int number = group[i][j].id;
        try {
            if (group[i + 1][j].id == number) {
                down = 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if (group[i - 1][j].id == number) {
                up = 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if (group[i][j + 1].id == number) {
                right = 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            if (group[i][j - 1].id == number) {
                left = 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        text.setBorder(BorderFactory.createMatteBorder(up, left, down, right, Color.BLACK));
    }

    private void putColor(JTextPane text, String get) {
        switch (get) {
            case "+":
                text.setBackground(new Color(241, 148, 138));
                break;
            case "-":
                text.setBackground(new Color(229, 152, 102));
                break;
            case "*":
                text.setBackground(new Color(249, 231, 159));
                break;
            case "/":
                text.setBackground(new Color(171, 235, 198));
                break;
            case "%":
                text.setBackground(new Color(133, 193, 233));
                break;
            case "^":
                text.setBackground(new Color(210, 180, 222));
                break;
            default:
                text.setBackground(new Color(213, 216, 220));
                break;
        }
    }
}
