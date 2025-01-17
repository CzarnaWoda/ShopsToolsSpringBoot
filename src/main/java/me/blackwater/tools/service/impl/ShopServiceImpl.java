package me.blackwater.tools.service.impl;

import lombok.RequiredArgsConstructor;
import me.blackwater.tools.exception.ShopAlreadyExistException;
import me.blackwater.tools.exception.ShopNotFoundException;
import me.blackwater.tools.model.Shop;
import me.blackwater.tools.repository.ShopRepository;
import me.blackwater.tools.service.ShopService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;


    @Override
    @Cacheable(value = "shopByName", key = "#name")
    public Shop getShopByName(String name) throws ShopNotFoundException {
        return shopRepository.getShopByName(name).orElseThrow(() -> new ShopNotFoundException("Shop has not been found (id)"));
    }

    @Override
    @Cacheable(value = "shopById", key = "#id")
    public Shop getShopById(long id) throws ShopNotFoundException {

        return shopRepository.getShopById(id).orElseThrow(() -> new ShopNotFoundException("Shop has not been found (id)"));
    }


    @Override
    @Cacheable(value = "allShops", key = "'page-' + #page + '-size-' + #size")
    public Page<Shop> getAllShops(int page, int size, String sortBy, String sortDir) {

        final Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);

        final Pageable pageable = PageRequest.of(page, size, sort);

        return shopRepository.findAll(pageable);
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = "shopById", key = "#shop.id"),
                    @CachePut(value = "shopByName", key = "#shop.name")
            },
            evict = {
                    @CacheEvict(value = "allShops", allEntries = true)
            }
    )
    @Transactional
    public Shop createShop(Shop shop) throws ShopAlreadyExistException {
        if(shopRepository.getShopByName(shop.getName()).isPresent()){
            throw new ShopAlreadyExistException("Shop already exist");
        }

        return shopRepository.save(shop);
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = "shopByName", key = "#result.name"),
                    @CachePut(value = "shopById", key = "#result.id")
            },
            evict = {
                    @CacheEvict(value = "shopByName", key = "#oldShop.name", beforeInvocation = true),
                    @CacheEvict(value = "shopById", key = "#oldShop.id", beforeInvocation = true),
                    @CacheEvict(value = "allShops", allEntries = true)
            }
    )
    @Transactional
    public Shop updateShop(Shop oldShop, Shop newShop) throws ShopAlreadyExistException, ShopNotFoundException {
        if (!oldShop.getName().equals(newShop.getName()) &&
                shopRepository.getShopByName(newShop.getName()).isPresent()) {
            throw new ShopAlreadyExistException("Shop with that name already exists");
        }

        if (oldShop.equals(newShop)) {
            return oldShop;
        }

        oldShop.setName(newShop.getName());
        oldShop.setEmail(newShop.getEmail());

        return shopRepository.save(oldShop);
    }


    @Override
    @Caching(evict = {
            @CacheEvict(value = "shopById", key = "#id"),
            @CacheEvict(value = "shopByName", key = "#result.name")
    })
    @Transactional
    public Shop deleteShop(long id) throws ShopNotFoundException {
        final Shop shop = shopRepository.getShopById(id).orElseThrow(() -> new ShopNotFoundException("Shop not found"));

         shopRepository.deleteById(id);

         return shop;
    }
}
