package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Andres Obando
 */
public class KenKen_Board {
    private ArrayList<ArrayList<Integer>> board;
    private ArrayList<ArrayList<Integer>> transverseBoard;
    private int[][] group;
    private final Random random = new Random();
    private static int actual;
    
    // Constructor
    public KenKen_Board(int size) {
        initializeBoard(size);
        fillBoards();
        group = new int[size + 6][size + 6];
        actual = 1;
        do
            this.Group();
        while (!isGrouped());
    }
    private void initializeBoard(int size) {
        board = new ArrayList<>();
        transverseBoard = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            board.add(new ArrayList<>());
            transverseBoard.add(new ArrayList<>());
            for (int j = 0; j < size; j++) {
                board.get(i).add(size + 2);
                transverseBoard.get(i).add(size + 2);
            }
        }
    }
    private void fillBoards() {
        int size, rand;
        
        size = board.size();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                do
                    rand = (random.nextInt() % 2 == 0 ? 1 : -1) * random.nextInt(size + 1);
                while (board.get(i).contains(rand) || transverseBoard.get(j).contains(rand));
                board.get(i).set(j, rand);
                transverseBoard.get(j).set(i, rand);
            }
    }
    private void Group() {
        boolean[][] grp;
        
        for (int i = 3; i < group.length - 3; i++)
            for (int j = 3; j < group.length - 3; j++)
                // Only for those without value.
                if (group[i][j] == 0) {
                    grp = Cage.getCage();
                    // Travel the obtained cage with a tetris figure.
                    for (int y = 0; y < grp.length; y++)
                        for (int x = 0; x < grp[0].length; x++)
                            if (grp[y][x] && group[i + y][j + x] == 0)
                                group[i + y][j + x] = actual;
                    actual++;
                }
    }
    private boolean isGrouped() {
        for (int i = 3; i < group.length - 3; i++)
            for (int j = 3; j < group.length - 3; j++)
                if (group[i][j] == 0)
                    return false;
        
        return true;
    }
    
    // Print
    public void print() {
        printBoard(this.board);
        printBoard(this.transverseBoard);
        
        for (int i = 3; i < group.length - 3; i++) {
            for (int j = 3; j < group.length - 3; j++)
                System.out.print(group[i][j] + "\t");
            System.out.println();
        }
    }
    private static void printBoard(ArrayList<ArrayList<Integer>> b) {
        for (ArrayList<Integer> row : b) {
            for (int c : row)
                System.out.print(c + "\t");
            System.out.println();
        }
        System.out.println();
    }
}
