package com.algtransformer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class CubeStateHandler extends BasicTurnOperations {

    private Map<String, char[]> solvedCorners;
    private Map<String, char[]> solvedEdges;
    private final Map<String, char[]> corners;
    private final Map<String, char[]> edges;
    private int incorrectCorners = 0;
    private int incorrectEdges = 0;
    private int twistedCorners = 0;
    private int flippedEdges = 0;

    public CubeStateHandler() {
        solvedCorners = Map.of(
                // U face corners:
                "UBL", new char[]{'W', 'B', 'O'},
                "UBR", new char[]{'W', 'B', 'R'},
                "UFR", new char[]{'W', 'G', 'R'},
                "UFL", new char[]{'W', 'G', 'O'},
                // D face corners:
                "DBL", new char[]{'Y', 'B', 'O'},
                "DBR", new char[]{'Y', 'B', 'R'},
                "DFR", new char[]{'Y', 'G', 'R'},
                "DFL", new char[]{'Y', 'G', 'O'}
        );

        solvedEdges = Map.ofEntries(
                // U face edges:
                entry("UB", new char[]{'W', 'B'}),
                entry("UR", new char[]{'W', 'R'}),
                entry("UF", new char[]{'W', 'G'}),
                entry("UL", new char[]{'W', 'O'}),
                // E slice edges:)
                entry("BL", new char[]{'B', 'O'}),
                entry("BR", new char[]{'B', 'R'}),
                entry("FL", new char[]{'G', 'O'}),
                entry("FR", new char[]{'G', 'R'}),
                // D face edges:)
                entry("DB", new char[]{'Y', 'B'}),
                entry("DR", new char[]{'Y', 'R'}),
                entry("DF", new char[]{'Y', 'G'}),
                entry("DL", new char[]{'Y', 'O'})
        );
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

        try {
            for (int i = 0; i < tempCorners.length; i++) {
                if (i - degreeFlag < 0) {
                    this.corners.replace(corners[i], tempCorners[(i + tempCorners.length - degreeFlag) % tempCorners.length]);
                    this.edges.replace(edges[i], tempEdges[(i + tempCorners.length - degreeFlag) % tempEdges.length]);
                } else {
                    this.corners.replace(corners[i], tempCorners[(i - degreeFlag) % tempCorners.length]); // replaces this.corners with corners[i] key by i-th + degreeFlag value corrected by size of temp Corners
                    this.edges.replace(edges[i], tempEdges[(i - degreeFlag) % tempEdges.length]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Replacing cube state elements exception");
        }
    }

    public void affectCubeState(String move) {

        int charArrayOrderFlag;
        String[] corners, edges;

        switch (getAxis(move)) {
            case "U" -> {
                corners = new String[]{"UBL", "UBR", "UFR", "UFL"};
                edges = new String[]{"UB", "UR", "UF", "UL"};
                charArrayOrderFlag = 1;                 //cycle + swap char[1] with char[2] for corners if not U2, no affect on edges
            }
            case "D" -> {
                corners = new String[]{"DBL", "DFL", "DFR", "DBR"};
                edges = new String[]{"DB", "DL", "DF", "DR"};
                charArrayOrderFlag = 1;
            }
            case "F" -> {
                corners = new String[]{"UFL", "UFR", "DFR", "DFL"};
                edges = new String[]{"UF", "FR", "DF", "FL"};
                charArrayOrderFlag = 2;                //cycle + inverse char array order for both corners and edges if not F2 ArrayUtils.reverse(char[] array)
            }
            case "B" -> {
                corners = new String[]{"UBL", "DBL", "DBR", "UBR"};
                edges = new String[]{"UB", "BL", "DB", "BR"};
                charArrayOrderFlag = 2;
            }
            case "L" -> {
                corners = new String[]{"UBL", "UFL", "DFL", "DBL"};
                edges = new String[]{"UL", "FL", "DL", "BL"};
                charArrayOrderFlag = 3;                //cycle + swap char[0] with char[1] for corners if not L2, no affect on edges
            }
            case "R" -> {
                corners = new String[]{"UBR", "DBR", "DFR", "UFR"};
                edges = new String[]{"UR", "BR", "DR", "FR"};
                charArrayOrderFlag = 3;
            }
            default -> throw new IllegalArgumentException("cycle elements argument Exception");
        }

        cycleElements(corners, edges, charArrayOrderFlag, getDegreeFlag(move));
    }

    public void affectCubeState(List<String> moves) {
        moves.forEach(this::affectCubeState);
    }

    public void countIncorrectElements() {
        for (Map.Entry<String, char[]> el : corners.entrySet()) {
            if (!Arrays.equals(el.getValue(), solvedCorners.get(el.getKey()))) {
                char[] temp1 = el.getValue().clone();
                char[] temp2 = solvedCorners.get(el.getKey()).clone();
                Arrays.sort(temp1);
                Arrays.sort(temp2);
                if (Arrays.equals(temp1, temp2)) {
                    twistedCorners++;
                }
                else {
                    incorrectCorners++;
                }
            }
        }

        for (Map.Entry<String, char[]> el : edges.entrySet()) {
            if (!Arrays.equals(el.getValue(), solvedEdges.get(el.getKey()))) {
                char[] temp1 = el.getValue().clone();
                char[] temp2 = solvedEdges.get(el.getKey()).clone();
                Arrays.sort(temp1);
                Arrays.sort(temp2);
                if (Arrays.equals(temp1, temp2)) {
                    flippedEdges++;
                }
                else {
                    incorrectEdges++;
                }
            }
        }
    }

    public String getIncorrectElementsString() {
        countIncorrectElements();
        StringBuilder sb = new StringBuilder();
        if (incorrectEdges > 0) sb.append(incorrectEdges).append("e");
        if (flippedEdges > 0) sb.append(flippedEdges).append("fe");
        if (incorrectCorners > 0) sb.append(incorrectCorners).append("c");
        if (twistedCorners > 0) sb.append(twistedCorners).append("tc");
        return sb.toString();
    }

}
