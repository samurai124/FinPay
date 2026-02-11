package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

    private static  String URL =
            "jdbc:mysql://localhost:3306/finpay";
    private static  String USER = "root";
    private static  String PASSWORD = "";

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion etablie");
        }
        return connection;
    }

    // Client
    public static void ajouterClientDB(){}

    public static void supprimerClientDB(){}

    public static void midifierClientDB(){}

    // prestataire
    public static void ajouterPrestatireDB(){}

    public static void supprimerPrestatireDB(){}

    public static void modifierPrestatireDB(){}

    // Facture
    public static void ajouterFactureDB(){}

    public static void supprimerFactureDB(){}

    public static void modifierFactureDB(){}

    // Paiment
    public static void ajouterPaimentDB(){}

    public static void supprimerPaimentDB(){}

    public static void modifierPaimentDB(){}









}
