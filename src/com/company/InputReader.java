package com.company;

import java.util.ArrayList;

public class InputReader {

    private final RotationHandler rh = new RotationHandler();
    private final MovesHandler mh = new MovesHandler();

    public String getCurrentRotation() {
        return rh.getCurrentRotation();
    }

    private ArrayList<String> movesStringToArray(String moves) {
        ArrayList<String> MovesArray = new ArrayList<>();

        StringBuilder Move = new StringBuilder();
        for (int i = 0; i < moves.length(); i++) {
            if (moves.charAt(i) == '[') i++;

            if (moves.charAt(i) == ' ' || moves.charAt(i) == ',' || i == moves.length() - 1) {
                if ((i == moves.length() - 1) && moves.charAt(i) != ']') Move.append(moves.charAt(i));
                if (Move.length() == 0) continue;
                MovesArray.add(Move.toString());
                Move = new StringBuilder();
            } else Move.append(moves.charAt(i));

        }
        return MovesArray;
    }

    public int movesCanceller(ArrayList<String> moves) {
        int originalSize = moves.size();
        for (int i = 0; i < moves.size() - 1; i++) {
            if (mh.getAxis(moves.get(i)).equals(mh.getAxis(moves.get(i + 1)))) //sprawdzenie czy dwa ruchy sa tego samego znaku
            {
                moves.set(i + 1, mh.addSameAxisTurns(moves.get(i), moves.get(i + 1)));
                if (moves.get(i + 1).equals("")) moves.remove(i + 1);
                moves.remove(i);
                i -= 2;
            }
        }
        return originalSize - moves.size();
    }

//    public String algExpander(String moves) {
//        return "";
//
//    }


    public ArrayList<String> commExpander(String moves) {
        ArrayList<String> firstPartArray;
        ArrayList<String> secondPartArray;

        int commaIndex = moves.indexOf(',');
        String firstPart = moves.substring(0, commaIndex);
        String secondPart = moves.substring(commaIndex + 1);

        firstPartArray = movesStringToArray(firstPart);
        secondPartArray = movesStringToArray(secondPart);
//
//        StringBuilder expandedComm = new StringBuilder();
//
//        for (String move : firstPartArray) expandedComm.append(move).append(' ');
//        for (String move2 : secondPartArray) expandedComm.append(move2).append(' ');
//
//        for (int i = firstPartArray.size() - 1; i >= 0; i--)
//            expandedComm.append(mh.getOppositeMove(firstPartArray.get(i))).append(' ');
//
//        for (int i = secondPartArray.size() - 1; i >= 0; i--) {
//            expandedComm.append(mh.getOppositeMove(secondPartArray.get(i)));
//            if (i != 0) expandedComm.append(' ');
//        }
//
//        return expandedComm.toString();
        ArrayList<String> expandedComm = new ArrayList<>();
        expandedComm.addAll(firstPartArray);
        expandedComm.addAll(secondPartArray);

        for (int i = firstPartArray.size() - 1; i >= 0; i--)
            expandedComm.add(mh.getOppositeMove(firstPartArray.get(i)));


        for (int i = secondPartArray.size() - 1; i >= 0; i--)
            expandedComm.add(mh.getOppositeMove(secondPartArray.get(i)));

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
                    mh.middleLayerMovesHandler(move, NewMoves, rh);
                    continue;
                }

                if (move.contains("w") || wideMovesString.contains(String.valueOf(move.charAt(0))))
                    move = mh.wideMovesHandler(move, rh);

                if (rh.getCurrentRotation().length() > 0) {
                    move = mh.affectTurn(move, rh);
                }

                NewMoves.add(move);
            }

        }
        // System.out.println("obecna rotacja : " + '\t' + rh.getCurrentRotation());
        return NewMoves;
    }
}


