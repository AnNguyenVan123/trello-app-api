package app.controller;

import app.component.UserModelAssembler;
import app.model.sql_models.entities.Users;
import app.service.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserModelAssembler assembler;
    @Autowired
    UserService userService;

    public UserController(UserModelAssembler assembler) {
        this.assembler = assembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        EntityModel<Users> entityModel = assembler.toModel(userService.getById(id));
        return ResponseEntity.ok().body(entityModel);
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        CollectionModel<EntityModel<Users>> collectionModel = CollectionModel.of(userService.getAll().stream()
                .map(assembler::toModel
                )
                .collect(Collectors.toList()), linkTo(methodOn(UserController.class).getAll()).withSelfRel());
        return ResponseEntity.ok().body(collectionModel);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return ResponseEntity.ok().body(userService.getByAccount(authentication.getName()));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> signup_credentials ) {
        String account = signup_credentials.get("account");
        String password = signup_credentials.get("password");
        String name= signup_credentials.get("name");
        System.out.println(account);
        Map<String, String> tokenMap = userService.register(new Users(null,name,account,password,null,null,null));
        if (tokenMap == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Incorrect Account and Password");
        }
        return ResponseEntity.ok()
                .body(tokenMap);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@RequestBody Users users, @PathVariable Long id) {
        EntityModel<Users> entityModel = assembler.toModel(userService.edit(id, users));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> body, @PathVariable Long id) {

        String old_password = body.get("old_password");
        String new_password = body.get("new_password");
        try {
         userService.changePassword(id,new_password,old_password);
         return ResponseEntity.ok().body("Change password successfully");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String account = credentials.get("account");
        String password = credentials.get("password");

        Map<String, String> tokenMap = userService.login(account, password);
        if (tokenMap == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Incorrect Account and Password");
        }
        return ResponseEntity.ok()
                .body(tokenMap);

    }


}
