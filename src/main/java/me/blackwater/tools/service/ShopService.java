package me.blackwater.tools.service;


import me.blackwater.tools.exception.ShopAlreadyExistException;
import me.blackwater.tools.exception.ShopNotFoundException;
import me.blackwater.tools.model.Shop;
import me.blackwater.tools.web.requests.ShopCreateRequest;
import me.blackwater.tools.web.requests.ShopUpdateRequest;
import me.blackwater.tools.web.requests.ToolUpdateRequest;
import org.springframework.data.domain.Page;


public interface ShopService {


    Shop getShopByName(String name) throws ShopNotFoundException;

    Shop getShopById(long id) throws ShopNotFoundException;

    Page<Shop> getAllShops(int page, int size, String sortBy, String sortDir);

    Shop createShop(ShopCreateRequest shopCreateRequest) throws ShopAlreadyExistException;

    int updateShop(Shop oldShop, ShopUpdateRequest shopUpdateRequest) throws ShopAlreadyExistException,ShopNotFoundException;

    Shop deleteShop(long id) throws ShopNotFoundException;

    boolean existShopById(long id);

}
