package com.company;

import java.util.*;

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

    public void displayCubeState() {
        for (Map.Entry<String, char[]> corner : corners.entrySet()) {
            System.out.println(corner.getKey() + ": " + Arrays.toString(corner.getValue()));
        }
        for (Map.Entry<String, char[]> edge : edges.entrySet()) {
            System.out.println(edge.getKey() + ": " + Arrays.toString(edge.getValue()));
        }
    }

    private void cycleElements(String[] corners, String[] edges, int charArrayOrderFlag, int degreeFlag) {
        char[][] tempCorners = new char[corners.length][3];
        char[][] tempEdges = new char[edges.length][2];

        if (corners.length != edges.length)
            throw new RuntimeException("number of corners doesn't match number of edges");
        System.out.println("test2");
        if (degreeFlag == 2) {
            for (int i = 0; i < corners.length; i++) {
                tempCorners[i] = this.corners.get(corners[i]);
                tempEdges[i] = this.edges.get(edges[i]);
            }
        } else {
            switch (charArrayOrderFlag) {
                case 1:
                    for (int i = 0; i < corners.length; i++) {
                        tempCorners[i][0] = this.corners.get(corners[i])[0]; // 0 is an index of char array being value of key represented by i-th String of corners map
                        tempCorners[i][1] = this.corners.get(corners[i])[2];
                        tempCorners[i][2] = this.corners.get(corners[i])[1];
                        tempEdges[i] = this.edges.get(edges[i]);
                    }
                    break;
                case 2:
                    for (int i = 0; i < corners.length; i++) {
                        tempCorners[i][0] = this.corners.get(corners[i])[2]; //reverse char array order
                        tempCorners[i][1] = this.corners.get(corners[i])[1];
                        tempCorners[i][2] = this.corners.get(corners[i])[0];
                        tempEdges[i][0] = this.edges.get(edges[i])[1];
                        tempEdges[i][1] = this.edges.get(edges[i])[0];
                    }
                    break;
                case 3:
                    for (int i = 0; i < corners.length; i++) {
                        tempCorners[i][0] = this.corners.get(corners[i])[1];
                        tempCorners[i][1] = this.corners.get(corners[i])[0];
                        tempCorners[i][2] = this.corners.get(corners[i])[2];
                        tempEdges[i] = this.edges.get(edges[i]);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid charArrayOrderFlag argument");
            }
        }

//            for (int i = 0; i < elements.length; i++) {
//                if (i + degreeFlag >= elements.length)
//                    newElements[i] = elements[(i + degreeFlag - elements.length) % 4];
//                else newElements[i] = elements[i + degreeFlag];
//            }
//
//            System.arraycopy(newElements, 0, elements, 0, newElements.length);
//        }

        for (int i = 0; i < tempCorners.length; i++) {
            if (degreeFlag < 0) {
                this.corners.replace(corners[i], tempCorners[(tempCorners.length - i) % tempCorners.length]);
                this.edges.replace(corners[i], tempEdges[(tempEdges.length - i) % tempEdges.length]);
            } else {
                this.corners.replace(corners[i], tempCorners[(i + degreeFlag) % tempCorners.length]); // replaces this.corners with corners[i] key by i-th + degreeFlag value corrected by size of temp Corners
                this.edges.replace(edges[i], tempEdges[(i + degreeFlag) % tempEdges.length]);
            }
        }
    }

    public void affectCubeState(String move) {

        int charArrayOrderFlag;
        String[] corners, edges;

        switch (getAxis(move)) {
            case "U":
                corners = new String[]{"UBL", "UBR", "UFR", "UFL"};
                edges = new String[]{"UB", "UR", "UF", "UL"};
                charArrayOrderFlag = 1;                 //cycle + swap char[1] with char[2] for corners if not U2, no affect on edges
                break;
            case "D":
                corners = new String[]{"DBL", "DFL", "DFR", "DBR"};
                edges = new String[]{"DB", "DL", "DF", "DR"};
                charArrayOrderFlag = 1;
                break;
            case "F":
                corners = new String[]{"UFL", "UFR", "DFR", "DFL"};
                edges = new String[]{"UF", "FR", "DF", "FL"};
                charArrayOrderFlag = 2;                //cycle + inverse char array order for both corners and edges if not F2 ArrayUtils.reverse(char[] array)
                break;
            case "B":
                corners = new String[]{"UBL", "DBL", "DBR", "UBR"};
                edges = new String[]{"UB", "BL", "DB", "BR"};
                charArrayOrderFlag = 2;
                break;
            case "L":
                corners = new String[]{"UBL", "UFL", "DFL", "DBL"};
                edges = new String[]{"UL", "FL", "DL", "BL"};
                charArrayOrderFlag = 3;                //cycle + swap char[0] with char[1] for corners if not L2, no affect on edges
                break;
            case "R":
                corners = new String[]{"UBR", "DBR", "DFR", "UFR"};
                edges = new String[]{"UR", "BR", "DR", "FR"};
                charArrayOrderFlag = 3;
                break;
            default:
                throw new IllegalArgumentException("cycle elements argument Exception");

        }

        cycleElements(corners, edges, charArrayOrderFlag, getDegreeFlag(move));
    }

    public void affectCubeState(List<String> moves) {
        for (String move : moves) {
            affectCubeState(move);
        }
    }
}
