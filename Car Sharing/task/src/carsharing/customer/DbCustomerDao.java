package carsharing.customer;

import carsharing.DbClient;

public class DbCustomerDao {
    private final DbClient dbClient;

    public DbCustomerDao(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    private final static String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS CUSTOMER(
                ID INT IDENTITY PRIMARY KEY,
                NAME VARCHAR NOT NULL UNIQUE,
                RENTED_CAR_ID INT NULL,
                CONSTRAINT fk_car FOREIGN KEY(RENTED_CAR_ID)
                REFERENCES CAR(ID)
            );
            """;

    private final static String SELECT_ALL = """
            SELECT * 
            FROM CUSTOMER
            """;


    public void createTable() {
        dbClient.run(CREATE_TABLE);
    }

}
