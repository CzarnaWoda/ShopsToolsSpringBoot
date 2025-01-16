package me.blackwater.tools.mapper;


import me.blackwater.tools.dto.ShopDto;
import me.blackwater.tools.model.Shop;
import org.springframework.stereotype.Component;

@Component
public class ShopMapper {

    public ShopDto toDto(Shop shop){
        return new ShopDto(shop.getId(),shop.getName(),shop.getEmail());
    }
}
