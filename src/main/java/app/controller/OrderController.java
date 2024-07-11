package app.controller;

import app.component.OrderModelAssembler;
import app.model.sql_models.entities.House;
import app.model.sql_models.entities.Orders;
import app.model.sql_models.enums.OrderState;
import app.service.house.HouseService;
import app.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderModelAssembler assembler;
    @Autowired
    HouseService houseService;

    @GetMapping("/tenant/{id}")
    public ResponseEntity<?> getOrdersByTenant(@PathVariable Long id) {
        List<Orders> orders = orderService.getByTenant(id);
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        EntityModel<Orders> entityModel = assembler.toModel(orderService.getById(id));
        return ResponseEntity.ok().body(entityModel);
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        List<EntityModel<Orders>> orders = orderService.getAll().stream().map(assembler::toModel).collect(Collectors.toList());
        CollectionModel<EntityModel<Orders>> collectionModel = CollectionModel.of(orders, linkTo(methodOn(OrderController.class).getAll()).withSelfRel());
        return ResponseEntity.ok().body(collectionModel);
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<?> getAll(@PathVariable Long id) {
        List<Orders> list = orderService.getOrdersByOwner(id);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody Orders newOrder) {
        EntityModel<Orders> entityModel = assembler.toModel(orderService.add(newOrder));
        House house = houseService.rented(newOrder.getHouse().getId());
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody Orders newOrder) {
        EntityModel<Orders> entityModel = assembler.toModel(orderService.edit(id, newOrder));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PatchMapping("/{id}/done")
    public ResponseEntity<?> done(@PathVariable Long id) {
        OrderState state = orderService.getById(id).getState();
        if (state == OrderState.PENDING) {
            Orders orders = orderService.done(id);
            return ResponseEntity.ok().body(orders);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        OrderState state = orderService.getById(id).getState();
        if (state == OrderState.PENDING) {
            Orders orders = orderService.cancel(id);
            House house = houseService.empty(orders.getHouse().getId());
            return ResponseEntity.ok().body(orders);
        } else {
            return ResponseEntity.badRequest().body("Invalid request");
        }
    }

}
