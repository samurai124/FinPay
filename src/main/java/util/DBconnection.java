package util;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBconnection {

    private static String URL = "jdbc:mysql://localhost:3306/finpay";
    private static String USER = "root";
    private static String PASSWORD = "2005085";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
