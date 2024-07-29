package carsharing.company;

import carsharing.DbClient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class DbCompanyDao implements CompanyDao {
    private final DbClient<Company> dbClient;

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

    public DbCompanyDao(DbClient<Company> dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public List<Company> findAll() {
        return dbClient.selectForList(SELECT_ALL, setResultList);
    }

    @Override
    public Optional<Company> findById(int id) {
        if (dbClient.select(SELECT_BY_ID, id, setResult).isPresent()) {
            return Optional.of(dbClient.select(SELECT_BY_ID, id, setResult).get());
        } else {
            return Optional.empty();
        }

    }

    @Override
    public void save(Company company) {
        dbClient.insertValue(INSERT, company, setValue);
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


    public Function<ResultSet, Company> setResult = x -> {
        try {
            int index = x.getInt("ID");
            String name = x.getString("NAME");
            return new Company(index, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    };

    public BiConsumer<ResultSet, List<Company>> setResultList = (x, y) -> {
        try {
            int index = x.getInt("ID");
            String name = x.getString("NAME");
            y.add(new Company(index, name));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };


    public BiConsumer<PreparedStatement, Company> setValue = (x, y) -> {
        try {
            x.setString(1, y.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

}
