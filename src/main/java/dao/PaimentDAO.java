package dao;

import model.Facture;
import model.Paiement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.FactureDAO.getFactureById;
import static util.DBconnection.getConnection;

public class PaimentDAO {

    // Paiement
    public static int ajouterPaimentDB(Paiement paiement, int idFacture) {
        String insert = "INSERT INTO Paiement(montant, datePaiement, statut, montantCommision, idFacture) VALUES (?, ?, ?, ?, ?)";
        String update = "UPDATE facture SET status = true WHERE id = ?";
        int generatedId = -1;
        try (Connection con = getConnection();
             PreparedStatement p1 = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement p2 = con.prepareStatement(update)) {
            p1.setDouble(1, paiement.getMontant());
            p1.setTimestamp(2, Timestamp.valueOf(paiement.getDatePaiement()));
            p1.setBoolean(3, paiement.isStatut());
            p1.setDouble(4, paiement.getMontantCommision());
            p1.setInt(5, idFacture);
            p1.executeUpdate();

            try (ResultSet rs = p1.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }
            p2.setInt(1, idFacture);
            p2.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur paiement: " + e.getMessage());
        }

        return generatedId;
    }


    public static void supprimerPaimentDB(int id) {
        String requetSql = "DELETE FROM Paiement WHERE id = ?";
        try {
            Connection con = getConnection();
            PreparedStatement stmS = con.prepareStatement(requetSql);
            stmS.setInt(1, id);
            stmS.executeUpdate();
            System.out.println("Paiement supprimé avec succès");
        } catch (SQLException e) {
            System.out.println("Échec de la suppression : " + e.getMessage());
        }
    }

    public static void modifierPaimentDB(int id, String champ, String valeur) {

        String requetSql = "UPDATE Paiement SET " + champ + " = ? WHERE id = ?";

        try {
            Connection con = getConnection();
            PreparedStatement stm = con.prepareStatement(requetSql);

            stm.setString(1, valeur);
            stm.setInt(2, id);
            int rows = stm.executeUpdate();
            if (rows > 0) {
                System.out.println("Paiement mis à jour avec succès.");
            } else {
                System.out.println("Aucun paiement trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Échec de la modification : " + e.getMessage());
        }
    }

    public static List<Paiement> getPaimentDB() {
        List<Paiement> paiements = new ArrayList<>();
        String requetSQl = "SELECT * FROM Paiement";
        try {
            Connection con = getConnection();
            Statement stmq = con.createStatement();
            ResultSet res = stmq.executeQuery(requetSQl);
            while (res.next()) {
                int idF = res.getInt("idFacture");
                Facture f = getFactureById(idF);

                Paiement p = new Paiement(
                        res.getInt("id"),
                        res.getDouble("montant"),
                        res.getTimestamp("datePaiement").toLocalDateTime(),
                        res.getBoolean("statut"),
                        res.getDouble("montantCommision"),
                        f);
                paiements.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des paiements : " + e.getMessage());
        }
        return paiements;
    }

}
