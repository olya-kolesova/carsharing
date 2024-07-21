package carsharing;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    static final String JDBC_DRIVER = "org.h2.Driver";

    static final String path = System.getProperty("user.dir");


    public static void main(String[] args) {
//        Connection conn = null;
//        Statement stmt = null;
//        File pathToDb = new File("./src/carsharing/db/carsharing");
//        try {
//            if (pathToDb.createNewFile()) {
//                Class.forName(JDBC_DRIVER);
//                System.out.println("Connecting to database...");
//                conn = DriverManager.getConnection("jdbc:h2:./src/carsharing/db/carsharing");
//                conn.setAutoCommit(true);
//                System.out.println("Creating table in the database");
//                stmt = conn.createStatement();
//
//                String sql = "CREATE TABLE COMPANY " +
//                        "(ID INTEGER not NULL, " +
//                        "NAME VARCHAR, " +
//                        "PRIMARY KEY (ID))";
////            String sql = "DROP TABLE COMPANY";
//                stmt.executeUpdate(sql);
//
//                System.out.println("Created table in the database...");
//                stmt.close();
//                conn.close();
//            } else {
//                System.out.println("The db was not created!");
//            }
//
//        } catch (SQLException se) {
//            se.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (stmt != null) {
//                    stmt.close();
//                }
//            } catch (SQLException ignored) {
//            }
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException se3) {
//                se3.printStackTrace();
//            }
//        }
//        System.out.println("Good Bye!");

        DbClient dbClient = new DbClient();

        DbCompanyDao dbCompanyDao = new DbCompanyDao(dbClient);

        dbCompanyDao.findAll();

    }

    
}