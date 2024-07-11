package app.repository;
import app.model.sql_models.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Users findByAccountAndPassword(String account, String password);
    Users findByAccount(String account);
}
