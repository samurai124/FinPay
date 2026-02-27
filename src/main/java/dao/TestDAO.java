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

public class TestDAO {
    public List<Facture> getFacturesByPrestataire(int idPrestataire) {
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
    public void updateFactureStatus(int idFacture) {
        String query = "UPDATE facture SET status = true WHERE id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, idFacture);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Facture getFactureById(int id) {
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
}
