package com.company;

import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Arrays;
//import java.util.Scanner;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;

public class Main {

    public static void main(String[] args) {

        long startTime = System.nanoTime();

       // HashMap hm = new HashMap();

        InputReader ir = new InputReader();
        CubeStateHandler csh = new CubeStateHandler();
//        CubeStateHandler_OLD csh = new CubeStateHandler_OLD();
//        csh.affectCubeState("F'");

//        Scanner s = new Scanner(System.in);
//        String moves = s.nextLine();
//        String moves = "F2 L' y B2 z' y2 F2 z y x z2 R2 z' y F' x2";
//        String moves2 = "x2 y' y2 z x' y' z2 y y' x' z2 y z y2 z2 y x'";
//        String moves3 = "F2 Rw' B2 D' x2 z' Uw Bw' z2 Lw2 z' R Uw'";
//        String moves4 = "Rw' L2 D2 x' Fw Uw' z L2 R2 x' Dw2 Bw' y";
////
//        String moves5 = "[Rw2 F' x Uw', L2 D' B Lw]";
//        String moves6 = "Rw2 F' x Uw', L2 D' B Lw";
//        String moves7 = "Rw2 F' x Uw',L2 D' B Lw";
//
//
//        //System.out.println(moves.length());
//        System.out.println();
//        System.out.println("twoj string to:" + '\t'+ moves3);
//        System.out.println();
//        //ir.readInput(moves);
//
//        //System.out.println("###########");
//        ir.readInput(moves3);
//
//        System.out.println();
//        System.out.println();
//        System.out.println("twoj string to: " + '\t'+ moves5);
//        System.out.println();
//        ir.readInput(moves5);
//
//        System.out.println();
//        System.out.println();
//        System.out.println("twoj string to: " + '\t'+ moves7);
//        System.out.println();
//        ir.readInput(moves7);
//
//        ArrayList<String> movvess = new ArrayList<String>(Arrays.asList("L2", "B2", "D2", "F2", "Rw2", "Rw2", "F2", "D'", "Rw", "U2"));
//
//        System.out.println(movvess);
//
//        ir.movesCanceller(movvess);
//
//        System.out.println(movvess);
        String moves6 = "[L2 U Rw2 y2, L' U2 x' F L']";
        String moves7 = "[L2 U Rw2 F, L' U2 x' F L']";
        String moves1 = "[L2 U r', L' U2 x' Bw z L']";
        String moves8 = "M2 U' M, U";
        String moves5 = "L2 Dw' E x' U M2 Rw L S' M2 D L' r";
        String moves12 ="R U R' U' R U R' U'";
        String moves2 = "[r U' r', D2]";

        System.out.println();
        System.out.println("String wprowadzony: " + '\t' + moves5);
        System.out.println();

        ArrayList<String> newMoves = ir.readInput(moves5);

//        for (int i = 0; i < 1000000; i++) {
//            newMoves = ir.readInput(moves5);
//        }

        int x = ir.movesCanceller(newMoves);
        csh.affectCubeState(newMoves);
        System.out.println();
        System.out.print("String po poteznych skroceniach a'la Jeziorski: \t");
        for (String move : newMoves) System.out.print(move + " ");

        System.out.print(ir.getCurrentRotation());
        System.out.print(" (" + newMoves.size() + ")");
        System.out.print(" - " + csh.getIncorrectElementsString());
        System.out.println();
        System.out.println("Kancelacja " + x + " ruchow!!!");

        System.out.println();

        System.out.println("Obecny cube state:");
       // csh.displayCubeState();
        long stopTime = System.nanoTime();
        double TotalTime = (stopTime - startTime) * 0.000000001;
        System.out.println("Execution time: " + TotalTime);

    }
}
