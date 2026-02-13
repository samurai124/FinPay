package util;

import model.Facture;
import model.Client;
import model.Prestataire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBconnection {

    private static  String URL =
            "jdbc:mysql://localhost:3306/finpay";
    private static  String USER = "root";
//    private static  String PASSWORD = "2005085";
    private static  String PASSWORD = "";

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    // Client
    public static void ajouterClientDB(Client client){
        String requet = "INSERT INTO client (nom) VALUES (?) ";
        try{
            Connection connection = getConnection();
            PreparedStatement statment = connection.prepareStatement(requet);
            statment.setString(1,client.getNom());
            statment.executeUpdate();
            System.out.println("Client inséré avec succès");

        }catch(SQLException e) {
            System.out.println("Échec de l'insertion du client");
        }
    }


    public static void supprimerClientDB(int id){
        String requet = "DELETE FROM client WHERE id = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(requet);
            statement.setInt(1,id);
            statement.executeUpdate();
            System.out.println("client supprimé avec succès");

        }catch (SQLException e){
            System.out.println("Échec de la suppression du prestataire");
        }
    }

    public static void midifierClientDB(int id, String nom, String nvNom){
        String requet = "UPDATE client SET " + nom + " = ? WHERE id = ?";
        try {
            Connection connection1 = getConnection();
            PreparedStatement statement = connection1.prepareStatement(requet);
            statement.setString(1,nvNom);
            statement.setInt(2,id);
            statement.executeUpdate();
            System.out.println("nom de client modifier avec succès");
        }catch (SQLException e){
            System.out.println("Échec de la modification");
        }
    }

    public static List<Client> getClientDB(){
        List<Client> clients = new ArrayList<>();
        String requet = "SELECT id,nom FROM client";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(requet);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                Client client = new Client(result.getInt("id"),result.getString("nom"));
                clients.add(client);
            }
        }catch (SQLException e){
            System.out.println("Aucun client trouvé dans la base de données.");
        }
        return clients;
    }

    public static Client getClientById(int id) {
        String query = "SELECT id, nom FROM client WHERE id = ?";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Client(
                        rs.getInt("id"),
                        rs.getString("nom")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }





    // prestataire
    public static void ajouterPrestatireDB(Prestataire prestataire){
        String requet = "INSERT INTO prestataire (nomEntreprise, email,solde) VALUES (?,?,?)";
        try{
            Connection connection1 = getConnection();
            PreparedStatement statment = connection1.prepareStatement(requet);
            statment.setString(1,prestataire.getNomEntreprise());
            statment.setString(2,prestataire.getEmail());
            statment.setFloat(3, prestataire.getSolde());
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
        String requet = "SELECT id,nomEntreprise,email ,solde FROM prestataire";
        try {
            Connection connection1 = getConnection();
            PreparedStatement statement = connection1.prepareStatement(requet);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                Prestataire prestataire = new Prestataire(result.getInt("id"),result.getString("nomEntreprise"),result.getString("email"),result.getFloat("solde"));
                prestataires.add(prestataire);
            }
        }catch (SQLException e){
            System.out.println("Aucun prestataire trouvé dans la base de données.");
        }
        return prestataires;
    }
    public static Prestataire getPrestataireById(int id) {
        String query = "SELECT * FROM prestataire WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Prestataire(rs.getInt("id"), rs.getString("nomEntreprise"), rs.getString("email"), rs.getFloat("solde"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // Facture
    public static void ajouterFactureDB(Facture facture,int idClient, int idPrestataire){
        String requet = "INSERT INTO facture (numero,montant, status,idClient,idPrestataire) VALUES (?,?,?,?,?)";
        try{
            Connection connection1 = getConnection();
            PreparedStatement statment = connection1.prepareStatement(requet);
            statment.setString(1,facture.getNumero());
            statment.setDouble(2,facture.getMontant());
            statment.setBoolean(3,facture.getStatut());
            statment.setInt(4,idClient);
            statment.setInt(5,idPrestataire);
            statment.executeUpdate();
            System.out.println("Facture inséré avec succès");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Échec de l'insertion du facture");
        }
    }



    public static void supprimerFactureDB(int id) {
        String requet = "DELETE FROM facture WHERE id = ?";
        try {
            Connection connection1 = getConnection();
            PreparedStatement statement = connection1.prepareStatement(requet);
            statement.setInt(1,id);;
            statement.executeUpdate();
            System.out.println("facture  supprimé avec succès");

        }catch (SQLException e){
            System.out.println("Échec de la suppression du facture");
        }
           }


        public static void modifierFactureDB () {
        }

        // Paiment
        public static void ajouterPaimentDB () {
        }

        public static void supprimerPaimentDB () {
        }

        public static void modifierPaimentDB () {
        }
//fonction pour creer une facture
        public static List<Facture> getFacturesDB(){
            List<Facture>factures=new ArrayList<>();
            String request="SELECT * FROM FACTURE";
            try {
                Connection connexion1=getConnection();
                PreparedStatement statement=connexion1.prepareStatement(request);
                ResultSet res=statement.executeQuery();
                while(res.next()){

                    Facture facture =new Facture(res.getInt("id"),res.getString("numero"),res.getFloat("montant"),res.getBoolean("status"),getClientById(res.getInt("idClient")),getPrestataireById(res.getInt("idPrestataire")));
                    factures.add(facture);
                }


            }catch(Exception e){
                System.out.println(e.getMessage());
                System.out.println("aucune facture n'existe");
            }
            return factures;

        }

        // fonction modifier Facture
    public void modifierFacture(Facture facture, int idClient, int idPrestataire, String champ){
        String requet = "UPDATE facture SET " + champ + " = ? WHERE id = ?";

        try{
        Connection connection1 = getConnection();
        PreparedStatement statment = connection1.prepareStatement(requet);
        statment.setString(1,facture.getNumero());
        statment.setDouble(2,facture.getMontant());
        statment.setBoolean(3,facture.getStatut());
        statment.setInt(4,idClient);
        statment.setInt(5,idPrestataire);
        statment.executeUpdate();
        System.out.println("Facture modifié avec succés");
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        System.out.println("Échec de modification du facture");
    }
    }
}





