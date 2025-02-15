package carsharing.customer;

import carsharing.DbClient;
import carsharing.car.Car;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class DbCustomerDao implements CustomerDAO{
    private final DbClient<Customer> dbClient;
    private List<Car> cars;


    public DbCustomerDao(DbClient<Customer> dbClient) {
        this.dbClient = dbClient;
    }

    private final static String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS CUSTOMER(
                ID INT IDENTITY PRIMARY KEY,
                NAME VARCHAR NOT NULL UNIQUE,
                RENTED_CAR_ID INT NULL,
                CONSTRAINT fk_car FOREIGN KEY(RENTED_CAR_ID)
                REFERENCES CAR(ID)
                ON DELETE SET NULL
                ON UPDATE CASCADE
            );
            """;

    private final static String SELECT_ALL = """
            SELECT *
            FROM CUSTOMER
            """;

    private final static String SELECT_CUSTOMER = """
            SELECT *
            FROM CUSTOMER
            WHERE ID = ?
            """;


    private final static String INSERT_CUSTOMER = """
        INSERT INTO CUSTOMER (NAME)
        VALUES (?);
        """;

    private final static String UPDATE_CAR = """
        UPDATE CUSTOMER
        SET RENTED_CAR_ID = ?
        WHERE ID = ?
    """;

    private static final String DELETE_ALL = """
        DELETE FROM CUSTOMER;
        """;

    private static final String DROP_CUSTOMER = """
        DROP TABLE CUSTOMER;
        """;


    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void createTable() {
        dbClient.run(CREATE_TABLE);
    }

    @Override
    public void save(Customer customer) {
        dbClient.insertValue(INSERT_CUSTOMER, customer, setValue);
    }

    @Override
    public List<Customer>findAll(List<Car> cars) {
        setCars(cars);
        return dbClient.selectForList(SELECT_ALL, setResultList);
    }
    @Override
    public Optional<Customer> findById(int id) {
        return dbClient.select(SELECT_CUSTOMER, id, setResult);
    }

    @Override
    public void update(Customer customer) {
        dbClient.insertValue(UPDATE_CAR, customer, setCar);
    }

    public void updateNull(Customer customer) {
        dbClient.insertValue(UPDATE_CAR, customer, setNull);
    }

    public void deleteAll() {
        dbClient.run(DELETE_ALL);
    }

    public void dropCustomer() {
        dbClient.run(DROP_CUSTOMER);
    }

    public BiConsumer<PreparedStatement, Customer> setValue = (x, y) -> {
        try {
            x.setString(1, y.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

    public BiConsumer<PreparedStatement, Customer> setCar = (x, y) -> {
        try {
            x.setInt(1, y.getCar().getId());
            x.setInt(2, y.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

    public BiConsumer<PreparedStatement, Customer> setNull = (x, y) -> {
        try {
            x.setObject(1, null);
            x.setInt(2, y.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    };


    public Function<ResultSet, Customer> setResult = x -> {
        try {
            int index = x.getInt("ID");
            String name = x.getString("NAME");
            int carId = x.getInt("RENTED_CAR_ID");
            Customer customer = new Customer(index, name, null);

            if (carId != 0) {
                Optional<Car> carOpt = cars.stream().filter(y -> y.getId() == carId).findFirst();
                carOpt.ifPresentOrElse(
                    z -> customer.setCar(z),
                    () -> System.out.println("There is no car with this id!")
                );
            }
            return customer;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    };

    public BiConsumer<ResultSet, List<Customer>> setResultList = (x, y) -> {
        try {
            int index = x.getInt("ID");
            String name = x.getString("NAME");
            int indexCar = x.getInt("RENTED_CAR_ID");

            if (indexCar != 0) {
                Optional<Car> carOpt = cars.stream().filter(z -> z.getId() == indexCar).findFirst();
                carOpt.ifPresentOrElse(
                    t -> y.add(new Customer(index, name, t)),
                    () ->  System.out.println("There is no car with such index!")
                );
            } else {
                y.add(new Customer(index, name, null));
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

}
