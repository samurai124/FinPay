package dao;

import model.Client;
import model.Facture;
import model.FacturePrestataire;
import model.Prestataire;
import service.FacturePDF;
import util.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.ClientDAO.getClientById;
import static dao.PrestataireDAO.getPrestataireById;
import static util.DBconnection.getConnection;

public class FactureDAO {
    public static void ajouterFactureDB(Facture facture, int idClient, int idPrestataire) {
        String requet = "INSERT INTO facture (numero, montant, status, idClient, idPrestataire, date) VALUES (?,?,?,?,?,?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(requet)) {

            statement.setString(1, facture.getNumero());
            statement.setDouble(2, facture.getMontant());
            statement.setBoolean(3, facture.getStatut());
            statement.setInt(4, idClient);
            statement.setInt(5, idPrestataire);
            statement.setTimestamp(6, Timestamp.valueOf(facture.getDate()));

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Facture insérée avec succès dans la base de données.");

                model.Client fullClient = ClientDAO.getClientById(idClient);
                model.Prestataire fullPrestataire = PrestataireDAO.getPrestataireById(idPrestataire);

                facture.setClient(fullClient);
                facture.setPrestataire(fullPrestataire);

                FacturePDF.facturepdf(facture);
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL: " + e.getMessage());
        }
    }
    public static void supprimerFactureDB(int id) {
        String requet = "DELETE FROM facture WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(requet)) {

            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Facture supprimée avec succès");
        } catch (SQLException e) {
            System.out.println("Échec de la suppression de la facture");
        }
    }

    public static Facture getFactureById(int id) {
        String query = "SELECT * FROM facture WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Client client = getClientById(rs.getInt("idClient"));
                    Prestataire prestataire = getPrestataireById(rs.getInt("idPrestataire"));

                    return new Facture(
                            rs.getInt("id"),
                            rs.getString("numero"),
                            rs.getFloat("montant"),
                            rs.getBoolean("status"),
                            client,
                            prestataire,
                            rs.getTimestamp("date").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void modifierFactureDB(int id, String champ, String valeur) {
        String column = champ;
        if (champ.equals("client")) column = "idClient";
        if (champ.equals("prestataire")) column = "idPrestataire";
        if (champ.equals("status")) column = "status";

        String requet = "UPDATE facture SET " + column + " = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(requet)) {

            if (column.equals("montant")) {
                statement.setFloat(1, Float.parseFloat(valeur));
            } else if (column.equals("status")) {
                statement.setBoolean(1, Boolean.parseBoolean(valeur));
            } else if (column.equals("idClient") || column.equals("idPrestataire")) {
                statement.setInt(1, Integer.parseInt(valeur));
            } else {
                statement.setString(1, valeur);
            }

            statement.setInt(2, id);
            statement.executeUpdate();
            System.out.println("Facture mise à jour avec succès !");
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Erreur lors de la modification: " + e.getMessage());
        }
    }

    public static List<Facture> getFacturesByStatut(boolean statut) {
        List<Facture> factures = new ArrayList<>();
        String query = "SELECT * FROM facture WHERE status = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBoolean(1, statut);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Client client = getClientById(rs.getInt("idClient"));
                    Prestataire prestataire = getPrestataireById(rs.getInt("idPrestataire"));

                    Facture f = new Facture(
                            rs.getInt("id"),
                            rs.getString("numero"),
                            rs.getDouble("montant"),
                            rs.getBoolean("status"),
                            client,
                            prestataire,
                            rs.getTimestamp("date").toLocalDateTime()
                    );
                    factures.add(f);
                }
            }
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
                        rs.getBoolean("status"),
                        ClientDAO.getClientById(rs.getInt("idClient")),
                        PrestataireDAO.getPrestataireById(rs.getInt("idPrestataire")),
                        rs.getTimestamp("date").toLocalDateTime()
                );

                factures.add(facture);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return factures;
    }

    public static void updateFactureStatus(int idFacture, boolean status) {
        String query = "UPDATE facture SET status = ? WHERE id = ?";

        try (Connection conn = getConnection(); // Utilise ta méthode de connexion habituelle
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setBoolean(1, status);
            statement.setInt(2, idFacture); // On lie l'ID de la facture ici

            statement.executeUpdate(); // <-- Indispensable pour appliquer le changement

        } catch (SQLException e) {
            System.out.println("Erreur mise à jour facture: " + e.getMessage());
        }
    }

    public static List<FacturePrestataire> totalFacturesChaquePresatataire() {
        List<FacturePrestataire> factures = new ArrayList<>();
        String query = "SELECT p.id ,Count(p.id) as'nombreFacture' ,SUM(f.montant) as 'sommeMontant'\n" +
                "FROM FACTURE f\n" +
                "JOIN Prestataire p  ON  f.idPrestataire=p.id\n" +
                "GROUP BY p.id;";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idPrestataire = rs.getInt("id");
                int nombreFactures = rs.getInt("nombreFacture");
                double sommeMontant = rs.getDouble("sommeMontant");
                factures.add(new FacturePrestataire(idPrestataire,nombreFactures,sommeMontant));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return factures;
    }
}