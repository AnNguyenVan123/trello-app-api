package app.component;

import app.controller.HouseController;
import app.model.sql_models.entities.House;
import app.model.sql_models.enums.HouseState;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HouseModelAssembler implements RepresentationModelAssembler<House, EntityModel<House>> {
    @Override
    public EntityModel<House> toModel(House house) {
        List<Link> ListLinks = new ArrayList<>();
        ListLinks.add(linkTo(methodOn(HouseController.class).getById(house.getId())).withSelfRel());
        ListLinks.add(linkTo(methodOn(HouseController.class).getAll(null)).withRel("orders"));
        if (house.getState().equals(HouseState.EMPTY)) {
            ListLinks.add(linkTo(methodOn(HouseController.class).rented(house.getId())).withRel("orders/{id}/rented"));
        } else if (house.getState().equals(HouseState.RENTED)) {
            ListLinks.add(linkTo(methodOn(HouseController.class).empty(house.getId())).withRel("orders/{id}/empty"));
        }
        return EntityModel.of(house,
                ListLinks);
    }
}
