package carsharing.company;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface CompanyDao {
    Optional<Company> findById(int id);
    List<Company> findAll();
    void save(Company company);
//    void update(Company company);
    void deleteAll();

}
