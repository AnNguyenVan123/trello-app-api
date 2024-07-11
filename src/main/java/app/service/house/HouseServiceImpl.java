package app.service.house;

import app.model.sql_models.entities.House;
import app.model.sql_models.entities.Users;
import app.model.sql_models.enums.HouseState;
import app.repository.HouseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    HouseRepository houseRepository;

    @Override
    public House getById(Long id) {

        return houseRepository.findById(id).orElseGet(() -> null);
    }

    @Override
    public House add(House newHouse) {
        return houseRepository.save(newHouse);
    }

    @Override
    public House edit(Long id, House newHouse) {
        return houseRepository.findById(id).map(
                house -> {
                    house.setAddress(newHouse.getAddress());
                    house.setOwner(newHouse.getOwner());
                    house.setPrice(newHouse.getPrice());
                    house.setState(newHouse.getState());

                    return houseRepository.save(house);
                }

        ).orElseGet(
                () -> {
                    return houseRepository.save(newHouse);
                }
        );
    }

    @Override
    public void delete(Long id) {
        houseRepository.deleteById(id);
    }

    @Override
    public List<House> getAll() {
        return houseRepository.findAll()

                ;
    }

    @Override
    public House rented(Long id) {
        return houseRepository.findById(id).map(house -> {
                    house.setState(HouseState.RENTED);
                    return houseRepository.save(house);
                })
                .orElseGet(
                        () -> null
                );
    }

    @Override
    public House empty(Long id) {
        return houseRepository.findById(id).map(house -> {
                    house.setState(HouseState.EMPTY);

                    return houseRepository.save(house);
                })
                .orElseGet(
                        () -> null
                );
    }

    @Override
    public List<House> findByOwnerId(Long id) {
        return houseRepository.findByOwnerId(id);
    }

    @Override
    public Boolean IsOwner(House house, String ownerAccount) {
        Users users = house.getOwner();

        return ownerAccount.equals(users.getAccount());
    }
    @Override
    public List<House> search(Map<String, String> params) {

        String address = " ";
        if (params.containsKey("address")) {
            address = params.get("address");
        }
        int min_price = 0;
        if (params.containsKey("min_price")) {
            min_price = Integer.parseInt(params.get("min_price"));
        }
        int max_price = 0;
        if (params.containsKey("max_price")) {
            max_price = Integer.parseInt(params.get("max_price"));
        }
        HouseState state = HouseState.EMPTY ;
        if (params.isEmpty()) {
            return houseRepository.findByState(state);
        } else {
            if (!address.equalsIgnoreCase(" ") && max_price != 0) {
                return houseRepository.findByAddressContainsIgnoreCaseAndPriceBetweenAndState(address, min_price,max_price,state);
            }
            else if(!address.equalsIgnoreCase(" ")){
                return houseRepository.findByAddressContainsIgnoreCaseAndState(address,state);
            }
            else if(max_price!=0){
                return houseRepository.findByPriceBetweenAndState(min_price,max_price,state);
            }

        }
        return List.of();
    }
}
