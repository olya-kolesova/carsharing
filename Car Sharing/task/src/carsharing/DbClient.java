package carsharing;
import carsharing.car.Car;
import carsharing.company.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class DbClient {
    static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String CONNECTION_URL = "jdbc:h2:./src/carsharing/db/carsharing";

    DbClient() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    };

    public void run(String query) {
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
            connection.setAutoCommit(true);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public Optional<Company> select(String query, int id) {
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Company company = null;
            while (resultSet.next()) {
                int index = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                company = new Company(index, name);
            }
            return Optional.of(company);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public List<Company> selectForList(String query) {
        List<Company> companies = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
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




    public void insertValue(String query, String name) {
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Car> selectCarsByCompany(String query, Company company) {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, company.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                Car car = new Car(id, name, company);
                cars.add(car);
            }
            return cars;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public void insertCar(String query, Car car) {
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, car.getName());
            preparedStatement.setInt(2, car.getCompany().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
