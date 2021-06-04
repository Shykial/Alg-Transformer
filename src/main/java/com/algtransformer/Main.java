package com.algtransformer;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        long startTime = System.nanoTime();

        InputReader ir = new InputReader();
        CubeStateHandler csh = new CubeStateHandler();

        String moves6 = "[L2 U Rw2 y2, L' U2 x' F L']";
        String moves7 = "[L2 U Rw2 F, L' U2 x' F L']";
        String moves5 = "[L2 U r2, L' U2 x' Bw z L']";
        String moves8 = "M2 U' M, U";
        String moves1 = "L2 Dw' E x' U M2 Rw L S' M2 D L' r";
        String moves12 = "R U R' U' R U R' U'";
        String moves2 = "[r U' r', D2]";

        System.out.println();
        System.out.println("String wprowadzony: " + '\t' + moves5);
        System.out.println();

        List<String> newMoves = ir.readInput(moves5);

        int x = InputReader.movesCanceller(newMoves);
        csh.affectCubeState(newMoves);
        System.out.println();
        System.out.print("String po poteznych skroceniach a'la Jeziorski: \t");

        newMoves.forEach(move -> System.out.print(move + " "));

        System.out.print(ir.getCurrentRotation());
        System.out.print(" (" + newMoves.size() + ")");
        System.out.print(" - " + csh.getIncorrectElementsString());
        System.out.println();
        System.out.println("Kancelacja " + x + " ruchow!!!");

        System.out.println();

        System.out.println("Obecny cube state:");

        long stopTime = System.nanoTime();
        double TotalTime = (stopTime - startTime) * 0.000000001;
        System.out.println("Execution time: " + TotalTime);
    }
}
