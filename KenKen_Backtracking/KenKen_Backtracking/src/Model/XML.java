/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
    //public static int[][] results;
    public  HashMap<Integer, ArrayList<Integer>> map;
    public  HashMap<Integer, Integer> resultsMap;
    // Other Variables
    public  int size;
    public  int threadCount;
    public  ArrayList<Integer> groupsArray;

    public  byte Powers;
    public  byte Modules;
    public  boolean solutionFound;
    
    public  long time;

    public XML(ArrayList<ArrayList<Integer>> board, ArrayList<ArrayList<Integer>> transverseBoard, int[][] group, Cage[][] cages, HashMap<Integer, ArrayList<Integer>> map, HashMap<Integer, Integer> resultsMap, int size, int threadCount, ArrayList<Integer> groupsArray, byte Powers, byte Modules, boolean solutionFound, long time) {
        this.board = board;
        this.transverseBoard = transverseBoard;
        this.group = group;
        this.cages = cages;
        this.map = map;
        this.resultsMap = resultsMap;
        this.size = size;
        this.threadCount = threadCount;
        this.groupsArray = groupsArray;
        this.Powers = Powers;
        this.Modules = Modules;
        this.solutionFound = solutionFound;
        this.time = time;
    }

    

    
    
    
}
