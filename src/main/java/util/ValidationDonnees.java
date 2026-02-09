package util;

import java.util.Scanner;

public class ValidationDonnees {
    public static Scanner input = new Scanner(System.in);

    public static int validateInts(String fieldName) {
        int validatedInt = 0;
        do {
            System.out.print("Saisir " + fieldName + " : ");
            try {
                validatedInt = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez saisir un(e) " + fieldName + " valide.");
                validatedInt = 0;
            }
        } while (validatedInt == 0);
        return validatedInt;
    }

    public static float validateFloats(String fieldName) {
        float validatedFloat = 0;
        do {
            System.out.print("Saisir " + fieldName + " : ");
            try {
                validatedFloat = Float.parseFloat(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez saisir un(e) " + fieldName + " valide.");
                validatedFloat = 0;
            }
        } while (validatedFloat == 0);
        return validatedFloat;
    }

    public static String validateString(String fieldName) {
        String validatedString = "";
        do {
            System.out.print("Saisir " + fieldName + " : ");
            validatedString = input.nextLine();
            if (validatedString.isEmpty()) {
                System.out.println("Veuillez saisir un(e) " + fieldName + " valide.");
            }
        } while (validatedString.isEmpty());
        return validatedString;
    }
}