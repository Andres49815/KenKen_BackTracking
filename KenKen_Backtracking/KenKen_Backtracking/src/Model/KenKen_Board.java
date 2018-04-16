package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Andres Obando
 */
public class KenKen_Board {
    // Boards
    private static ArrayList<ArrayList<Integer>> board;
    private static ArrayList<ArrayList<Integer>> transverseBoard;
    // Group
    private static int[][] group;
    // Results
    private static int[][] results;
    private static HashMap<Integer, ArrayList<Integer>> map;
    private static HashMap<Integer, Integer> resultsMap;
    private static HashMap<Integer, String> operations;
    // Other Variables
    private static int actual;
    private static int size;
    private final Random random = new Random();
    public static byte Powers;
    public static byte Modules;
    
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
        // Clean the boards
        //CleanBoards();
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
                board.get(i).add(100);
                transverseBoard.get(i).add(100);
            }
        }
    }
    private void fillBoards() {
        int rand;
        
        size = board.size();
        // Create a possible solution for the KenKen
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                // Secure the rand number isn't in the row and column.
                do
                    rand = (random.nextInt() % 2 == 0 ? 1 : -1) * random.nextInt(size);
                while (board.get(i).contains(rand) || transverseBoard.get(j).contains(rand));
                board.get(i).set(j, rand);
                transverseBoard.get(j).set(i, rand);
            }
    }
    private void CleanBoards() {
        initializeBoards(size);
    }
    private int getRandomNumber() {
        if (size < 10) {
            return random.nextInt(10);
        }
        else {
            int n = size - 9;
            return random.nextInt(size + n) - n;
        }
    }
    // On group matrix
    private void Group(int n) {
        group = new int[n + 6][n + 6];
        do
            Group();
        while (!isGrouped());
        fixDimensions();
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
                        else
                            break;
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
    private void fixDimensions() {
        int[][] newGroup = new int[size][size];
        
        for (int i = 3; i < group.length - 3; i++)
            for (int j = 3; j < group.length - 3; j++)
                newGroup[i - 3][j - 3] = group[i][j];
        this.group = new int[size][size];
        this.group = newGroup;
    }
    // On Operations
    private void Results() {
        fillMap();
        fillResults();
    }
    private void fillMap() {
        int key, value;
        
        map = new HashMap<Integer, ArrayList<Integer>>();
        // Fill the map in order to relate the group with the result.
        for (int i = 0; i < group.length; i++)
            for (int j = 0; j < group.length; j++) {
                key = group[i][j];
                value = board.get(i).get(j);
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
        
        Powers = Modules = 0;
        for (int key : map.keySet()) {
            ar = map.get(key);
            resultsMap.put(key, calculate(key, ar));
        }
    }
    private int calculate(int key, ArrayList<Integer> set) {
        int side, result;
        
        switch (set.size()) {
            case 1:
                if (set.get(0) >= 0) {
                    operations.put(key, "^");
                    Powers++;
                    return (int)Math.pow(2, set.get(0));
                }
                else {
                    operations.put(key, " ");
                    return set.get(0);
                }

            case 2:
                operations.put(key, "%");
                Modules++;
                try {
                    return set.get(0) % set.get(1);
                }
                // For 0 division.
                catch (ArithmeticException ae) {
                    return set.get(1) % set.get(0);
                }
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
        for (int i = 0; i < group.length; i++)
            for (int j = 0; j < group.length; j++)
                results[i][j] = resultsMap.get(group[i][j]);
    }
    public int Results(int i, int j) {
        return resultsMap.get(group[i][j]);
    }
    
    // Getters and Setters
    // On Boards
    public static ArrayList<ArrayList<Integer>> getBoard() {
        return board;
    }
    public static int get(int i, int j) {
        return board.get(i).get(j);
    }
    public static void set(int i, int j, int val) {
        board.get(i).set(j, val);
        transverseBoard.get(j).set(i, val);
    }
    // On Groups
    public static int[][] getGroup() {
        return group;
    }
    public static ArrayList<Integer> getGroup(int n) {
        return map.get(n);
    }
    public static ArrayList<Integer> getGroup(int i, int j) {
        return map.get(group[i][j]);
    }
    // On Results
    public static int[][] getResults() {
        return results;
    }
    public static int getResult(int i, int j) {
        return resultsMap.get(group[i][j]);

    }
    // On Operations
    public static String getOperation(int i, int j) {
        return operations.get(group[i][j]);
    }
    // Others
    public static int getSize() {
        return size;
    }
    public static HashMap<Integer, ArrayList<Integer>> getMap() {
        return map;
    }
    public static HashMap<Integer, String> getOperations() {
        return operations;
    }
    
    // Other Methods
    public int group(int i, int j) {
        return group[i][j];
    }
    public static boolean isComplete() {
        for (int i = 0; i < group.length; i++)
            for (int j = 0; j < group.length; j++) {
                if(operations.get(group[i][j]).equals("^")) {
                    if(board.get(i).get(j) == 100)
                        return false;
                }
            }
        return true;
        /*
        for (ArrayList<Integer> row : board)
            if (row.contains(100))
                return false;
        return true;
        */
    }
    public static boolean isPossible(int i, int j, int value) {
        return !board.get(i).contains(value) && !transverseBoard.get(j).contains(value);
    }
    // Print
    public static void print() {
        printBoard(board);
        /*
        // Print the group board
        for (int i = 0; i < group.length; i++) {
            for (int j = 0; j < group.length; j++)
                System.out.print(group[i][j] + "\t");
            System.out.println();
        }
        System.out.println();
        // Print Last Board
        printBoard(this.results);
        */
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