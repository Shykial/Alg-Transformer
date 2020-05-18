package com.company;

public class BasicTurnOperations {
    public int getDegreeFlag(String move) {

        if (move.length() == 0) return 0; //0 gdy nie ma ruchu
        if (!(move.contains("'") || move.contains("2"))) return 1; //1 gdy nie ma ani prima ani dwojki

        return switch (move.charAt(move.length() - 1)) {
            case '\'' -> -1;
            case '2' -> 2;
            default -> throw new IllegalArgumentException("Move flag exception");
        };

    }

    public String getAxis(String move) {

        if (move.length() == 0) return ""; //0 gdy nie ma ruchu
        if (move.contains("'") || move.contains("2"))
            return move.substring(0, move.length() - 1); //1 gdy nie ma ani prima ani dwojki
        else return move;
    }

    public String addDegreeFlags(String move, int x) {
        return switch (getDegreeFlag(move) + x) {
            case 1 -> getAxis(move);
            case 2, -2 -> getAxis(move) + '2';  // x' + x' = x + x
            case -1, 3 -> getAxis(move) + '\'';
            case 0, 4 -> "";
            default -> throw new IllegalArgumentException("Add degree flags exception");
        };
    }

    public String addSameAxisTurns(String firstMove, String secondMove) {
        return addDegreeFlags(firstMove, getDegreeFlag(secondMove));
    }

}
