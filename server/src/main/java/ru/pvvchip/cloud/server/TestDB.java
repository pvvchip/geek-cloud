package ru.pvvchip.cloud.server;

import java.sql.*;

public class TestDB {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:db_server/users.db");
        Statement stat = con.createStatement();
        ResultSet resSet = stat.executeQuery("SELECT * FROM account");
        while(resSet.next())
        {
            String  name = resSet.getString(1);
            String  pw = resSet.getString(2);
            System.out.println( "name = " + name );
            System.out.println( "pw = " + pw );
            System.out.println();
        }
        con.close();
    }
}
