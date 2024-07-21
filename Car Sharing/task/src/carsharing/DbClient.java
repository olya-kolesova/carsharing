package carsharing;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DbClient {
    private final DataSource dataSource;

    DbClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void run(String query) {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



//    public Company select(String query, ) {
//
//    }


    public List<Company> selectForList(String query) {
        List<Company> companies = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                companies.add(new Company(id, name));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }




}
