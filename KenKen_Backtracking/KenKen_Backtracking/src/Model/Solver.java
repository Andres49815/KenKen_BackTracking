package Model;

import java.util.ArrayList;

/**
 *
 * @author Andres Obando Alfaro
 */
public class Solver {
    private static KenKen_Board KenKen;
    private static ArrayList<ArrayList<Integer>> solution;
    
    public Solver(KenKen_Board KK) {
        KenKen = KK;
        //solution = 
    }
    
    public static void InitializeArray() {
        ArrayList<Integer> partial;
        
        solution = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < KenKen.getBoard().size(); i++) {
            partial = new ArrayList<Integer>();
            for (int j = 0; j < KenKen.getBoard().size(); j++)
                partial.add(0);
            solution.add(partial);
        }
    }

}
