package com.company;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CubeStateHandler extends BasicTurnOperations {
//    String[] corners;
//    String[] edges;

    HashMap<String, char[]> solvedCorners = new HashMap<>();
    HashMap<String, char[]> solvedEdges = new HashMap<>();
    HashMap<String, char[]> corners;
    HashMap<String, char[]> edges;

    public CubeStateHandler() {
        // U face corners:
        solvedCorners.put("UBL", new char[]{'W', 'B', 'O'});
        solvedCorners.put("UBR", new char[]{'W', 'B', 'R'});
        solvedCorners.put("UFR", new char[]{'W', 'G', 'R'});
        solvedCorners.put("UFL", new char[]{'W', 'G', 'O'});
        // D face corners:
        solvedCorners.put("DBL", new char[]{'Y', 'B', 'O'});
        solvedCorners.put("DBR", new char[]{'Y', 'B', 'R'});
        solvedCorners.put("DFR", new char[]{'Y', 'G', 'R'});
        solvedCorners.put("DFL", new char[]{'Y', 'G', 'O'});

        // U face edges:
        solvedEdges.put("UB", new char[]{'W', 'B'});
        solvedEdges.put("UR", new char[]{'W', 'R'});
        solvedEdges.put("UF", new char[]{'W', 'G'});
        solvedEdges.put("UL", new char[]{'W', 'O'});
        // E slice edges:
        solvedEdges.put("BL", new char[]{'B', 'O'});
        solvedEdges.put("BR", new char[]{'B', 'R'});
        solvedEdges.put("FL", new char[]{'G', 'O'});
        solvedEdges.put("FR", new char[]{'G', 'R'});
        // D face edges:
        solvedEdges.put("DB", new char[]{'Y', 'B'});
        solvedEdges.put("DR", new char[]{'Y', 'R'});
        solvedEdges.put("DF", new char[]{'Y', 'G'});
        solvedEdges.put("DL", new char[]{'Y', 'O'});

        //creating shallow copied maps for corners and edges
        corners = new HashMap<>(solvedCorners);
        edges = new HashMap<>(solvedEdges);
    }

    public void displayCubeState (){
        for (Map.Entry<String,char[]> corner : corners.entrySet()) {
            System.out.println(corner.getKey()+": "+ Arrays.toString(corner.getValue()));
        }
        for (Map.Entry<String,char[]> edge : edges.entrySet()) {
            System.out.println(edge.getKey()+": "+ Arrays.toString(edge.getValue()));
        }
    }

    private void cycleElements(String[] corners, String[] edges, int charArrayOrderFlag) {

    }

    public void affectCubeState(String move) {

        int charArrayOrderFlag;
        String[] corners, edges;

        switch (getAxis(move)) {
            case "U":
                //cycle + swap char[1] with char[2] for corners, no affect on edges
                corners = new String[]{"UBL", "UBR", "UFR", "UFL"};
                edges = new String[]{"UB", "UR", "UF", "UL"};
                charArrayOrderFlag = 1;
                break;
            case "D":
                corners = new String[]{"DBL", "DFL", "DFR", "DBR"};
                edges = new String[]{"DB", "DL", "DF", "DR"};
                charArrayOrderFlag = 1;
                break;
            case "F":
                //cycle + inverse char array order for both corners and edges if not F2 ArrayUtils.reverse(char[] array)
                corners = new String[]{"UFL", "UFR", "DFR", "DFL"};
                edges = new String[]{"UF", "FR", "DF", "FL"};
                charArrayOrderFlag = 2;
                break;
            case "B":
                corners = new String[]{"UBL", "DBL", "DBR", "UBR"};
                edges = new String[]{"UB", "BL", "DB", "BR"};
                charArrayOrderFlag = 2;
                break;
            case "L":
                //cycle + swap char[0] with char[1] for corners, no affect on edges
                corners = new String[]{"UBL", "UFL", "DFL", "DBL"};
                edges = new String[]{"UL", "FL", "DL", "BL"};
                charArrayOrderFlag = 3;
                break;
            case "R":
                corners = new String[]{"UBR", "DBR", "DFR", "UFR"};
                edges = new String[]{"UR", "BR", "DR", "FR"};
                charArrayOrderFlag = 3;
                break;
            default:
                throw new IllegalArgumentException("cycle elements argument Exception");

        }

    }
}
