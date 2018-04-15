package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Andres Obando
 */
public class KenKen_Board {
    public ArrayList<ArrayList<Integer>> board;
    private ArrayList<ArrayList<Integer>> transverseBoard;
    public int[][] group;
    public int[][] results;
    private final Random random = new Random();
    private static int actual;
    public int size;
    private static HashMap<Integer, ArrayList<Integer>> map;
    private static HashMap<Integer, Integer> resultsMap;
    private static HashMap<Integer, String> operations;
    
    // Constructor
    public KenKen_Board(int size) {
        this.size = size;
        actual = 1;
        // Initialize Boards
        Boards(size);
        // Group the boards
        Group(size);
        // View the results
        Results();
    }
    // On Created Solution and Transverse board
    private void Boards(int size) {
        initializeBoards(size);
        fillBoards();
    }
    private void initializeBoards(int size) {
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
        int rand;
        
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
    // On group matrix
    private void Group(int n) {
        group = new int[n + 6][n + 6];
        do
            Group();
        while(!isGrouped());
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
                        if (group[i + y][j] == 0)
                            for (int x = 0; x < grp[0].length; x++)
                                if (grp[y][x]) {
                                    if (group[i + y][j + x] == 0)
                                        group[i + y][j + x] = actual;
                                    else
                                        break;
                                }
                                else {}
                        else break;
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
    // On Operations
    private void Results() {
        fillMap();
        fillResults();
    }
    private void fillMap() {
        int key, value;
        
        map = new HashMap<Integer, ArrayList<Integer>>();
        for (int i = 3; i < group.length - 3; i++)
            for (int j = 3; j < group.length - 3; j++) {
                key = group[i][j];
                value = board.get(i - 3).get(j - 3);
                if (map.containsKey(key))
                    map.get(key).add(value);
                else {
                    map.put(key, new ArrayList<Integer>());
                    map.get(key).add(value);
                }
            }
    }
    private void fillResults() {
        resultsMap = new HashMap<Integer, Integer>();
        operations = new HashMap<Integer, String>();
        results = new int[size][size];
        calculateResults();
        fillResultsMatrix();
        
    }
    private void calculateResults() {
        ArrayList<Integer> ar;
        
        for (int key : map.keySet()) {
            ar = map.get(key);
            resultsMap.put(key, calculate(key, ar));
        }
    }
    private int calculate(int key, ArrayList<Integer> set) {
        int side, result;
        
        switch (set.size()) {
            case 1:
                operations.put(key, "^");
                return (int)Math.pow(set.get(0), 2);
            case 2:
                operations.put(key, "%");
                return set.get(0) % set.get(1);
            case 3:
            case 4:
                result = set.get(0);
                side = random.nextInt() % 2 == 0 ? 1 : -1;
                operations.put(key, side == 1 ? "+" : "-");
                for (int i = 1; i < set.size(); i++)
                    result += side * set.get(i);
                return result;
        }
        return 0;
    }
    private void fillResultsMatrix() {
        for (int i = 3; i < group.length - 3; i++)
            for (int j = 3; j < group.length - 3; j++)
                results[i - 3][j - 3] = resultsMap.get(group[i][j]);
    }
    
    // Gets and Sets
    public HashMap<Integer, String> getOperations() {
        return operations;
    }
    // Print
    public void print() {
        printBoard(this.board);
        
        // Print the group board
        for (int i = 3; i < group.length - 3; i++) {
            for (int j = 3; j < group.length - 3; j++)
                System.out.print(group[i][j] + "\t");
            System.out.println();
        }
        System.out.println();
        System.out.println();
        // Print Last Board
        printBoard(this.results);
    }
    private static void printBoard(ArrayList<ArrayList<Integer>> b) {
        for (ArrayList<Integer> row : b) {
            for (int c : row)
                System.out.print(c + "\t");
            System.out.println();
        }
        System.out.println();
    }
    private static void printBoard(int[][] b) {
        for (int[] row : b) {
            for (int c : row)
                System.out.print(c + "\t");
            System.out.println();
        }
        System.out.println();
    }
}
