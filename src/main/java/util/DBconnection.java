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
    private static  String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
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
                return new Client(rs.getInt("id"), rs.getString("nom"));
            }
            rs.close();
            stmt.close();
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
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Prestataire p = new Prestataire(rs.getInt("id"), rs.getString("nomEntreprise"), rs.getString("email"), rs.getFloat("solde"));
                rs.close();
                stmt.close();
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Facture ajouter facture du database fonctionnelle
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
            statement.setInt(1,id);
            statement.executeUpdate();
            System.out.println("facture  supprimé avec succès");

        }catch (SQLException e){
            System.out.println("Échec de la suppression du facture");
        }
           }


    public static Facture getFactureById(int id) {
        String query = "SELECT * FROM facture WHERE id = ?";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Client client = getClientById(rs.getInt("idClient"));
                Prestataire prestataire = getPrestataireById(rs.getInt("idPrestataire"));

                return new Facture(
                        rs.getInt("id"),
                        rs.getString("numero"),
                        rs.getFloat("montant"),
                        rs.getBoolean("status"),
                        client,
                        prestataire
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // fonction modifier Facture
    public static void modifierFactureDB(int id, String champ, String valeur) {
        String requet = "UPDATE facture SET " + champ + " = ? WHERE id = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(requet);

            if (champ.equals("montant")) {
                statement.setFloat(1, Float.parseFloat(valeur));
            } else if (champ.equals("status")) {
                statement.setBoolean(1, Boolean.parseBoolean(valeur));
            } else if(champ.equals("client") || champ.equals("prestataire")){
                statement.setInt(1,Integer.parseInt(valeur));
            }
            else {
                statement.setString(1, valeur);
            }

            statement.setInt(2, id);
            statement.executeUpdate();
            System.out.println("Facture mise à jour avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Erreur: Format de nombre invalide pour le montant.");
        }
    }

    //filtrer facture par statut
    public static List<Facture> getFacturesByStatut(boolean statut) {
        List<Facture> factures = new ArrayList<>();
        String query = "SELECT * FROM facture WHERE status = ?";

        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setBoolean(1, statut);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idClient=rs.getInt("idClient");
                int idPrestataire = rs.getInt("idPrestataire");
                Client client =DBconnection.getClientById(idClient);
                Prestataire prestataire =DBconnection.getPrestataireById(idPrestataire);
                Facture f = new Facture(
                        rs.getInt("id"),
                        rs.getString("numero"),
                        rs.getDouble("montant"),
                        rs.getBoolean("status"),
                        client,
                        prestataire

                );
                factures.add(f);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return factures;
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

}





