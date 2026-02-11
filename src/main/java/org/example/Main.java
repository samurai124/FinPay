package org.example;

import util.ValidationDonnees;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        int age = ValidationDonnees.validateInts("age");
        System.out.println(age);

    }
}
