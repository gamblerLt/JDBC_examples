package lt.code.academy.H2ConnectionExample;

import java.lang.ref.PhantomReference;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class H2TransactionExample {
    public static void main(String[] args) {
        ApplicationProperties properties = ApplicationProperties.getInstance();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(properties.getValue("jdbc.h2.connection.url"),
                    properties.getValue("jdbc.h2.connection.name"),
                    properties.getValue("jdbc.h2.connection.password"));

            connection.setAutoCommit(false); //atidarom transakcija

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE darbuotojas SET alga = ?");
            preparedStatement.setInt(1, 65000);
            preparedStatement.executeUpdate();

            throw new RuntimeException("Nepavyko atnaujinti");

            //connection.commit(); // visada rankiniu budu pakomitinam
        } catch(Exception e) {
            System.out.println(e.getMessage());
            if(connection != null) {
                try {
                    connection.rollback();
                }
                catch(SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } finally {
            if(connection != null) {
                try {
                    connection.setAutoCommit(true);
                }
                catch(SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }
}
