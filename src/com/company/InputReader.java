package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class InputReader {


    private final RotationHandler rh = new RotationHandler();

    public String getCurrentRotation() {
        return rh.getCurrentRotation();
    }

//    private static ArrayList<String> movesStringToArray2(String moves) {
//        ArrayList<String> MovesArray = new ArrayList<>();
//
//        StringBuilder Move = new StringBuilder();
//        for (int i = 0; i < moves.length(); i++) {
//            if (moves.charAt(i) == '[') i++;
//
//            if (moves.charAt(i) == ' ' || moves.charAt(i) == ',' || i == moves.length() - 1) {
//                if ((i == moves.length() - 1) && moves.charAt(i) != ']') Move.append(moves.charAt(i));
//                if (Move.length() == 0) continue;
//                MovesArray.add(Move.toString());
//                Move = new StringBuilder();
//            } else Move.append(moves.charAt(i));
//
//        }
//        return MovesArray;
//    }

    private static ArrayList<String> movesStringToArray(String moves) {
        String movesWithoutCurlyBraces = moves.trim().replaceAll("[\\[\\]]", "");
        return new ArrayList<>(Arrays.asList(movesWithoutCurlyBraces.split(",* ")));
    } //yo, those regexes are kinda cool

    public static int movesCanceller(ArrayList<String> moves) {
        int originalSize = moves.size();
        for (int i = 0; i < moves.size() - 1; i++) {
            if (MovesHandler.getAxis(moves.get(i)).equals(MovesHandler.getAxis(moves.get(i + 1)))) //sprawdzenie czy dwa ruchy sa tego samego znaku
            {
                moves.set(i + 1, MovesHandler.addSameAxisTurns(moves.get(i), moves.get(i + 1)));
                if (moves.get(i + 1).equals("")) moves.remove(i + 1);
                moves.remove(i);
                i -= 2;
            }
        }
        return originalSize - moves.size();
    }


    public static ArrayList<String> commExpander(String moves) {
        ArrayList<String> firstPartArray;
        ArrayList<String> secondPartArray;
//        String[] firstPartArray;
//        String[] secondPartArray;
        int commaIndex = moves.indexOf(',');
        String firstPart = moves.substring(0, commaIndex);
        String secondPart = moves.substring(commaIndex + 1);

        firstPartArray = movesStringToArray(firstPart);
        secondPartArray = movesStringToArray(secondPart);

        ArrayList<String> expandedComm = new ArrayList<>();
        expandedComm.addAll(firstPartArray);
        expandedComm.addAll(secondPartArray);

        for (int i = firstPartArray.size() - 1; i >= 0; i--)
            expandedComm.add(MovesHandler.getOppositeMove(firstPartArray.get(i)));


        for (int i = secondPartArray.size() - 1; i >= 0; i--)
            expandedComm.add(MovesHandler.getOppositeMove(secondPartArray.get(i)));

        return expandedComm;
    }

    public ArrayList<String> readInput(String moves) {

        rh.clearCurrentRotation();

        String rotationsString = "xyz";
        String turnsString = "RLFBUDrlfbudMES";
        String wideMovesString = turnsString.substring(6, 12);
        String middleLayerMovesString = turnsString.substring(12);
        String[] Moves;
        ArrayList<String> NewMoves = new ArrayList<>();

        if (moves.contains(",")) Moves = commExpander(moves).toArray(new String[0]);
        else Moves = movesStringToArray(moves).toArray(new String[0]);

        for (String move : Moves) {

            if (rotationsString.contains(String.valueOf(move.charAt(0))))
                rh.affectCurrentRotation(move);


            if (turnsString.contains(String.valueOf(move.charAt(0)))) {

                if (middleLayerMovesString.contains(String.valueOf(move.charAt(0)))) {
                    MovesHandler.middleLayerMovesHandler(move, NewMoves, rh);
                    continue;
                }

                if (move.contains("w") || wideMovesString.contains(String.valueOf(move.charAt(0))))
                    move = MovesHandler.wideMovesHandler(move, rh);

                if (rh.getCurrentRotation().length() > 0) {
                    move = MovesHandler.affectTurn(move, rh);
                }

                NewMoves.add(move);
            }

        }
        // System.out.println("obecna rotacja : " + '\t' + rh.getCurrentRotation());
        return NewMoves;
    }
}


