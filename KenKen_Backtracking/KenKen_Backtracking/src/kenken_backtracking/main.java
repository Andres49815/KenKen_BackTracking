package kenken_backtracking;

import Controller.C_MainMenu;
import View.MainMenu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;



/**
 *
 * @author Andres Obando Alfaro
 */
class main {
    
    public static void main(String[] args) {
        boolean[][] s = new boolean[3][3];
        for (boolean[] a : s) {
            for (boolean e : a)
                System.out.print(e + "\t");
            System.out.println();
        }
        new C_MainMenu(new MainMenu());
        
        Queue<Integer> cola=new LinkedList();
    }
}
