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
    public static ArrayList<ArrayList<Integer>> board;
    public static ArrayList<ArrayList<Integer>> transverseBoard;
    // Group
    public static int[][] group;
    // Results
    public static int[][] results;
    public static HashMap<Integer, ArrayList<Integer>> map;
    public static HashMap<Integer, Integer> resultsMap;
    public static HashMap<Integer, String> operations;
    // Other Variables
    public static int actual;
    public static int size;
    public static ArrayList<Integer> groupsArray;
    private final Random random = new Random();
    public static byte Powers;
    public static byte Modules;
    public static boolean solutionFound;
    
    // Constructor
    public KenKen_Board(int size) {
        this.size = size;
        actual = 1;
        solutionFound = false;
        // Initialize Boards
        Boards();
        // Group the boards
        Group(size);
        // ArrayGroup
        FillGroupArray();
        // View the results
        Results();
        // Clean the boards
        //CleanBoards();
        //print();
    }
    public KenKen_Board(ArrayList<ArrayList<Integer>> board, ArrayList<ArrayList<Integer>> transverseBoard, 
            int[][] group, int[][] results, HashMap<Integer, ArrayList<Integer>> map, 
            HashMap<Integer, Integer> resultsMap, HashMap<Integer, String> operations, 
            int actual, int size, byte Powers, byte Modules, 
            boolean solutionFound, ArrayList<Integer> groupsArray) {
        KenKen_Board.board = board;
        KenKen_Board.transverseBoard = transverseBoard;
        KenKen_Board.group = group;
        KenKen_Board.results = results;
        KenKen_Board.map = map;
        KenKen_Board.resultsMap = resultsMap;
        KenKen_Board.operations = operations;
        KenKen_Board.actual = actual;
        KenKen_Board.size = size;
        KenKen_Board.Powers = Powers;
        KenKen_Board.Modules = Modules;
        KenKen_Board.solutionFound = solutionFound;
        KenKen_Board.groupsArray = groupsArray;
    }
    
    /* Initialize the boards in order to get clean arrays to work and use get
    different combinations of boards */
    private void Boards() {
        InitializeBoards();
        SetRandoms();
        FillBoards();
    }
    private static void InitializeBoards() {
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
    private void SetRandoms() {
        int randX, randY, rand;
        
        // Fill 25% of the board with random numbers.
        for (int i = 0; i < size * size / 4; i++) {
            randX = random.nextInt(size);
            randY = random.nextInt(size);
            do
                rand = random.nextInt(size);
            while (!isPossible(randX, randY, rand));
            set(randX, randY, rand);
        }
    }
    // For backtracking functions
    private void FillBoards() {
        int i, j;
        ArrayList<Integer> possibilities;
        
        if (Complete()) {
            print();
            solutionFound = true;
            return;
        }
        else {
            // Search for the last unused space
            i = j = 0;
            for (int a = 0; a < size; a++)
                for (int b = 0; b < size; b++)
                    if (get(a, b) == 100) {
                        i = a;
                        j = b;
                    }
            // Search for the possible values to be in the array
            possibilities = Possibilities(i, j);
            for (int n : possibilities) {
                if (!solutionFound) {
                    set(i, j, n);
                    FillBoards();
                }
                else
                    return;
            }
            // Control Backtracking
            if (!solutionFound)
                set(i, j, 100);
        }
    }
    private ArrayList<Integer> Possibilities(int i, int j) {
        ArrayList<Integer> possibilities;
        
        possibilities = new ArrayList<Integer>();
        for (int n = 0; n < size ; n++)
            if (isPossible(i, j, n))
                possibilities.add(n);
        return possibilities;
    }
    private boolean Complete() {
        for (ArrayList<Integer> ar : board)
            if (ar.contains(100))
                return false;
        return true;
    }
    // Restore boards
    public static void CleanBoards() {
        InitializeBoards();
    }
    
    /* Defines the groups in wich each operation will work*/
    private void Group(int n) {
        group = new int[n + 6][n + 6];
        do
            Group();
        while (!isGrouped());
        fixDimensions();
    }
    private void Group() {
        boolean[][] grp;
        boolean doSomething;
        
        for (int i = 3; i < group.length - 3; i++)
            for (int j = 3; j < group.length - 3; j++)
                // Only for those without value.
                if (group[i][j] == 0) {
                    grp = Cage.getCage();
                    doSomething = false;
                    // Travel the obtained cage with a tetris figure.
                    for (int y = 0; y < grp.length; y++)
                        if (group[i + y][j] == 0)
                            for (int x = 0; x < grp[0].length; x++)
                                if (grp[y][x]) {
                                    if (group[i + y][j + x] == 0) {
                                        group[i + y][j + x] = actual;
                                        doSomething = true;
                                    }
                                    else
                                        break;
                                }
                                else {}
                        else
                            break;
                    if (doSomething)
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
    private static void FillGroupArray() {
        groupsArray = new ArrayList<Integer>();
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!groupsArray.contains(group[i][j]))
                    groupsArray.add(group[i][j]);
            }
        }
    }
    
    /* Fill the results maps in order to get a O(n) each time we consult */
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
                operations.put(key, "^");
                return (int)Math.pow(set.get(0), 3);
            case 2:
                operations.put(key, "%");
                Modules++;
                try {
                    result = set.get(0) % set.get(1);
                    if (result < 0) {
                        result = set.get(0) * set.get(1);
                        operations.put(key, "*");
                    }
                    else
                        operations.put(key, "%");
                    return result;
                }
                // For 0 division.
                catch (ArithmeticException ae) {
                    operations.put(key, "%");
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
    public  int Results(int i, int j) {
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
    public static void set100(int groupNo) {
        for (int i = 0; i < group.length; i++) {
            for (int j = 0; j < group.length; j++) {
                if (group[i][j] == groupNo)
                    group[i][j] = 100;
            }
        }
    }
    public static boolean groupIsComplete(int groupNo) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (group[i][j] == groupNo && board.get(i).get(j) == 100)
                    return false;
            }
        }
        return true;
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
                if(operations.get(group[i][j]).equals("^") || operations.get(group[i][j]).equals("%") || operations.get(group[i][j]).equals("*") ) {
                    if(board.get(i).get(j) == 100)
                        return false;
                }
            }
        return true;
    }
    /**
     *
     * @return
     */
    public static boolean isCompletePowers() {
        for (int i = 0; i < group.length; i++)
            for (int j = 0; j < group.length; j++) {
                if(operations.get(group[i][j]).equals("^") ) {
                    if(board.get(i).get(j) == 100)
                        return false;
                }
            }
        return true;
    }
    public static boolean isPossible(int i, int j, int value) {
        boolean result = true;
        if(value>=size)
        {
            for (int x = 0; x<board.get(i).size();x++)
            {
                if(board.get(i).contains(value) && x!=j)
                    result = false;
            }
            for (int x = 0; x<transverseBoard.get(i).size();x++)
            {
                if(transverseBoard.get(j).contains(value) && x!=i)
                    result = false;
            }
        }
        else
        {
            result = false;
        }
        return result;
        
            
        
       // return !board.get(i).contains(value) && !transverseBoard.get(j).contains(value) && range().contains(value);
    }
    // Print
    public static void print() {
        printBoard(board);
        
        /*for (int i = 0; i < group.length; i++) {
            for (int j = 0; j < group.length; j++)
                System.out.print(group[i][j] + "\t");
            System.out.println();
        }
        /*
        System.out.println();
         Print Last Board
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
    
    public static int cantOfGroup(int groupID)
    {
        int cant = 0;
        for (int x = 0; x<size; x++)
        {
            for (int y = 0; y<size; y++)
            {
                if(group[x][y] == groupID)
                    cant++;
            }
        }
        return cant;
    }
    
    
    
    public static int maxGroup ()
    {
        int big = 1;
        for (int x = 0; x<size; x++)
        {
            for (int y = 0; y<size; y++)
            {
                if(group[x][y] >= big)
                    big = group[x][y];
            }
        }
        return big;
    }
    
    static ArrayList<ArrayList<Integer>> getPeople(int i) {
        ArrayList<ArrayList<Integer>> places = new ArrayList<>();  
        for (int a = 0; a < size; a++) {
            for (int b = 0; b < size; b++) {
                if (group[a][b]==i)
                {
                    ArrayList<Integer> place = new ArrayList<>();
                    place.add(a);
                    place.add(b);
                    places.add(place);
                }
            }
        }
        return places;
        
    }
}