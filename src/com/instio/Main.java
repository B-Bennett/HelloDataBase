package com.instio;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {
        testPostgres();

    }

    static void testH2() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");//created a data base

        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS players (name VARCHAR, health DECIMAL, score INT, is_alive BOOLEAN)");
        stmt.execute("INSERT INTO players VALUES ('Alice', 100, 10, true)");
        stmt.execute("INSERT INTO players VALUES ('Bob', 100, 10, true)");
        stmt.execute("UPDATE players SET health = 50 WHERE name = 'Alice'");
        stmt.execute("DELETE FROM players WHERE name = 'Bob'");

        //Bad way
        //String input = "Charlie";
        //stmt.execute(String.format("INSERT INTO players VALUES('%s', 100, 10, true)", input ));
        // Good way
        String input = "Charlie";
        PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO players VALUES (?, 100, 10, true)");
        stmt2.setString(1, input);
        stmt2.execute();

        ResultSet results = stmt.executeQuery("SELECT * FROM players");
        while (results.next()) {
            String name = results.getString("name");
            double health = results.getDouble("health");
            int score = results.getInt("score");
            boolean isAlive = results.getBoolean("is_alive");
            System.out.println(String.format("%s %s %s %s", name, health, score, isAlive));
        }

        stmt.execute("DROP TABLE players");
        conn.close();

    }

    static void testPostgres() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hellodb");//created a data base

        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS players (name VARCHAR, health DECIMAL, score INT, is_alive BOOLEAN)");
        stmt.execute("INSERT INTO players VALUES ('Alice', 100, 10, true)");
        stmt.execute("INSERT INTO players VALUES ('Bob', 100, 10, true)");
        stmt.execute("UPDATE players SET health = 50 WHERE name = 'Alice'");
        stmt.execute("DELETE FROM players WHERE name = 'Bob'");

        //Bad way
        //String input = "Charlie";
        //stmt.execute(String.format("INSERT INTO players VALUES('%s', 100, 10, true)", input ));
        // Good way
        String input = "Charlie";
        PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO players VALUES (?, 100, 10, true)");
        stmt2.setString(1, input);
        stmt2.execute();

        ResultSet results = stmt.executeQuery("SELECT * FROM players");
        while (results.next()) {
            String name = results.getString("name");
            double health = results.getDouble("health");
            int score = results.getInt("score");
            boolean isAlive = results.getBoolean("is_alive");
            System.out.println(String.format("%s %s %s %s", name, health, score, isAlive));
        }

        stmt.execute("DROP TABLE players");
        conn.close();

    }
}
