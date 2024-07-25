package carsharing;

public class DbCarDao {
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



    DbCarDao(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    public void createTable() {
        dbClient.run(CREATE_TABLE);
        System.out.println("Table car created!");
    }



}
