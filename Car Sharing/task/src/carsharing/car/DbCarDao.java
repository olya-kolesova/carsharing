package carsharing.car;

import carsharing.company.Company;
import carsharing.DbClient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class DbCarDao implements CarDao {
    DbClient<Car> dbClient;
    private List<Company> companies;

    private static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS CAR(
                ID INT IDENTITY PRIMARY KEY,
                NAME VARCHAR NOT NULL UNIQUE,
                COMPANY_ID INT NOT NULL,
                CONSTRAINT fk_company FOREIGN KEY(COMPANY_ID)
                REFERENCES COMPANY(ID)
            );
            """;


    private static final String INSERT_CAR = """
            INSERT INTO CAR (NAME, COMPANY_ID)
            VALUES (?, ?);
            """;


    private static final String SELECT_WITH_COMPANY = """
            SELECT *
            FROM CAR
            WHERE COMPANY_ID = ?
            """;

    private static final String SELECT_ALL = """
            SELECT *
            FROM CAR
            """;

    private static final String DELETE_CARS = """
            DELETE FROM CAR;
            """;



    public DbCarDao(DbClient<Car> dbClient) {
        this.dbClient = dbClient;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public void createTable() {
        dbClient.run(CREATE_TABLE);
        System.out.println("Table car created!");
    }
    @Override
    public List<Car> findByCompany(Company company) {
        return dbClient.selectCarsByCompany(SELECT_WITH_COMPANY, company);
    }
    @Override
    public List<Car> findAll(List<Company> companies) {
        this.setCompanies(companies);
        return dbClient.selectForList(SELECT_ALL, setResult);
    }
    @Override
    public void save(Car car) {
        dbClient.insertValue(INSERT_CAR, car, setValue);
    }

    public void deleteCars() {
        dbClient.run(DELETE_CARS);
    }

    public BiConsumer<PreparedStatement, Car>setValue = (x, y) -> {
        try {
            x.setString(1, y.getName());
            x.setInt(2, y.getCompany().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

    public BiConsumer<ResultSet, List<Car>> setResult = (x, y) -> {
        try {
            int index = x.getInt("ID");
            String name = x.getString("NAME");
            int companyId = x.getInt("COMPANY_ID");
            Stream<Company> stream = companies.stream();
            Optional<Company> companyOpt = stream.filter(z -> z.getId() == companyId).findFirst();
            companyOpt.ifPresentOrElse(
                    z -> y.add(new Car(index, name, z)),
                    () -> System.out.println("There is no company with this id!")
            );


        } catch (SQLException e) {
            e.printStackTrace();
        }

    };




}
