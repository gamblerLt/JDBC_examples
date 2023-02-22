package lt.code.academy.H2ConnectionExample.poltergeist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrepareStatementWrapper {
    private final Connection connection;
    private PreparedStatement preparedStatement;

    public PrepareStatementWrapper(Connection connection) {
        this.connection = connection;
    }

    public PrepareStatementWrapper create(String sql) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);

        return this;
    }

    public PrepareStatementWrapper setString(int index, String value) throws SQLException {
        preparedStatement.setString(index, value);

        return this;
    }

    public void execute() throws SQLException {
        preparedStatement.execute();
        preparedStatement.close();
    }

    public PrepareStatementWrapper setInt(int i, Integer id) throws SQLException {
        preparedStatement.setInt(i,id);
        return this;
    }
}