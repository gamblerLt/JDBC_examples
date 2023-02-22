package lt.code.academy.H2ConnectionExample.poltergeist;

import lt.code.academy.H2ConnectionExample.ApplicationProperties;
import org.postgresql.plugin.AuthenticationPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostgreSQLConnectionExample {
    private final Connection connection;

    public PostgreSQLConnectionExample(Connection connection) {
        this.connection = connection;
    }

    public static void main(String[] args) {

        ApplicationProperties properties = ApplicationProperties.getInstance();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(properties.getValue("jdbc.postgresql.connection.url"),
                    properties.getValue("jdbc.postgresql.connection.name"),
                    properties.getValue("jdbc.postgresql.connection.password"));
            if (connection != null) {
                System.out.println("Prisijungeme prie postgresql DB...");
            }
            PostgreSQLConnectionExample example = new PostgreSQLConnectionExample(connection);
            example.createUser(2,"Povilas", "Arbatauskas", "37022258222", "svbiwsbdh@nomail.net");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }


    }

    private void createUser(Integer id, String name, String surname, String phone, String email) throws SQLException {
       /* PreparedStatement statement = connection.prepareStatement("INSERT INTO \"User\" (\"ID\", \"NAME\", \"SURNAME\", \"PHONE\", \"EMAIL\") VALUES (?, ?, ?, ?, ?)");
        statement.setInt(1, id);
        statement.setString(2, name);
        statement.setString(3, surname);
        statement.setString(4, phone);
        statement.setString(5, email);

        statement.executeUpdate();
        statement.close();*/

        new PrepareStatementWrapper(connection)
                .create("INSERT INTO \"User\" (\"ID\", \"NAME\", \"SURNAME\", \"PHONE\", \"EMAIL\") VALUES (?, ?, ?, ?, ?)")
        .setInt(1, id)
        .setString(2, name)
        .setString(3, surname)
        .setString(4, phone)
        .setString(5, email)
                .execute();
    }
}