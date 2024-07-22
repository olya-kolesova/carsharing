package carsharing;

import java.util.List;

public class DbCompanyDao implements CompanyDao {
    private static final String CONNECTION_URL = "jdbc:h2:./src/carsharing/db/carsharing";
    private final DbClient dbClient;


    private static final String CREATE_DB = """
            CREATE TABLE IF NOT EXISTS COMPANY(
                ID INT IDENTITY PRIMARY KEY,
                NAME VARCHAR UNIQUE NOT NULL
            );
            """;

    private static final String SET_ID_NOT_NULL = """
            ALTER TABLE COMPANY
            ALTER COLUMN ID
            SET NOT NULL;
            """;


    private static final String UPDATE_ID_COLUMN = """
            ALTER TABLE COMPANY
            ALTER COLUMN ID INT GENERATED ALWAYS AS IDENTITY;
            """;

    private static final String ADD_NOT_NULL_NAME = """
            ALTER TABLE COMPANY
            ALTER COLUMN NAME SET NOT NULL;
            """;

    private static final String ADD_UNIQUE_NAME = """
            ALTER TABLE COMPANY
            ADD UNIQUE (NAME);
            """;


    private static final String SELECT_ALL = """
            SELECT *
            FROM COMPANY;
            """;

    private static final String INSERT = """
            INSERT INTO COMPANY (NAME)
            VALUES (?)
            """;


    DbCompanyDao(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public List<Company> findAll() {
        List<Company> companies = dbClient.selectForList(SELECT_ALL);
        System.out.println(companies.size());
        for (Company company : companies) {
            System.out.println(company.getId());
            System.out.println(company.getName());
        }

        return companies;
    }

    public void save(Company company) {
        System.out.println(dbClient.insertValue(INSERT, company.getName()));
    }

    public void updateColumnId() {
        dbClient.run(SET_ID_NOT_NULL);
        dbClient.run(UPDATE_ID_COLUMN);
    }

    public void updateColumnName() {
        dbClient.run(ADD_NOT_NULL_NAME);
        dbClient.run(ADD_UNIQUE_NAME);
    }



}
