package com.algtransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputReader {

    private final RotationHandler rh = new RotationHandler();

    public String getCurrentRotation() {
        return rh.getCurrentRotation();
    }

    private static List<String> movesStringToArray(String moves) {
        return new ArrayList<>(
                Arrays.asList(moves.trim().replaceAll("[\\[\\]]", "")
                        .split(",* ")
                )
        );
    }

    public static int movesCanceller(List<String> moves) {
        int originalSize = moves.size();
        for (int i = 0; i < moves.size() - 1; i++) {
            if (MovesHandler.getAxis(moves.get(i)).equals(MovesHandler.getAxis(moves.get(i + 1)))) //checking if two moves don't result in same sign
            {
                moves.set(i + 1, MovesHandler.addSameAxisTurns(moves.get(i), moves.get(i + 1)));
                if (moves.get(i + 1).equals("")) {
                    moves.remove(i + 1);
                }
                moves.remove(i);
                i -= 2;
            }
        }
        return originalSize - moves.size();
    }


    private static List<String> getExpandedMovesList(String moves) {

        int commaIndex = moves.indexOf(',');
        String firstPart = moves.substring(0, commaIndex);
        String secondPart = moves.substring(commaIndex + 1);

        List<String> firstPartList = movesStringToArray(firstPart);
        List<String> secondPartList = movesStringToArray(secondPart);

        ArrayList<String> expandedComm = new ArrayList<>();
        expandedComm.addAll(firstPartList);
        expandedComm.addAll(secondPartList);

        for (int i = firstPartList.size() - 1; i >= 0; i--)
            expandedComm.add(MovesHandler.getOppositeMove(firstPartList.get(i)));

        for (int i = secondPartList.size() - 1; i >= 0; i--)
            expandedComm.add(MovesHandler.getOppositeMove(secondPartList.get(i)));

        return expandedComm;
    }

    public List<String> readInput(String moves) {

        rh.clearCurrentRotation();

        String rotationsString = "xyz";
        String turnsString = "RLFBUDrlfbudMES";
        String wideMovesString = turnsString.substring(6, 12);
        String middleLayerMovesString = turnsString.substring(12);

        List<String> newMoveList = new ArrayList<>();

        List<String> moveList = moves.contains(",") ? getExpandedMovesList(moves) : movesStringToArray(moves);

        for (String move : moveList) {

            if (rotationsString.contains(String.valueOf(move.charAt(0))))
                rh.affectCurrentRotation(move);


            if (turnsString.contains(String.valueOf(move.charAt(0)))) {

                if (middleLayerMovesString.contains(String.valueOf(move.charAt(0)))) {
                    MovesHandler.middleLayerMovesHandler(move, newMoveList, rh);
                    continue;
                }

                if (move.contains("w") || wideMovesString.contains(String.valueOf(move.charAt(0))))
                    move = MovesHandler.wideMovesHandler(move, rh);

                if (rh.getCurrentRotation().length() > 0) {
                    move = MovesHandler.affectTurn(move, rh);
                }

                newMoveList.add(move);
            }

        }
        return newMoveList;
    }
}


