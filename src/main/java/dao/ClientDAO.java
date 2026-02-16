package dao;

import model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.DBconnection.getConnection;

public class ClientDAO {
    public static void ajouterClientDB(Client client) {
        String requet = "INSERT INTO client (nom) VALUES (?) ";
        try {
            Connection connection = getConnection();
            PreparedStatement statment = connection.prepareStatement(requet);
            statment.setString(1, client.getNom());
            statment.executeUpdate();
            System.out.println("Client inséré avec succès");

        } catch (SQLException e) {
            System.out.println("Échec de l'insertion du client");
        }
    }

    public static void supprimerClientDB(int id) {
        String requet = "DELETE FROM client WHERE id = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(requet);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("client supprimé avec succès");

        } catch (SQLException e) {
            System.out.println("Échec de la suppression du prestataire");
        }
    }

    public static void midifierClientDB(int id, String nom, String nvNom) {
        String requet = "UPDATE client SET " + nom + " = ? WHERE id = ?";
        try {
            Connection connection1 = getConnection();
            PreparedStatement statement = connection1.prepareStatement(requet);
            statement.setString(1, nvNom);
            statement.setInt(2, id);
            statement.executeUpdate();
            System.out.println("nom de client modifier avec succès");
        } catch (SQLException e) {
            System.out.println("Échec de la modification");
        }
    }

    public static List<Client> getClientDB() {
        List<Client> clients = new ArrayList<>();
        String requet = "SELECT id,nom FROM client";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(requet);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Client client = new Client(result.getInt("id"), result.getString("nom"));
                clients.add(client);
            }
        } catch (SQLException e) {
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

}
