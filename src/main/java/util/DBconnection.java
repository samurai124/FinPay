package util;

import model.Prestataire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBconnection {

    private static  String URL =
            "jdbc:mysql://localhost:3306/finpay";
    private static  String USER = "root";
    private static  String PASSWORD = "";

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    // Client
    public static void ajouterClientDB(){}

    public static void supprimerClientDB(){}

    public static void midifierClientDB(){}

    // prestataire
    public static void ajouterPrestatireDB(Prestataire prestataire){
        String requet = "INSERT INTO prestataire (nomEntreprise, email) VALUES (?,?)";
        try{
            Connection connection1 = getConnection();
            PreparedStatement statment = connection1.prepareStatement(requet);
            statment.setString(1,prestataire.getNomEntreprise());
            statment.setString(2,prestataire.getEmail());
            statment.executeUpdate();
            System.out.println("Prestataire inséré avec succès");

        } catch (SQLException e) {
            System.out.println("Échec de l'insertion du prestataire");
        }
    }

    public static void supprimerPrestatireDB(int id){
        String requet = "DELETE FROM prestataire WHERE id = ?";
        try {
            Connection connection1 = getConnection();
            PreparedStatement statement = connection1.prepareStatement(requet);
            statement.setInt(1,id);
            statement.executeUpdate();
            System.out.println("prestatair supprimé avec succès");

        }catch (SQLException e){
            System.out.println("Échec de la suppression du prestataire");
        }
    }

    public static void modifierPrestatireDB(int id,String champ,String valeur){
        String requet = "UPDATE prestataire SET " + champ + " = ? WHERE id = ?";
        try {
            Connection connection1 = getConnection();
            PreparedStatement statement = connection1.prepareStatement(requet);
            statement.setString(1,valeur);
            statement.setInt(2,id);
            statement.executeUpdate();
            System.out.println("Prestataire modifier avec succès");
        }catch (SQLException e){
            System.out.println("Échec de la modification");
        }
    }

    public static List<Prestataire> getPreatataireDB(){
        List<Prestataire> prestataires = new ArrayList<>();
        String requet = "SELECT id,nomEntreprise,email FROM prestataire";
        try {
            Connection connection1 = getConnection();
            PreparedStatement statement = connection1.prepareStatement(requet);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                Prestataire prestataire = new Prestataire(result.getInt("id"),result.getString("nomEntreprise"),result.getString("email"));
                prestataires.add(prestataire);
            }
        }catch (SQLException e){
            System.out.println("Aucun prestataire trouvé dans la base de données.");
        }
        return prestataires;
    }

    // Facture
    public static void ajouterFactureDB(){}

    public static void supprimerFactureDB(){}

    public static void modifierFactureDB(){}

    // Paiment
    public static void ajouterPaimentDB(){}

    public static void supprimerPaimentDB(){}

    public static void modifierPaimentDB(){}









}
