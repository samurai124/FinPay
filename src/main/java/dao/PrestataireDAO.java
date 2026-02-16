package dao;

import model.Prestataire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.DBconnection.getConnection;

public class PrestataireDAO {

    // prestataire
    public static void ajouterPrestatireDB(Prestataire prestataire) {
        String requet = "INSERT INTO prestataire (nomEntreprise, email,solde) VALUES (?,?,?)";
        try {
            Connection connection1 = getConnection();
            PreparedStatement statment = connection1.prepareStatement(requet);
            statment.setString(1, prestataire.getNomEntreprise());
            statment.setString(2, prestataire.getEmail());
            statment.setFloat(3, prestataire.getSolde());
            statment.executeUpdate();
            System.out.println("Prestataire inséré avec succès");
        } catch (SQLException e) {
            System.out.println("Échec de l'insertion du prestataire");
        }
    }

    public static void supprimerPrestatireDB(int id) {
        String requet = "DELETE FROM prestataire WHERE id = ?";
        try {
            Connection connection1 = getConnection();
            PreparedStatement statement = connection1.prepareStatement(requet);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("prestatair supprimé avec succès");

        } catch (SQLException e) {
            System.out.println("Échec de la suppression du prestataire");
        }
    }

    public static void modifierPrestatireDB(int id, String champ, String valeur) {
        String requet = "UPDATE prestataire SET " + champ + " = ? WHERE id = ?";
        try {
            Connection connection1 = getConnection();
            PreparedStatement statement = connection1.prepareStatement(requet);
            statement.setString(1, valeur);
            statement.setInt(2, id);
            statement.executeUpdate();
            System.out.println("Prestataire modifier avec succès");
        } catch (SQLException e) {
            System.out.println("Échec de la modification");
        }
    }

    public static List<Prestataire> getPreatataireDB() {
        List<Prestataire> prestataires = new ArrayList<>();
        String requet = "SELECT id,nomEntreprise,email ,solde FROM prestataire";
        try {
            Connection connection1 = getConnection();
            PreparedStatement statement = connection1.prepareStatement(requet);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Prestataire prestataire = new Prestataire(result.getInt("id"), result.getString("nomEntreprise"),
                        result.getString("email"), result.getFloat("solde"));
                prestataires.add(prestataire);
            }
        } catch (SQLException e) {
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
                Prestataire p = new Prestataire(rs.getInt("id"), rs.getString("nomEntreprise"), rs.getString("email"),
                        rs.getFloat("solde"));
                rs.close();
                stmt.close();
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
