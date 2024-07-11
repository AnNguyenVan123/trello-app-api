package app.service.user;
import app.model.sql_models.entities.Users;
import java.util.List;
import java.util.Map;

public interface UserService {
    public Users getById(Long id);
    public List<Users> getAll();
    public Map<String,String> register(Users users);
    public void delete(Long id);
    public Users edit(Long id , Users newUser);
    public void changePassword(Long id,String new_password,String old_password);
    public Map<String,String> login(String account , String password);
    public Users getByAccount(String account);
}
