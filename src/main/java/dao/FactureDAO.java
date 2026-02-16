package dao;

import model.Client;
import model.Facture;
import model.Prestataire;
import util.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.ClientDAO.getClientById;
import static dao.PrestataireDAO.getPrestataireById;
import static util.DBconnection.getConnection;

public class FactureDAO {

    // Facture ajouter facture du database fonctionnelle
    public static void ajouterFactureDB(Facture facture, int idClient, int idPrestataire) {
        String requet = "INSERT INTO facture (numero,montant, status,idClient,idPrestataire) VALUES (?,?,?,?,?)";
        try {
            Connection connection1 = getConnection();
            PreparedStatement statment = connection1.prepareStatement(requet);
            statment.setString(1, facture.getNumero());
            statment.setDouble(2, facture.getMontant());
            statment.setBoolean(3, facture.getStatut());
            statment.setInt(4, idClient);
            statment.setInt(5, idPrestataire);
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
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("facture  supprimé avec succès");

        } catch (SQLException e) {
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
                        prestataire);
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
            } else if (champ.equals("client") || champ.equals("prestataire")) {
                statement.setInt(1, Integer.parseInt(valeur));
            } else {
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

    // filtrer facture par statut
    public static List<Facture> getFacturesByStatut(boolean statut) {
        List<Facture> factures = new ArrayList<>();
        String query = "SELECT * FROM facture WHERE status = ?";

        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setBoolean(1, statut);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idClient = rs.getInt("idClient");
                int idPrestataire = rs.getInt("idPrestataire");
                Client client =getClientById(idClient);
                Prestataire prestataire = getPrestataireById(idPrestataire);
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


    public static List<Facture> getFacturesByPrestataire(int idPrestataire) {
        List<Facture> factures = new ArrayList<>();

        String query = "SELECT * FROM facture WHERE idPrestataire = ?";

        try {
            Connection conn = DBconnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idPrestataire);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Facture facture = new Facture(
                rs.getInt("id"),
                rs.getString("numero"),
                rs.getDouble("montant"),
                rs.getBoolean("statut"),
                        ClientDAO.getClientById(rs.getInt("idClient")),
                        PrestataireDAO.getPrestataireById(rs.getInt("idPrestataire"))
                );


                factures.add(facture);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return factures;
    }










}
