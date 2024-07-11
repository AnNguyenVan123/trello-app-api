package app.component;
import app.controller.UserController;
import app.model.sql_models.entities.Users;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@Component
public class UserModelAssembler implements RepresentationModelAssembler<Users, EntityModel<Users>> {
    @Override
    public EntityModel<Users> toModel(Users user) {
        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getAll()).withRel("users"));

    }
}
