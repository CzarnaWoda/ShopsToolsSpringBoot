package me.blackwater.tools.service;


import me.blackwater.tools.exception.ShopNotFoundException;
import me.blackwater.tools.model.Shop;
import org.springframework.data.domain.Page;


public interface ShopService {


    Shop getShopByName(String name) throws ShopNotFoundException;

    Shop getShopById(long id) throws ShopNotFoundException;

    Page<Shop> getAllShops(int page, int size, String sortBy, String sortDir);
}
