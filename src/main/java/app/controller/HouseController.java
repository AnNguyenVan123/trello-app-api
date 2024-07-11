package app.controller;

import app.component.HouseModelAssembler;
import app.model.sql_models.entities.House;
import app.service.house.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("houses")
@CrossOrigin(origins = "http://localhost:3000")
public class HouseController {
    @Autowired
    HouseModelAssembler assembler;
    @Autowired
    HouseService houseService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        House house = houseService.getById(id);
        if (house == null) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok().body(house);
    }
    @GetMapping("/owner/{id}")
    public ResponseEntity<?> getByOwnerId(@PathVariable Long id) {

       List<House> list = houseService.findByOwnerId(id);
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok().body(list);
    }

    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam Map<String, String> params) {
        System.out.println(1);
        List<House> houseList = houseService.search(params);
        return ResponseEntity.ok().body(houseList);
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody House house) {
        EntityModel<House> entityModel = assembler.toModel(houseService.add(house));
        return ResponseEntity.created(entityModel.getRequiredLink((IanaLinkRelations.SELF)).toUri()).body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        houseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody House newHouse) {

        EntityModel<House> entityModel = assembler.toModel(houseService.edit(id, newHouse));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PatchMapping("/{id}/rented")
    public ResponseEntity<?> rented(@PathVariable Long id) {

        EntityModel<House> entityModel = assembler.toModel(houseService.rented(id));
        return ResponseEntity.created((entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())).body(entityModel);
    }

    @PatchMapping("/{id}/empty")
    public ResponseEntity<?> empty(@PathVariable Long id) {
        EntityModel<House> entityModel = assembler.toModel(houseService.empty(id));
        return ResponseEntity.created((entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())).body(entityModel);
    }


}
