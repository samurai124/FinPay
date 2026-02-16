package dao;

import model.CommissionFinPay;
import model.Facture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.ClientDAO.getClientById;
import static dao.PrestataireDAO.getPrestataireById;
import static util.DBconnection.getConnection;

public class CommissionFinPayDAO {
    // CommissionFinPay
    public static void enregistrerCommissionDB(CommissionFinPay com, int idPaiement) {

        String query = "INSERT INTO CommisionFinPay (pourcentage, montantTotal, datecommission, idPaiement) VALUES (?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(query)) {
            st.setDouble(1, com.getPourcentage());
            st.setDouble(2, com.getMontantTotal());
            st.setTimestamp(3, Timestamp.valueOf(com.getDatecommission()));
            st.setInt(4, idPaiement);
            st.executeUpdate();
            System.out.println("Commission archivée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur archive commission: " + e.getMessage());
        }
    }

    public static double calculerTotalCommssion() {
        String query = "SELECT SUM(montantTotal) FROM CommisionFinPay";
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(query);
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next())
                return rs.getDouble(1);
        } catch (SQLException e) {
            System.out.println("Erreur :" + e.getMessage());
            ;
        }
        return 0.0;
    }

    // fonction pour creer une facture
    public static List<Facture> getFacturesDB() {
        List<Facture> factures = new ArrayList<>();
        String request = "SELECT * FROM FACTURE";
        try {
            Connection connexion1 = getConnection();
            PreparedStatement statement = connexion1.prepareStatement(request);
            ResultSet res = statement.executeQuery();
            while (res.next()) {

                Facture facture = new Facture(res.getInt("id"), res.getString("numero"), res.getFloat("montant"),
                        res.getBoolean("status"), getClientById(res.getInt("idClient")),
                        getPrestataireById(res.getInt("idPrestataire")));
                factures.add(facture);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("aucune facture n'existe");
        }
        return factures;

    }

}
