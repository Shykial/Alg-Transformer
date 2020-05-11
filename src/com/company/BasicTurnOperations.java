package com.company;

public class BasicTurnOperations {
    public int getDegreeFlag(String move) {

        if (move.length() == 0) return 0; //0 gdy nie ma ruchu
        if (!(move.contains("'") || move.contains("2"))) return 1; //1 gdy nie ma ani prima ani dwojki
        switch (move.charAt(move.length() - 1)) {
            case '\'':
                return -1;
            case '2':
                return 2;
            default:
                // throw new RuntimeException("Move flag exception");
                throw new IllegalArgumentException("Move flag exception");
                // System.out.println("move flag exception");
                // return 1012;
        }

    }

    public String getAxis(String move) {

        if (move.length() == 0) return ""; //0 gdy nie ma ruchu
        if (move.contains("'") || move.contains("2"))
            return move.substring(0, move.length() - 1); //1 gdy nie ma ani prima ani dwojki
        else return move;
    }

    public String addDegreeFlags(String move, int x) {
        switch (getDegreeFlag(move) + x) {
            case 1:
                return getAxis(move);
            case 2:
            case -2:
                return getAxis(move) + '2'; // x' + x' = x + x
            case -1:
            case 3:
                return getAxis(move) + '\'';
            case 0:
            case 4:
                return "";
            default:
                throw new IllegalArgumentException("Add degree flags exception");

        }
    }

    public String addSameAxisTurns(String firstMove, String secondMove) {
        return addDegreeFlags(firstMove, getDegreeFlag(secondMove));
    }

}
