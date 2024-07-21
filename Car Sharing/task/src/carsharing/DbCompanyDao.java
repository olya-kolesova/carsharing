package carsharing;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class DbCompanyDao implements CompanyDao {
    private static final String CONNECTION_URL = "jdbc:h2:./src/carsharing/db/carsharing";
    private final DbClient dbClient;


    private static final String CREATE_DB = """
            CREATE TABLE IF NOT EXISTS COMPANY(
                ID INT PRIMARY KEY,
                NAME VARCHAR
            );
            """;

    private static final String UPDATE_ID_COLUMN = """
            ALTER TABLE COMPANY
            MODIFY COLUMN ID INT AUTO_INCREMENT PRIMARY_KEY;
            """;

    private static final String ADD_NOT_NULL_NAME = """
            ALTER TABLE COMPANY
            MODIFY COLUMN NAME VARCHAR NOT NULL;
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


}
