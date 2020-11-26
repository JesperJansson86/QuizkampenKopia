package GUI.models;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        String databaseName = "quizkampen_db_new";
        String databaseUser = "root";
        String databasePassword = "password1234";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            System.out.println("in try databaseconnection");
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
            System.out.println("Success connection");
            System.out.println(databaseLink);
            return databaseLink;
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();

        }

        return null;
    }
}
