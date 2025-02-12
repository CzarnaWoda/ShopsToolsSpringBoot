package me.blackwater.tools.mapper;


import me.blackwater.tools.dto.ShopDto;
import me.blackwater.tools.model.Shop;
import me.blackwater.tools.web.requests.ShopCreateRequest;
import me.blackwater.tools.web.requests.ShopUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class ShopMapper {

    public ShopDto toDto(Shop shop){
        return new ShopDto(shop.getId(),shop.getName(),shop.getEmail());
    }

    public Shop toEntity(ShopUpdateRequest shopUpdateRequest){
        return new Shop(shopUpdateRequest.shopName(),shopUpdateRequest.email());
    }

    public Shop toEntity(ShopCreateRequest shopCreateRequest){
        return new Shop(shopCreateRequest.name(),shopCreateRequest.email());
    }
}
