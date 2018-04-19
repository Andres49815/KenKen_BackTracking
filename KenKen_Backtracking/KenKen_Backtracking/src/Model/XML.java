/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Rubén González V
 */
public class XML {
    private  ArrayList<ArrayList<Integer>> board;
    private  ArrayList<ArrayList<Integer>> transverseBoard;
    // Group
    public  int[][] group;
    // Results
    private  int[][] results;
    private  HashMap<Integer, ArrayList<Integer>> map;
    public  HashMap<Integer, Integer> resultsMap;
    public  HashMap<Integer, String> operations;
    // Other Variables
    private  int actual;
    public  int size;

    
    public  byte Powers;
    public  byte Modules;
    public  boolean solutionFound;
    public  ArrayList<Integer> range;

    public XML(ArrayList<ArrayList<Integer>> board, ArrayList<ArrayList<Integer>> transverseBoard, int[][] group, int[][] results, HashMap<Integer, ArrayList<Integer>> map, HashMap<Integer, Integer> resultsMap, HashMap<Integer, String> operations, int actual, int size, byte Powers, byte Modules, boolean solutionFound, ArrayList<Integer> range) {
        this.board = board;
        this.transverseBoard = transverseBoard;
        this.group = group;
        this.results = results;
        this.map = map;
        this.resultsMap = resultsMap;
        this.operations = operations;
        this.actual = actual;
        this.size = size;
        this.Powers = Powers;
        this.Modules = Modules;
        this.solutionFound = solutionFound;
        this.range = range;
    }
    
    
    
}
