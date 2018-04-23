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
    public  ArrayList<ArrayList<Integer>> board;
    public  ArrayList<ArrayList<Integer>> transverseBoard;
    // Group
    public  int[][] group;
    public  Cage[][] cages;
    // Results
    public  int[][] results;
    public  HashMap<Integer, ArrayList<Integer>> map;
    public  HashMap<Integer, Integer> resultsMap;
    public  HashMap<Integer, String> operations;
    // Other Variables
    public  int actual;
    public  int size;
    public  ArrayList<Integer> groupsArray;
    public  byte Powers;
    public  byte Modules;
    public  boolean solutionFound;

    public XML(ArrayList<ArrayList<Integer>> board, ArrayList<ArrayList<Integer>> transverseBoard, int[][] group, Cage[][] cages, int[][] results, HashMap<Integer, ArrayList<Integer>> map, HashMap<Integer, Integer> resultsMap, HashMap<Integer, String> operations, int actual, int size, ArrayList<Integer> groupsArray, byte Powers, byte Modules, boolean solutionFound) {
        this.board = board;
        this.transverseBoard = transverseBoard;
        this.group = group;
        this.cages = cages;
        this.results = results;
        this.map = map;
        this.resultsMap = resultsMap;
        this.operations = operations;
        this.actual = actual;
        this.size = size;
        this.groupsArray = groupsArray;
        this.Powers = Powers;
        this.Modules = Modules;
        this.solutionFound = solutionFound;
    }

    
    
    
    
}
