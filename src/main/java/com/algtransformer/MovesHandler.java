package com.algtransformer;

import java.util.ArrayList;
import java.util.List;

public class MovesHandler extends BasicTurnOperations {

    public static String getOppositeMove(String move) {
        if (move.contains("2")) return move;
        else if (move.charAt(move.length() - 1) == '\'') return move.substring(0, move.length() - 1);
        else return move + '\'';
    }


    private static void affectCubeRotationState(char[] CRS, String rotation) {
        char[] affectedCRS;

        switch (rotation) {
            case "x" -> affectedCRS = new char[]{CRS[2], CRS[3], CRS[1], CRS[0], CRS[4], CRS[5]};
            case "x'" -> affectedCRS = new char[]{CRS[3], CRS[2], CRS[0], CRS[1], CRS[4], CRS[5]};
            case "x2" -> affectedCRS = new char[]{CRS[1], CRS[0], CRS[3], CRS[2], CRS[4], CRS[5]};
            case "y" -> affectedCRS = new char[]{CRS[0], CRS[1], CRS[5], CRS[4], CRS[2], CRS[3]};
            case "y'" -> affectedCRS = new char[]{CRS[0], CRS[1], CRS[4], CRS[5], CRS[3], CRS[2]};
            case "y2" -> affectedCRS = new char[]{CRS[0], CRS[1], CRS[3], CRS[2], CRS[5], CRS[4]};
            case "z" -> affectedCRS = new char[]{CRS[4], CRS[5], CRS[2], CRS[3], CRS[1], CRS[0]};
            case "z'" -> affectedCRS = new char[]{CRS[5], CRS[4], CRS[2], CRS[3], CRS[0], CRS[1]};
            case "z2" -> affectedCRS = new char[]{CRS[1], CRS[0], CRS[2], CRS[3], CRS[5], CRS[4]};
            default -> throw new IllegalArgumentException("affect cube rotation state array parameter exception");
        }

        System.arraycopy(affectedCRS, 0, CRS, 0, CRS.length);
    }


    public static String affectTurn(String turn, RotationHandler rh) {

        if (rh.getCurrentRotation().equals("")) return turn;
        StringBuilder newTurn = new StringBuilder();
        newTurn.append(turn);

        String rotation = rh.getCurrentRotation();

        String cubeRotationStateString = "UDFBLR"; //UDFBLR
        int turnIndex = cubeRotationStateString.indexOf(turn.charAt(0));

        String firstRotationSubString, secondRotationSubString;

        ArrayList<String> rotationSubStrings = new ArrayList<>();

        if (rotation.contains(" ")) {
            int separator = rotation.indexOf(' ');
            firstRotationSubString = rotation.substring(0, separator);
            secondRotationSubString = rotation.substring(separator + 1);
            rotationSubStrings.add(firstRotationSubString);
            rotationSubStrings.add(secondRotationSubString);
        } else rotationSubStrings.add(rotation);

        char[] cubeRotationState = {'U', 'D', 'F', 'B', 'L', 'R'}; // U D F B L R
        for (String rotationSubString : rotationSubStrings)
            affectCubeRotationState(cubeRotationState, rotationSubString);

        newTurn.setCharAt(0, cubeRotationState[turnIndex]);

        return newTurn.toString();
    }

    public static void middleLayerMovesHandler(String move, List<String> moves, RotationHandler rh) {
        String moveFlag;
        String oppositeMoveFlag;

        switch (getDegreeFlag(move)) {
            case 1 -> {
                moveFlag = "";
                oppositeMoveFlag = "'";
            }
            case -1 -> {
                moveFlag = "'";
                oppositeMoveFlag = "";
            }
            case 2 -> {
                moveFlag = "2";
                oppositeMoveFlag = "2";
            }
            default -> throw new IllegalArgumentException("mlwh Move flag exception");
        }

        switch (getAxis(move)) {
            case "M" -> {
                rh.affectCurrentRotation("x" + oppositeMoveFlag);
                moves.add(affectTurn(("R" + moveFlag), rh));
                moves.add(affectTurn(("L" + oppositeMoveFlag), rh));
            }
            case "S" -> {
                rh.affectCurrentRotation("z" + moveFlag);
                moves.add(affectTurn(("F" + oppositeMoveFlag), rh));
                moves.add(affectTurn(("B" + moveFlag), rh));
            }
            case "E" -> {
                rh.affectCurrentRotation("y" + oppositeMoveFlag);
                moves.add(affectTurn(("U" + moveFlag), rh));
                moves.add(affectTurn(("D" + oppositeMoveFlag), rh));
            }
            default -> throw new IllegalArgumentException("Middle layers exception");
        }
    }
    public static String wideMovesHandler(String turn, RotationHandler rh) {

        String moveFlag;
        String oppositeMoveFlag;

        switch (getDegreeFlag(turn)) {
            case 1 -> {
                moveFlag = "";
                oppositeMoveFlag = "'";
            }
            case -1 -> {
                moveFlag = "'";
                oppositeMoveFlag = "";
            }
            case 2 -> {
                moveFlag = "2";
                oppositeMoveFlag = "2";
            }
            default -> throw new IllegalArgumentException("wmh Move flag exception");
        }

        switch (Character.toUpperCase(turn.charAt(0))) {
            case 'R' -> {
                rh.affectCurrentRotation("x" + moveFlag);
                return "L" + moveFlag;
            }
            case 'L' -> {
                rh.affectCurrentRotation("x" + oppositeMoveFlag);
                return "R" + moveFlag;
            }
            case 'F' -> {
                rh.affectCurrentRotation("z" + moveFlag);
                return "B" + moveFlag;
            }
            case 'B' -> {
                rh.affectCurrentRotation("z" + oppositeMoveFlag);
                return "F" + moveFlag;
            }
            case 'U' -> {
                rh.affectCurrentRotation("y" + moveFlag);
                return "D" + moveFlag;
            }
            case 'D' -> {
                rh.affectCurrentRotation("y" + oppositeMoveFlag);
                return "U" + moveFlag;
            }
            default -> throw new IllegalArgumentException("wmh Move flag exception 2");
        }
    }
}
