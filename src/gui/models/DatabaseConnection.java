package gui.models;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {

        // GOOGLE HOST
//        String databaseName = "quizkampen-db";
//        String databaseUser = "root";
//        String databasePassword = "mysql_native_password";
//        String newURL = "jdbc:mysql://localhost:3306/quizkopia-db?serverTimezone=UTC&useSSL=false";
//        String jdbcUrl2 = String.format("jdbc:mysql://%s/%s?cloudSqlInstance=%s" +
//                        "&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false",
//                "google", "quizkampen-db", "quizkampen-db");
//        String jdbcUrl3 = String.format("jdbc:mysql://35.242.140.155:3306/%s?cloudSqlInstance=%s" +
//                        "&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false",
//                "quizkampen-db", "quizkampen-db");
//        String jdbcUrl = String.format("jdbc:mysql://google/%s?cloudSqlInstance=%s&"+"socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false",
//                "quizkampen-db",
//                "quizkampen-database:europe-west2:quizkampen-db");
//        String newNewTestURL = "jdbc:google:mysql://quizkampen-db:quizkampen-database:europe-west2:quizkampen-db/quizkampen-db?user=root;";
//        String newTestURL = "jdbc:mysql:///quizkampen-db?socketFactory=com.google.cloud.sql.mysql.SocketFactory&cloudSqlInstance=quizkampen-database:europe-west2:quizkampen-db:quizkampen-db";
//        String test = "jdbc:mysql://google:3306/quizkampen-db?cloudSqlInstance=quizkampen-database:europe-west2:quizkampen-db-instance&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=username&password=password";
//        String googleURL = "jdbc:mysql:///quizkampen-db?cloudSqlInstance=quizkampen-database:europe-west2:quizkampen-db&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=root&password=mysql_native_password";
//
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            Connection conn = DriverManager.getConnection
//                    (jdbcUrl, "root", "mysql_native_password");
//
//            Statement stmt = conn.createStatement();
//            stmt.execute("SELECT * FROM `FOO.BAR`");
//            stmt.close();
//            conn.close();
//
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }

        //LOCAL HOST
        String databaseNameLocal = "quizdb-local";
        String databaseUserLocal = "root";
        String passwordLocal = "password1234";

        String urlToOwn = "jdbc:mysql://localhost/" + databaseNameLocal;

        try {
            System.out.println("in try databaseconnection");
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(urlToOwn, databaseUserLocal, passwordLocal);
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
