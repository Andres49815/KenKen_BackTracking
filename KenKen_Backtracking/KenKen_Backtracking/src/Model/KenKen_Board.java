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
    public static HashMap<Integer, Integer> resultsMap;
    // Other Variables
    public static int size;
    public static ArrayList<Integer> groupsArray;
    public static ArrayList<Integer> groupsArray2;

    
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
        Cages(size);
        // ArrayGroup
        FillGroupArray();
        FillGroupArray2();
        // View the results
        Results();
        // Clean the boards
        //CleanBoards();
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
            //print();
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
    
    /* Defines the groups in wich each operation will work */
    private void Cages(int n) {
        Cage.Reset();
        cages = new Cage[n + 6][n + 6];
        group = new int[n + 6][n + 6];
        
        do
            Cages();
        while (!isGrouped());
        FixDims();
        FillCoordinates();
    }
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
                if (!groupsArray.contains(cages[i][j].id))
                    groupsArray.add(cages[i][j].id);
            }
        }
    }
    private static void FillGroupArray2() {
        groupsArray2 = new ArrayList<>();
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!groupsArray2.contains(cages[i][j].id) && !cages[i][j].operation.equals("^"))
                    groupsArray2.add(cages[i][j].id);
            }
        }
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
                newGroup[i - 3][j - 3] = cages[i][j].id;
            }
        }
        cages = new Cage[size][size];
        cages = groupCages;
        group = new int[size][size];
        group = newGroup;
    }
    private static void FillCoordinates() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                int[] coord = {j, i};
                cages[i][j].coordinates.add(coord);
            }
    }
    
    /* Fill the results maps in order to get a O(n) each time we consult */
    private void Results() {
        FillResultsM();
        setCagesResults();
    }
    private void FillResultsM() {
        for (Cage[] row : cages) {
            for (Cage c : row) {
                c.setOperation();
            }
        }
    }
    public static void setCagesResults() {
        HashMap<Integer, ArrayList<Integer>> cagesResults;
        
        cagesResults = new HashMap<Integer, ArrayList<Integer>>();
        for (int i = 0; i < cages.length; i++) {
            for (int j = 0; j < cages.length; j++) {
                if (!cagesResults.containsKey(cages[i][j].id))
                    cagesResults.put(cages[i][j].id, new ArrayList<Integer>());
                cagesResults.get(cages[i][j].id).add(get(i, j));
            }
        }
        putResults(cagesResults);
    }
    public static void putResults(HashMap<Integer, ArrayList<Integer>> cagesResults) {
        for (Cage[] row : cages) {
            for (Cage c : row) {
                c.result = CalculateResults(cagesResults.get(c.id), c);
            }
        }
    }
    public static int CalculateResults(ArrayList<Integer> ar, Cage c) {
        switch(ar.size()) {
            case 1:
                return size_1(ar);
            case 2:
                return size_2(ar, c.operation);
            case 3:
            case 4:
                return size_3(ar, c.operation);
        }
        return 0;
    }
    // Operations
    // size_1: Powers
    private static int size_1(ArrayList<Integer> numbers) {
        return (int)Math.pow(numbers.get(0), 3);
    }
    // size_2: Difference, Division, Module
    private static int size_2(ArrayList<Integer> numbers, String operation) {
        switch (operation) {
            case "-":
                return Difference(numbers);
            case "/":
                return Division(numbers);
            case "%":
                return Module(numbers);
        }
        return 0;
    }
    private static int Difference(ArrayList<Integer> ar) {
        return (ar.get(0) - ar.get(1) > 0 ? 1 : -1) * ar.get(0) - ar.get(1);
    }
    private static int Division(ArrayList<Integer> ar) {
        int result;
        
        result = ar.get(0) / ar.get(1);
        return result != 0 ? result : ar.get(1) / ar.get(0);
    }
    private static int Module(ArrayList<Integer> ar) {
        return ar.get(0) % ar.get(1);
    }
    // size_3: Multiplication, Sum
    private static int size_3(ArrayList<Integer> numbers, String operation) {
        return operation.equals("*") ? Multiplication(numbers) : Sum(numbers);
    }
    private static int Multiplication(ArrayList<Integer> ar) {
        int result;
        
        result = 1;
        for (int actual : ar)
            result *= actual;
        return result;
    }
    private static int Sum(ArrayList<Integer> ar) {
        int result;
        
        result = 0;
        for (int actual : ar)
            result += actual;
        return result;
    }
    
    // Getters and Setters
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
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cages[i][j].id == groupNo)
                    KenKen_Board.set(new Place(i,j), 100);
            }
        }
    }
    public static boolean groupIsComplete(int groupNo) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cages[i][j].id == groupNo && KenKen_Board.get(i, j) == 100)
                    return false;
            }
        }
        return true;
    }
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
                    if(board.get(i).get(j) == 100)
                        return false;
            }
        return true;
    }
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
    
    public static boolean isPossible2(ArrayList<Place> people) {
        
        for (int a = 0; a< people.size() ; a++)
        {
            Place place = people.get(a);
            if(!isPossible3(KenKen_Board.transverseBoard.get(place.y)) && !isPossible3(KenKen_Board.board.get(place.x)))
                return false;
        }
        return true;
    }
    
    public static boolean isPossible3(ArrayList<Integer> row) {
        ArrayList<Integer> nuevos = new ArrayList<>();
        int contador = 0;
        for (int a = 0; a < size ; a++)
        {
            if (row.get(a)!=100)
            {
                if(!nuevos.contains(row.get(a)))
                {
                    nuevos.add(row.get(a));                
                }
                contador++;
            }
        }
        return nuevos.size() == contador;
    }
    
    // Print
    public static void print() {
        printBoard(board);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                System.out.print(cages[i][j].result + ":" + cages[i][j].operation + ":" + cages[i][j].id + "\t");
            System.out.println();
        }
        System.out.println();
////        for (int i = 0; i < group.length; i++) {
////            for (int j = 0; j < group.length; j++)
////                System.out.print(group[i][j] + "\t");
////            System.out.println();
////        }
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
                if(cages[x][y].id == groupID)
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
                if (cages[a][b].id==i)
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
    public static boolean real() {
        for (int x=0; x<size; x++)
        {
            if(isPossible3(KenKen_Board.transverseBoard.get(x)) && isPossible3(KenKen_Board.board.get(x)))
                return true;
        }
        return false;
    }
}