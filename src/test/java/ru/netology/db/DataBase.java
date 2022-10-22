package ru.netology.db;

import lombok.SneakyThrows;

import java.sql.DriverManager;

public class DataBase {

    @SneakyThrows
    public void cleanDataBase() {
        var conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");

        conn.prepareStatement("DELETE FROM card_transactions").execute();
        conn.prepareStatement("DELETE FROM auth_codes").execute();
        conn.prepareStatement("DELETE FROM cards").execute();
        conn.prepareStatement("DELETE FROM users").execute();
    }

    @SneakyThrows
    public static String getCorrectVerificationCode() {
        var codesSQL = "SELECT code FROM auth_codes WHERE created is not null;";

        var conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");

        var countStmt = conn.createStatement();
        var result = countStmt.executeQuery(codesSQL);

        if (result.next()) {
            return result.getString("code");
        }
        return null;
    }
}