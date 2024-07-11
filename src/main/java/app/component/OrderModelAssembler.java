package app.component;

import app.controller.OrderController;
import app.model.sql_models.entities.Orders;
import app.model.sql_models.enums.OrderState;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Orders, EntityModel<Orders>> {
    @Override
    public EntityModel<Orders> toModel(Orders orders) {
        List<Link> ListLinks = new ArrayList<>();
        ListLinks.add(linkTo(methodOn(OrderController.class).getById(orders.getId())).withSelfRel());
        ListLinks.add(linkTo(methodOn(OrderController.class).getAll()).withRel("orders"));
        if (orders.getState().equals(OrderState.PENDING)) {
            ListLinks.add(linkTo(methodOn(OrderController.class).done(orders.getId())).withRel("orders/{id}/done"));
        }
        return EntityModel.of(orders,
                ListLinks);

    }
}
