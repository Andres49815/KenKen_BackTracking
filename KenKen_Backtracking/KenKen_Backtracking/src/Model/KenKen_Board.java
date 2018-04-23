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
    public static Cage[][] cages;
    // Results
    //public static int[][] results;
    public static HashMap<Integer, ArrayList<Integer>> map;
    //public static HashMap<Integer, Integer> resultsMap;
    // Other Variables
    public static int size;
    public static ArrayList<Integer> groupsArray;
    private final Random random = new Random();
    public static byte Powers;
    public static byte Modules;
    public static boolean solutionFound;
    
    // Constructor
    public KenKen_Board(int size) {
        KenKen_Board.size = size;
        solutionFound = false;
        // Initialize Boards
        Boards();
        // Group the boards
        //Group(size);
        Cages(size);
        // ArrayGroup
        FillGroupArray();
        // View the results
        Results();
        // Clean the boards
        //CleanBoards();
        //print();
    }
    public KenKen_Board(ArrayList<ArrayList<Integer>> board, ArrayList<ArrayList<Integer>> transverseBoard, 
            int[][] group,Cage[][] cages,  HashMap<Integer, ArrayList<Integer>> map, 
            int size, ArrayList<Integer> groupsArray, byte Powers, byte Modules, 
            boolean solutionFound ) {
        KenKen_Board.board = board;
        KenKen_Board.transverseBoard = transverseBoard;
        KenKen_Board.group = group;
        KenKen_Board.cages = cages;
        KenKen_Board.map = map;
        KenKen_Board.size = size;
        KenKen_Board.groupsArray = groupsArray;
        KenKen_Board.Powers = Powers;
        KenKen_Board.Modules = Modules;
        KenKen_Board.solutionFound = solutionFound;
        
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
            Place place = new Place(randX,randY);
            do
                rand = random.nextInt(size);
            while (!isPossible(new Place(randX, randY), rand));
            set(place, rand);
        }
    }
    // For backtracking functions
    private void FillBoards() {
        int i, j;
        ArrayList<Integer> possibilities;
        
        if (Complete()) {
            print();
            solutionFound = true;
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
            Place place = new Place(i, j);
            possibilities = Possibilities(place);
            for (int n : possibilities) {
                if (!solutionFound) {
                    set(place, n);
                    FillBoards();
                }
                else
                    return;
            }
            // Control Backtracking
            if (!solutionFound)
                set(place, 100);
        }
    }
    private ArrayList<Integer> Possibilities(Place place) {
        ArrayList<Integer> possibilities;
        
        possibilities = new ArrayList<>();
        for (int n = 0; n < size ; n++)
            if (isPossible(place, n))
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
    private boolean isGrouped() {
        for (int i = 3; i < group.length - 3; i++)
            for (int j = 3; j < group.length - 3; j++)
                if (group[i][j] == 0)
                    return false;
        
        return true;
    }
    private static void FillGroupArray() {
        groupsArray = new ArrayList<>();
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!groupsArray.contains(group[i][j]))
                    groupsArray.add(group[i][j]);
            }
        }
    }
    // On Cages
    private void Cages(int n) {
        new Cage();
        cages = new Cage[n + 6][n + 6];
        group = new int[n + 6][n + 6];
        
        do {
            Cages();
        } while (!isGrouped());
        FixDims();
    }
    private static void Cages() {
        for (int i = 3; i < cages.length - 3; i++) {
            for (int j = 3; j < cages.length - 3; j++) {
                if (group[i][j] == 0) {
                    new Cage(i, j);
                }
            }
        }
    }
    private static void FixDims() {
        int[][] newGroup = new int[size][size];
        Cage[][] groupCages = new Cage[size][size];
        
        for (int i = 3; i < group.length - 3; i++) {
            for (int j = 3; j < group.length - 3; j++) {
                groupCages[i - 3][j - 3] = cages[i][j];
                newGroup[i - 3][j - 3] = group[i][j];
            }
        }
        cages = new Cage[size][size];
        cages = groupCages;
        group = new int[size][size];
        group = newGroup;
    }
    
    /* Fill the results maps in order to get a O(n) each time we consult */
    private void Results() {
        FillResultsM();
        //fillMap();
        //fillResults();
    }
    private void fillMap() {
        int key, value;
        
        map = new HashMap<>();
        // Fill the map in order to relate the group with the result.
        for (int i = 0; i < group.length; i++)
            for (int j = 0; j < group.length; j++) {
                key = group[i][j];
                value = board.get(i).get(j);
                if (map.containsKey(key))
                    map.get(key).add(value);
                else {
                    map.put(key, new ArrayList<>());
                    map.get(key).add(value);
                }
            }
    }
    private void fillResults() {
        calculateResults();
        
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
    
   
    private void FillResultsM() {
        for (Cage[] row : cages) {
            for (Cage c : row) {
                c.setOperation();
            }
        }
    }
    
    // Getters and Setters
    // On Boards
    public static ArrayList<ArrayList<Integer>> getBoard() {
        return board;
    }
    public static int get(int i, int j) {
        return board.get(i).get(j);
    }
    public static void set(Place place, int val) {
        board.get(place.x).set(place.y, val);
        transverseBoard.get(place.y).set(place.x, val);
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
    // On Cages
    
    
  
    // Others
    public static int getSize() {
        return size;
    }
    public static HashMap<Integer, ArrayList<Integer>> getMap() {
        return map;
    }
    
    
    // Other Methods
    public int group(int i, int j) {
        return group[i][j];
    }
    public static boolean isComplete() {
        for (int i = 0; i < group.length; i++)
            for (int j = 0; j < group.length; j++) {
                if(cages[i][j].operation.equals("^") || cages[i][j].operation.equals("%") || cages[i][j].operation.equals("*") ) {
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
                if(cages[i][j].operation.equals("^") ) {
                    if(board.get(i).get(j) == 100)
                        return false;
                }
            }
        return true;
    }
    public static boolean isPossible(Place place, int value) {        
        return !board.get(place.x).contains(value) && !transverseBoard.get(place.y).contains(value) && value < size;
    }
    // Print
    public static void print() {
        printBoard(board);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                System.out.print(cages[i][j].id + ": " + cages[i][j].operation + "\t");
            System.out.println();
        }
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
    
    static ArrayList<Place> getPeople(int i) {
        ArrayList<Place> places = new ArrayList<>();  
        for (int a = 0; a < size; a++) {
            for (int b = 0; b < size; b++) {
                if (group[a][b]==i)
                {
                    Place place = new Place(a, b);
                    places.add(place);
                }
            }
        }
        return places;
        
    }
    
    public static Cage getCage(int ID)
    {
        for(int x=0; x<size; x++)
        {
            for (int y=0; y< size; y++)
            {
                if(cages[x][y].id == ID)
                    return cages[x][y];
            }
        }
        return null;
    }
}