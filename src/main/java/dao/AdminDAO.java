package dao;

import model.Admin;
import util.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
   public static Admin getAdminByID(int id){
       String  requet = "SELECT * FROM admin WHERE id = ?";
       try{
           Connection connection = DBconnection.getConnection();
           PreparedStatement statement = connection.prepareStatement(requet);
           statement.setInt(1,id);
           ResultSet result = statement.executeQuery();
           if (result.next()){
               return new Admin(result.getInt("id"),result.getString("nom"),result.getInt("password"));
           }

       }catch(SQLException e){
           e.printStackTrace();
       }
       return null;
   }


}
