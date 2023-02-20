package lt.code.academy.H2ConnectionExample;

import java.sql.*;

public class H2ConnectionExample {
    private static final String URL = "jdbc:h2:~/paskaita";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws SQLException {
        //1. sukurti rysi -> butina mavene nurodyti dependencio draiverius
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        if (connection == null) {
            System.out.println("Not connected!");
            return;
        }
        System.out.println("Connected!!!");

        //2. sukurti statementa
        Statement statement = connection.createStatement();

        //3. execute statement

        //statement.executeQuery naudojamas selektui
        //statement.execute -> delete
        //statement.executeUpdate --> atnaujinimui

        ResultSet resultSet = statement.executeQuery("SELECT * FROM DARBUOTOJAS");

        H2ConnectionExample example = new H2ConnectionExample();
        example.printEmployers(resultSet);

        //galima gauti databese info
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tablesResultSet = metaData.getTables(null, "PUBLIC", null, null);
        System.out.println("Visos lenteles, esancios duomenu bazeje:");
        while (tablesResultSet.next()) {
            // System.out.println(tablesResultSet.getString(1));
            // System.out.println(tablesResultSet.getString(2));
            System.out.println(tablesResultSet.getString(3));
        }

        //SELCT WITH FILTERS
        System.out.println("\nIssfiltruoti useriai:");
       // resultSet = statement.executeQuery("SELECT * FROM DARBUOTOJAS  WHERE PROJEKTAS_ID = 1 AND ALGA >= 3200");
        int id = 1;
        int sum = 300;
       PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM DARBUOTOJAS  WHERE PROJEKTAS_ID = ? AND ALGA >= ?");
       preparedStatement.setInt(1, id);
       preparedStatement.setInt(2, sum);
       resultSet = preparedStatement.executeQuery();

        example.printEmployers(resultSet);

    }

    private void printEmployers(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            //pagal stulpelio pavadinima:
           /* System.out.println(resultSet.getLong("asmenskodas") + " " +
                    resultSet.getString("vardas") + " "
                            +  resultSet.getString("pavarde"));*/

            //Pagal stulpelio indeksa:
            System.out.println(resultSet.getLong(1) + " " +
                    resultSet.getString(2) + " " +
                    resultSet.getString(3));
        }
    }
}
