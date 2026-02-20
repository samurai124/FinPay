package App;

import Statistiques.Statistiques;
import model.Admin;
import model.Client;
import model.Prestataire;
import service.*;
import util.ValidationDonnees;

import java.util.Scanner;

import static dao.PrestataireDAO.*;

public class Main {
     static void main(String[] args){

         System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "off");
         System.setProperty("org.apache.poi.util.POILogger", "org.apache.poi.util.NullLogger");

         Menu menu = new Menu();
         menu.menuPrencipal();
    }
}
