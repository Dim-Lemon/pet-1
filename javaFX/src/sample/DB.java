package sample;

import java.sql.*;

public class DB {

    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "java_db";
    private final String LOGIN = "mysql";
    private final String PASS = "mysql";

    private Connection dbConn = null;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    public void isConnected() throws SQLException, ClassNotFoundException {
        dbConn = getDbConnection();
        System.out.println(dbConn.isValid(1000));
    }

    public boolean setArticles(String link, String abbName) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `link_db` (`link`, `abbName`) VALUES(?, ?)";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM `link_db` WHERE `abbName` = '" + abbName + "' LIMIT 1");
        if(res.next())
            return false;
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, link);
        prSt.setString(2, abbName);

        prSt.executeUpdate();
        return true;
    }

    public boolean authUser(String login, String password) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        String sql = "SELECT * FROM `users` WHERE `login` = '" + login + "' AND `password` = '" + password + "' LIMIT 1";
        ResultSet res = statement.executeQuery(sql);

        return res.next();
    }

    public ResultSet getArticles() throws SQLException, ClassNotFoundException {
        String sql = "SELECT `link`, `abbName` FROM `link_db`";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        return res;
    }


    public boolean setAbbName(String abbName) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `link_db` (`abbName`) VALUES(?)";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM `link_db` WHERE `abbName` = '" + abbName + "' LIMIT 1");
        if(res.next())
            return false;
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, abbName);

        prSt.executeUpdate();
        return true;
    }

}
