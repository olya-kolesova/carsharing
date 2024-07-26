package carsharing.company;

import carsharing.DbClient;

import java.util.List;
import java.util.Optional;

public class DbCompanyDao implements CompanyDao {
    private final DbClient dbClient;

    private static final String CREATE_DB = """
            CREATE TABLE IF NOT EXISTS COMPANY(
                ID INT IDENTITY PRIMARY KEY,
                NAME VARCHAR UNIQUE NOT NULL
            );
            """;

    private static final String DROP_TABLE = """
            DROP TABLE COMPANY;""";

    private static final String SET_ID_NOT_NULL = """
            ALTER TABLE COMPANY
            ALTER COLUMN ID
            SET NOT NULL;
            """;


    private static final String UPDATE_ID_COLUMN = """
            ALTER TABLE COMPANY
            ALTER COLUMN ID INT GENERATED ALWAYS AS IDENTITY
            START WITH 1;
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

    private static final String DELETE_ALL = """
            DELETE FROM COMPANY;
            """;

    private static final String RESTART_ID = """
            ALTER TABLE COMPANY ALTER COLUMN ID RESTART WITH 1;
            """;

    private static final String SELECT_BY_ID = """
            SELECT *
            FROM COMPANY
            WHERE ID = ?
            """;

    public DbCompanyDao(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public List<Company> findAll() {
        List<Company> companies = dbClient.selectForList(SELECT_ALL);
        return companies;
    }

    @Override
    public Optional<Company> findById(int id) {
        return dbClient.select(SELECT_BY_ID, id);
    }

    @Override
    public void save(Company company) {
        dbClient.insertValue(INSERT, company.getName());
    }

    public void updateColumnId() {
        dbClient.run(SET_ID_NOT_NULL);
        dbClient.run(UPDATE_ID_COLUMN);
    }

    public void updateColumnName() {
        dbClient.run(ADD_NOT_NULL_NAME);
        dbClient.run(ADD_UNIQUE_NAME);
    }

    public void deleteAll() {
        dbClient.run(DELETE_ALL);
    }

    public void restartId() {
        dbClient.run(RESTART_ID);
    }

    public void dropTable() {
        dbClient.run(DROP_TABLE);
    }

    public void createDb() {
        dbClient.run(CREATE_DB);
    }

}
