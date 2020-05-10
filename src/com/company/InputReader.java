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

    public String commExpander(String moves) {
        ArrayList<String> firstPartArray;
        ArrayList<String> secondPartArray;

        int commaIndex = moves.indexOf(',');
        String firstPart = moves.substring(0, commaIndex);
        String secondPart = moves.substring(commaIndex + 1);

        firstPartArray = movesStringToArray(firstPart);
        secondPartArray = movesStringToArray(secondPart);

        //movesCanceller(secondPartArray);

        StringBuilder expandedComm = new StringBuilder();

        for (String move : firstPartArray) expandedComm.append(move).append(' ');
        for (String move2 : secondPartArray) expandedComm.append(move2).append(' ');

        for (int i = firstPartArray.size() - 1; i >= 0; i--)
            expandedComm.append(mh.getOppositeMove(firstPartArray.get(i))).append(' ');

        for (int i = secondPartArray.size() - 1; i >= 0; i--) {
            expandedComm.append(mh.getOppositeMove(secondPartArray.get(i)));
            if (i != 0) expandedComm.append(' ');
        }

        return expandedComm.toString();
    }

    public ArrayList<String> readInput(String moves) {

        rh.clearCurrentRotation();

        StringBuilder Move = new StringBuilder();
        //StringBuilder Turn = new StringBuilder();
        String rotationsString = "xyz";
        String turnsString = "RLFBUDrlfbudMES";
        String wideMovesString = turnsString.substring(6, 12);
        String middleLayerMovesString = turnsString.substring(12);
        //StringBuilder NewMovesString = new StringBuilder();
        ArrayList<String> NewMoves = new ArrayList<>();

        if (moves.contains(",")) moves = commExpander(moves);
        for (int i = 0; i < moves.length(); i++) {

            //if (i == moves.length() - 1 && Move.toString().equals("")) Move.append(moves.charAt(i));

            //System.out.println("obecny ruch to: "+currentMove);
            if ((moves.charAt(i) == ' ' || moves.charAt(i) == ',' || i == moves.length() - 1)) {
                if (i == moves.length() - 1) Move.append(moves.charAt(i));

                ///rotacja
                if (rotationsString.contains(String.valueOf(Move.charAt(0)))) {
                    //System.out.println("dodaje rotacje "+Move);
                    rh.affectCurrentRotation(Move.toString());
                    //System.out .println("znak "+ (i + 1) + ", obecna rotacja to: " + rh.getCurrentRotation());
                }

                ///ruch
                if (turnsString.contains(String.valueOf(Move.charAt(0)))) {
                    //  System.out.println("iteracja: "+i);


                    //if (i == moves.length() - 1) Move.append(moves.charAt(i));

                    if (middleLayerMovesString.contains(String.valueOf(Move.charAt(0))))
                    {
                        mh.middleLayerMovesHandler(Move, NewMoves, rh);
                        Move = new StringBuilder();
                        continue;
                    }

                    if (Move.toString().contains("w") || wideMovesString.contains(String.valueOf(Move.charAt(0))))
                        Move = new StringBuilder(mh.wideMovesHandler(Move.toString(), rh));

               //     else if (middleLayerMovesString.contains(String.valueOf(Move.charAt(0))))

                    if (rh.getCurrentRotation().length() > 0) {
                        StringBuilder temp = new StringBuilder();
                        temp.append(mh.affectTurn(Move.toString(), rh));
                        Move = temp;
                    }

//                    if (NewMovesString.length() > 0) NewMovesString.append(" ");
//                    NewMovesString.append(Move);
                    NewMoves.add(Move.toString());
                }

                Move = new StringBuilder();
            } else Move.append(moves.charAt(i));
            //  System.out .println("znak "+ (i + 1) + ", obecna rotacja to: " + rh.getCurrentRotation());
            // System.out .println("znak "+ (i + 1) + ", obecny ruch to: " + Move);

        }
        //System.out.println("String po zmianach: " + '\t' + NewMovesString + " (" + NewMoves.size() + ')');
        System.out.println("obecna rotacja : " + '\t' + rh.getCurrentRotation());
        return NewMoves;
    }
}


