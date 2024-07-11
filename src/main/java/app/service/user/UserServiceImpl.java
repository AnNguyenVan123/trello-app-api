package app.service.user;

import app.model.sql_models.entities.CustomUserDetails;
import app.model.sql_models.entities.Users;
import app.repository.UserRepository;
import app.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    JWTUtil jwtUtil;

    public Users getById(Long id) {
        return userRepo.findById(id).get();
    }

    public List<Users> getAll() {
        return userRepo.findAll();
    }

    public Map<String,String> register(Users users) {
        Users users1 = userRepo.save(users);
        UserDetails userDetails = new CustomUserDetails(users1);
        System.out.println(userDetails.getUsername());
        Map<String,String> tokenMap = new HashMap<>();
        String accessToken = jwtUtil.generateToken(userDetails.getUsername());
        String refreshToken = jwtUtil.generateToken(userDetails.getUsername());
        tokenMap.put("access_token",accessToken);
        tokenMap.put("refresh_token",refreshToken);
        return tokenMap ;
    }

    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    public Users edit(Long id, Users newUser) {
        return userRepo.findById(id)
                .map(users -> {
                    users.setName(newUser.getName());
                    users.setEmail(newUser.getEmail());
                    users.setPhone(newUser.getPhone());
                    users.setAddress(newUser.getAddress());
                    return userRepo.save(users);
                })
                .orElseGet(() -> {
                    return userRepo.save(newUser);
                });

    }

    @Override
    public void changePassword(Long id, String new_password, String old_password) {
        Users users = userRepo.findById(id).get();
        if(users.getPassword().equals(old_password)){
               users.setPassword(new_password);
               userRepo.save(users);
        }
        else {
            throw new RuntimeException("Invalid password");
        }
    }



    @Override
    public Map<String, String> login(String account, String password) {
        Users users = userRepo.findByAccountAndPassword(account, password);
        if(users == null){
            return null ;
        }
        UserDetails userDetails = new CustomUserDetails(users);
        System.out.println(userDetails.getUsername());
        Map<String,String> tokenMap = new HashMap<>();
        String accessToken = jwtUtil.generateToken(userDetails.getUsername());
        String refreshToken = jwtUtil.generateToken(userDetails.getUsername());
        tokenMap.put("access_token",accessToken);
        tokenMap.put("refresh_token",refreshToken);
        return tokenMap;

    }

    @Override
    public Users getByAccount(String account) {
        return userRepo.findByAccount(account);
    }

}
