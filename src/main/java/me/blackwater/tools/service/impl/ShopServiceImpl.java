package me.blackwater.tools.service.impl;

import lombok.RequiredArgsConstructor;
import me.blackwater.tools.exception.ShopNotFoundException;
import me.blackwater.tools.model.Shop;
import me.blackwater.tools.repository.ShopRepository;
import me.blackwater.tools.service.ShopService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;


    @Override
    @Cacheable(value = "shopByName", key = "#name")
    public Shop getShopByName(String name) throws ShopNotFoundException {
        final Optional<Shop> shop = shopRepository.getShopByName(name);

        if(shop.isEmpty()){
            throw new ShopNotFoundException("Shop has not been found (name)");
        }

        return shop.get();
    }

    @Override
    @Cacheable(value = "shopById", key = "#id")
    public Shop getShopById(long id) throws ShopNotFoundException {

        final Optional<Shop> shop = shopRepository.getShopById(id);

        if(shop.isEmpty()){
            throw new ShopNotFoundException("Shop has not been found (id)");
        }
        return shop.get();
    }


    @Override
    @Cacheable(value = "allShops", key = "'page-' + #page + '-size-' + #size")
    public Page<Shop> getAllShops(int page, int size, String sortBy, String sortDir) {

        final Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);

        final Pageable pageable = PageRequest.of(page, size, sort);

        return shopRepository.findAll(pageable);
    }
}
