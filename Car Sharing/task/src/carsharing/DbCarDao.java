package carsharing;

import java.util.List;

public class DbCarDao implements CarDao {
    DbClient dbClient;

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

    private static final String DELETE_CARS = """
            DELETE FROM CAR;
            """;



    DbCarDao(DbClient dbClient) {
        this.dbClient = dbClient;
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
    public void save(Car car) {
        dbClient.insertCar(INSERT_CAR, car);
    }

    public void deleteCars() {
        dbClient.run(DELETE_CARS);
    }


}
