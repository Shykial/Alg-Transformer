package unused;

import com.company.MovesHandler;

import java.util.ArrayList;

//NAAAAAH, REBUIIIILD


public class CubeStateHandler_OLD {
    private char[] Ue, De, Fe, Be, Re, Le;
    private char[] Uc, Dc, Fc, Bc, Rc, Lc;
    private char[][] cornersState;
    private char[][] originalCornersState;
    private char[][] edgesState;
    private char[][] originalEdgesState;
    private MovesHandler mh = new MovesHandler();

    public CubeStateHandler_OLD() {
        this.Ue = new char[]{'W', 'W', 'W', 'W'};
        //this.Uc = new char[]{'W', 'W', 'W', 'W'};
        Uc = Ue.clone();
        //this.Uc = Ue;

        this.De = new char[]{'Y', 'Y', 'Y', 'Y'};
        this.Dc = new char[]{'Y', 'Y', 'Y', 'Y'};
        //this.Dc = De;

        this.Fe = new char[]{'G', 'G', 'G', 'G'};
        this.Fc = new char[]{'G', 'G', 'G', 'G'};
        //this.Fc = Fe;

        this.Be = new char[]{'B', 'B', 'B', 'B'};
        this.Bc = new char[]{'B', 'B', 'B', 'B'};
        //this.Bc = Be;

        this.Le = new char[]{'O', 'O', 'O', 'O'};
        this.Lc = new char[]{'O', 'O', 'O', 'O'};
        //this.Lc = Le;

        this.Re = new char[]{'R', 'R', 'R', 'R'};
        this.Rc = new char[]{'R', 'R', 'R', 'R'};
        //this.Rc = Re;

        System.out.println("test");

        this.originalEdgesState = new char[][]{Ue, De, Fe, Be, Le, Re};
        this.originalCornersState = new char[][]{Uc, Dc, Fc, Bc, Lc, Rc};

        this.edgesState = originalEdgesState;
        this.cornersState = originalCornersState;
    }

    private void cycleElements(char[] elements, int degreeFlag) {
        char[] newElements = new char[elements.length];
        if (degreeFlag < 0) degreeFlag = elements.length - degreeFlag;
        for (int i = 0; i < elements.length; i++) {
            if (i + degreeFlag >= elements.length)
                newElements[i] = elements[(i + degreeFlag - elements.length) % 4];
            else newElements[i] = elements[i + degreeFlag];
        }

        System.arraycopy(newElements, 0, elements, 0, newElements.length);
    }

    private void cycleElements(char[][] elements, int degreeFlag, byte[] index) {
//        char[] newElements = new char[oneFaceElements.length];
//
//        for (int i = 0; i < oneFaceElements.length; i++) {
//            if (i + degreeFlag >= oneFaceElements.length)
//                newElements[i] = oneFaceElements[(i + degreeFlag - oneFaceElements.length) % 4];
//            else newElements[i] = oneFaceElements[i + degreeFlag];
//        }
//
//        System.arraycopy(newElements, 0, oneFaceElements, 0, newElements.length);

        char[] newElements = new char[index.length / 2];
        //char[] elementsArray = new char[index.length / 2];

        for (int i = 0, j = 0; i < newElements.length; i++, j+=2) {
            newElements[i] = elements [index[j]] [index[j+1]];
        }

        cycleElements(newElements, degreeFlag);

        for (int i = 0, j = 0; i < newElements.length; i++, j+=2) {
            elements[index[j]][index[j+1]] = newElements[i];
        }

        System.out.println("test");
    }


    public void affectCubeState(ArrayList<String> moves) {
        for (String move : moves) affectCubeState(move);
    }

    public void affectCubeState(String move) {

        //char[] temp;
        byte[] temp;
        int degree = mh.getDegreeFlag(move);
        switch (mh.getAxis(move)) {
            case "F":
                cycleElements(Fe, degree); //cycle F face edges
                cycleElements(Fc, degree); //cycle F face corners

                temp = new byte[]{0, 2, 5, 3, 1, 0, 4, 1};
              //  char[] temp2 = new char[]{Ue[2], Re[3], De[0], Le[1]}; //  UDFBRL
                cycleElements(edgesState, degree, temp);

                temp = new byte[]{0, 2, 5, 3, 1, 0, 4, 1}; // 0-U 1-D 2-F 3-B 4-R 5-L
                cycleElements(cornersState, degree, temp);
                temp = new byte[]{0, 3, 5, 0, 1, 1, 4, 2}; // 0-U 1-D 2-F 3-B 4-R 5-L
                cycleElements(cornersState, degree, temp);
                break;
            case "B":
                cycleElements(Be, degree); //cycle B face edges
                cycleElements(Bc, degree); //cycle B face corners

                temp = new byte[]{0, 0, 4, 3, 1, 2, 5, 1}; //   0-U 1-D 2-F 3-B 4-R 5-L
                cycleElements(edgesState, degree, temp);

                temp = new byte[]{0, 0, 4, 3, 1, 2, 5, 1}; // 0-U 1-D 2-F 3-B 4-R 5-L
                cycleElements(cornersState, degree, temp);
                temp = new byte[]{0, 1, 4, 0, 1, 3, 5, 2}; // 0-U 1-D 2-F 3-B 4-R 5-L
                cycleElements(cornersState, degree, temp);
                break;
            case "L":
                cycleElements(Le, degree); //cycle L face edges
                cycleElements(Lc, degree); //cycle L face corners

                temp = new byte[]{0, 3, 2, 3, 1, 3, 3, 1}; //  0-U 1-D 2-F 3-B 4-R 5-L
                cycleElements(edgesState, degree, temp);

                temp = new byte[]{0, 0, 2, 0, 1, 0, 3, 2}; // 0-U 1-D 2-F 3-B 4-R 5-L
                cycleElements(cornersState, degree, temp);
                temp = new byte[]{0, 3, 2, 3, 1, 3, 2, 1}; // 0-U 1-D 2-F 3-B 4-R 5-L
                cycleElements(cornersState, degree, temp);
                break;
            case "R":
                cycleElements(Re, degree); //cycle L face edges
                cycleElements(Rc, degree); //cycle L face corners

                temp = new byte[]{0, 1, 3, 3, 1, 1, 2, 1}; //  0-U 1-D 2-F 3-B 4-R 5-L
                cycleElements(edgesState, degree, temp);

                temp = new byte[]{0, 1, 3, 3, 1, 1, 2, 1}; // 0-U 1-D 2-F 3-B 4-R 5-L
                cycleElements(cornersState, degree, temp);
                temp = new byte[]{0, 2, 3, 0, 1, 2, 2, 2}; // 0-U 1-D 2-F 3-B 4-R 5-L
                cycleElements(cornersState, degree, temp);
                break;
            case "U":
                cycleElements(Ue, degree); //cycle U face edges
                cycleElements(Uc, degree); //cycle U face corners

                temp = new byte[]{0, 1, 3, 3, 1, 1, 2, 1}; //  0-U 1-D 2-F 3-B 4-R 5-L
                cycleElements(edgesState, degree, temp);

                temp = new byte[]{0, 1, 3, 3, 1, 1, 2, 1}; // 0-U 1-D 2-F 3-B 4-R 5-L
                cycleElements(cornersState, degree, temp);
                temp = new byte[]{0, 2, 3, 0, 1, 2, 2, 2}; // 0-U 1-D 2-F 3-B 4-R 5-L
                cycleElements(cornersState, degree, temp);
        }
    }
}
