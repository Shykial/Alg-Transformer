package com.company;

public class RotationHandler extends BasicTurnOperations {

    private String currentRotation;

    public RotationHandler() {
        this.currentRotation = "";
    }

    public RotationHandler(String currentRotation) {
        this.currentRotation = currentRotation;
    }

    public String getCurrentRotation() {
        return currentRotation;
    }

    public void setCurrentRotation(String rotation) {
        this.currentRotation = rotation;
    }

    public void clearCurrentRotation() {
        this.currentRotation = "";
    }


    private int getRotationAxisFlag(String rotation) {

        if (rotation.length() == 0) return 10; //zwraca brak osi rotacji gdy takowej nie ma

        return switch (getAxis(rotation)) {
            case "x" -> 11;
            case "y" -> 12;
            case "z" -> 13;
            default -> throw new IllegalArgumentException("Rotation Axis exception");
        };
    }


    public String getRotationDegree(String rotation, int x) {
        return addDegreeFlags(rotation, x-1);
    }

    private String addTwo180Rotations(String firstRotation, String secondRotation) {
        {
            return switch (getRotationAxisFlag(firstRotation) * getRotationAxisFlag(secondRotation)) {
                case 132 -> "z2"; //11*12
                case 143 -> "y2"; //11*13
                case 156 -> "x2"; //12*13
                case 121, 144, 169 -> ""; //11^2, 12^2, 13^2
                default -> throw new RuntimeException("Invalid rotations flag multiplier");
            };
        }
    }

    public void affectCurrentRotation(String rotation) {

        //ustawienie rotacji gdy takowej nie ma
        if (currentRotation.equals("")) {
            currentRotation = rotation;
            return;
        }

        //sprawdzenie czy rotacja jest w jednej osi
        if (!(currentRotation.contains(" "))) {

            //obsluga dodania rotacji po tej samej osi
            if (getAxis(currentRotation).equals(getAxis(rotation)))
                setCurrentRotation(addSameAxisTurns(currentRotation, rotation));

                ////////////////////dodanie dwoch podwojnych rotacji
            else if (getDegreeFlag(currentRotation) == 2 && getDegreeFlag(rotation) == 2)
                this.currentRotation = addTwo180Rotations(currentRotation, rotation);

            else currentRotation += ' ' + rotation;
            //return 1;
        }

        //gdy rotacja sklada sie z dwoch sub rotacji
        else {

            int spacePosition = currentRotation.indexOf(' '); //wyznaczenie separatora i podzial subStringow
            String firstSubRotation = currentRotation.substring(0, spacePosition);
            String secondSubRotation = currentRotation.substring(spacePosition + 1);

           // if (secondSubRotation.charAt(0) == rotation.charAt(0))
            if (getAxis(secondSubRotation).equals(getAxis(rotation)))
                secondSubRotation = addSameAxisTurns(secondSubRotation, rotation); //dodanie drugiej sub rotacji do rotacji gdy sa na tej samej osi

            else if (getDegreeFlag(secondSubRotation) == 2 && getDegreeFlag(rotation) == 2) //dodanie dwoch podwojnych rotacji
                secondSubRotation = addTwo180Rotations(secondSubRotation, rotation);

            //dodanie pierwszej subRotacji do drugiej gdy sa na tej samej osi po dodaniu rotacji docelowej
            if (secondSubRotation.length() > 0 && getAxis(firstSubRotation).equals(getAxis(secondSubRotation))) {
                firstSubRotation = addSameAxisTurns(firstSubRotation, secondSubRotation);
                secondSubRotation = "";
            }


            ////////////////////////////////// AB + C
            int rotationMultiplier = 1;
            if (!getAxis(firstSubRotation).equals(getAxis(rotation)) && !getAxis(secondSubRotation).equals(getAxis(rotation))) {
                String threeRotationsString = "" + getAxis(firstSubRotation) + getAxis(secondSubRotation) + getAxis(rotation);
                if (threeRotationsString.contains("xz") || threeRotationsString.contains("zy")) rotationMultiplier = -1;
                switch (getDegreeFlag(secondSubRotation)) {
                    case 1 -> firstSubRotation = addDegreeFlags(firstSubRotation, rotationMultiplier * getDegreeFlag(rotation));
                    case -1 -> firstSubRotation = addDegreeFlags(firstSubRotation, -1 * rotationMultiplier * getDegreeFlag(rotation));
                    case 2 -> {
                        firstSubRotation = addDegreeFlags(firstSubRotation, 2);
                        secondSubRotation = addDegreeFlags(rotation, 2);
                    }
                }
            }

            ////////////////////////////// AB + A
            else if (getAxis(firstSubRotation).equals(getAxis(rotation)) && (!(getDegreeFlag(secondSubRotation) == 2 && getDegreeFlag(rotation) == 2))) {
                if (getDegreeFlag(rotation) == 2) {
                    firstSubRotation = addDegreeFlags(firstSubRotation, getDegreeFlag(rotation));
                    secondSubRotation = addDegreeFlags(secondSubRotation, 2);
                } else {
                    String twoRotationsString = "" + getAxis(firstSubRotation) + getAxis(secondSubRotation);
                    char complement = 0;
                    if (!twoRotationsString.contains("x")) complement = 'x';
                    else if (!twoRotationsString.contains("y")) complement = 'y';
                    else if (!twoRotationsString.contains("z")) complement = 'z';

                    if (twoRotationsString.equals("zx") || twoRotationsString.equals("xy") || twoRotationsString.equals("yz"))
                        rotationMultiplier = -1;
                    firstSubRotation = addSameAxisTurns(firstSubRotation, rotation);
                    secondSubRotation = getRotationDegree(String.valueOf(complement), rotationMultiplier * getDegreeFlag(secondSubRotation) * getDegreeFlag(rotation));
                }
            }

            if (!getAxis(firstSubRotation).equals(getAxis(secondSubRotation)) && getDegreeFlag(firstSubRotation) == 2 && getDegreeFlag(secondSubRotation) == 2) {
                firstSubRotation = addTwo180Rotations(firstSubRotation, secondSubRotation);
                secondSubRotation = "";
                //System.out.println("mam Cie!");
            } else if ((firstSubRotation.length() > 0 && secondSubRotation.length() > 0 && firstSubRotation.charAt(0) == secondSubRotation.charAt(0))) {
                firstSubRotation = addSameAxisTurns(firstSubRotation, secondSubRotation);
                secondSubRotation = "";
            }


            if (firstSubRotation.equals("")) currentRotation = secondSubRotation;
            else if (secondSubRotation.equals("")) currentRotation = firstSubRotation;
            else this.currentRotation = firstSubRotation + ' ' + secondSubRotation;

        }
    }
}