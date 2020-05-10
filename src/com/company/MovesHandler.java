package com.company;

import java.util.ArrayList;

public class MovesHandler extends BasicTurnOperations {


    public String getOppositeMove(String move) {
        if (move.contains("2")) return move;
        else if (move.charAt(move.length() - 1) == '\'') return move.substring(0, move.length() - 1);
        else return move + '\'';
    }


//    private void swapElements(String[] array, int index1, int index2)
//    {
//        String temp = array[index1];
//        array[index1] = array[index2];
//        array[index2] = temp;
//    }
//
//    private void swapElements(char[] array, int index1, int index2)
//    {
//        char temp = array[index1];
//        array[index1] = array[index2];
//        array[index2] = temp;
//    }

    private void affectCubeRotationState(char[] CRS, String rotation) {
        char[] affectedCRS; //

        switch (rotation) {
            case "x": {
                affectedCRS = new char[]{CRS[2], CRS[3], CRS[1], CRS[0], CRS[4], CRS[5]};
                break;
            }

            case "x'": {
                affectedCRS = new char[]{CRS[3], CRS[2], CRS[0], CRS[1], CRS[4], CRS[5]};
                break;
            }
            case "x2": {
                affectedCRS = new char[]{CRS[1], CRS[0], CRS[3], CRS[2], CRS[4], CRS[5]};
                break;
            }
            case "y": {
                affectedCRS = new char[]{CRS[0], CRS[1], CRS[5], CRS[4], CRS[2], CRS[3]};
                break;
            }
            case "y'": {
                affectedCRS = new char[]{CRS[0], CRS[1], CRS[4], CRS[5], CRS[3], CRS[2]};
                break;
            }
            case "y2": {
                affectedCRS = new char[]{CRS[0], CRS[1], CRS[3], CRS[2], CRS[5], CRS[4]};
                break;
            }
            case "z": {
                affectedCRS = new char[]{CRS[4], CRS[5], CRS[2], CRS[3], CRS[1], CRS[0]};
                break;
            }
            case "z'": {
                affectedCRS = new char[]{CRS[5], CRS[4], CRS[2], CRS[3], CRS[0], CRS[1]};
                break;
            }
            case "z2": {
                affectedCRS = new char[]{CRS[1], CRS[0], CRS[2], CRS[3], CRS[5], CRS[4]};
                break;
            }

            default:
                throw new IllegalArgumentException("affect cube rotation state array parameter exception");

        }

        //return affectedCRS;
        System.arraycopy(affectedCRS, 0, CRS, 0, CRS.length);
    }


    public String affectTurn(String turn, RotationHandler rh) {

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
            affectCubeRotationState(cubeRotationState, rotationSubString); //enhanced for

        newTurn.setCharAt(0, cubeRotationState[turnIndex]);

        return newTurn.toString();
    }

    //   public

    public void middleLayerMovesHandler(StringBuilder move, ArrayList<String> moves, RotationHandler rh) {
        String moveFlag;
        String oppositeMoveFlag;

        switch (getDegreeFlag(move.toString())) {
            case 1:
                moveFlag = "";
                oppositeMoveFlag = "'";
                break;
            case -1:
                moveFlag = "'";
                oppositeMoveFlag = "";
                break;
            case 2:
                moveFlag = "2";
                oppositeMoveFlag = "2";
                break;
            default:
                throw new IllegalArgumentException("mlwh Move flag exception");
//                moveFlag = "1000";
//                oppositeMoveFlag = "2000";
//                System.out.println("move flag exception");
        }

        switch (move.charAt(0)) {
            case 'M':
                rh.affectCurrentRotation("x" + oppositeMoveFlag);
                moves.add(affectTurn(("R" + moveFlag), rh));
                moves.add(affectTurn(("L" + oppositeMoveFlag), rh));
                break;
            case 'S':
                rh.affectCurrentRotation("z" + moveFlag);
                moves.add(affectTurn(("F" + oppositeMoveFlag), rh));
                moves.add(affectTurn(("B" + moveFlag), rh));
                break;
            case 'E':
                rh.affectCurrentRotation("y" + oppositeMoveFlag);
                moves.add(affectTurn(("U" + moveFlag), rh));
                moves.add(affectTurn(("D" + oppositeMoveFlag), rh));
                break;
            default:
                throw new IllegalArgumentException("Middle layers exception");

        }
    }
    public String wideMovesHandler(String turn, RotationHandler rh) {

        //String moveFlag = turn.length() > 2 ? String.valueOf(turn.charAt(turn.length() - 1)) : "";
//        String degreeFlag = String.valueOf(turn.charAt(turn.length() - 1));
//        String moveFlag = "'2".contains(degreeFlag) ? degreeFlag : "";
        String moveFlag;
        String oppositeMoveFlag;
        switch (getDegreeFlag(turn)) {
            case 1:
                moveFlag = "";
                oppositeMoveFlag = "'";
                break;

            case -1:
                moveFlag = "'";
                oppositeMoveFlag = "";
                break;
            case 2:
                moveFlag = "2";
                oppositeMoveFlag = "2";
                break;
            default:
                throw new IllegalArgumentException("wmh Move flag exception");

        }

        switch (Character.toUpperCase(turn.charAt(0))) {
            case 'R':
                rh.affectCurrentRotation("x" + moveFlag);
//                System.out.println("obcecna rotacja to: " + rh.getCurrentRotation());
//                System.out.println("test Rw: "+"x"+moveFlag);
                return "L" + moveFlag;
            case 'L':
                rh.affectCurrentRotation("x" + oppositeMoveFlag);
//                System.out.println("obcecna rotacja to: " + rh.getCurrentRotation());
//                System.out.println("test Lw: "+"x"+moveFlag);
                return "R" + moveFlag;
            case 'F':
                rh.affectCurrentRotation("z" + moveFlag);
                return "B" + moveFlag;
            case 'B':
                rh.affectCurrentRotation("z" + oppositeMoveFlag);
                return "F" + moveFlag;
            case 'U':
                rh.affectCurrentRotation("y" + moveFlag);
                return "D" + moveFlag;
            case 'D':
                rh.affectCurrentRotation("y" + oppositeMoveFlag);
                return "U" + moveFlag;
            default:
                throw new IllegalArgumentException("wmh Move flag exception 2");
        }

    }
}
