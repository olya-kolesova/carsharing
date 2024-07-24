package carsharing;
import java.util.List;
import java.util.Optional;

public interface CompanyDao {
    Optional<Company> findById(int id);
    List<Company> findAll();
    void save(Company company);
//    void update(Company company);
    void deleteAll();

}
