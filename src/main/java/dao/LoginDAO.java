package dao;
import model.Admin;
import model.Client;
import model.Prestataire;

public class LoginDAO {

    public static Client clientLogInDB(int id){
       Client client =   ClientDAO.getClientById(id);
       return client;
    }

    public static Admin adminLogInDB(int id){
        Admin admin = AdminDAO.getAdminByID(id);
        return admin;
    }

    public static Prestataire prestataireLogInDB(int id){
        Prestataire prestataire = PrestataireDAO.getPrestataireById(id);
        return prestataire;
    }


}
