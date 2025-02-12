package me.blackwater.tools.service.impl;

import lombok.RequiredArgsConstructor;
import me.blackwater.tools.exception.IllegalPageableArgumentException;
import me.blackwater.tools.exception.ShopAlreadyExistException;
import me.blackwater.tools.exception.ShopNotFoundException;
import me.blackwater.tools.mapper.ShopMapper;
import me.blackwater.tools.model.Shop;
import me.blackwater.tools.repository.ShopRepository;
import me.blackwater.tools.service.ShopService;
import me.blackwater.tools.web.requests.ShopCreateRequest;
import me.blackwater.tools.web.requests.ShopUpdateRequest;
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
    private final ShopMapper shopMapper;


    @Override
    @Cacheable(value = "shopByName", key = "#name")
    public Shop getShopByName(String name) throws ShopNotFoundException {
        return shopRepository.getShopByName(name).orElseThrow(() -> new ShopNotFoundException("Shop has not been found (name)"));
    }

    @Override
    @Cacheable(value = "shopById", key = "#id")
    public Shop getShopById(long id) throws ShopNotFoundException {

        return shopRepository.getShopById(id).orElseThrow(() -> new ShopNotFoundException("Shop has not been found (id)"));
    }


    @Override
    @Cacheable(value = "allShops", key = "'page-' + #page + '-size-' + #size")
    public Page<Shop> getAllShops(int page, int size, String sortBy, String sortDir) {
        if(page < 0){
            throw new IllegalPageableArgumentException("page must be greater than 0");
        }
        if(size <= 0){
            throw new IllegalPageableArgumentException("page size must be greater than 0");
        }

        final Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);

        final Pageable pageable = PageRequest.of(page, size, sort);

        return shopRepository.findAll(pageable);
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = "shopById", key = "#result.id"),
                    @CachePut(value = "shopByName", key = "#result.name")
            },
            evict = {
                    @CacheEvict(value = "allShops", allEntries = true),
                    @CacheEvict(value = "existShopById", key = "#result.id")
            }
    )
    @Transactional
    public Shop createShop(ShopCreateRequest shopCreateRequest) throws ShopAlreadyExistException {
        if(shopRepository.existsShopByName(shopCreateRequest.name())){
            throw new ShopAlreadyExistException("Shop already exist");
        }

        return shopRepository.save(shopMapper.toEntity(shopCreateRequest));
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
    public int updateShop(Shop oldShop, ShopUpdateRequest shopUpdateRequest) throws ShopAlreadyExistException, ShopNotFoundException {
        if (!oldShop.getName().equals(shopUpdateRequest.shopName()) &&
                shopRepository.getShopByName(shopUpdateRequest.shopName()).isPresent()) {
            throw new ShopAlreadyExistException("Shop with that name already exists");
        }

        if (oldShop.getName().equals(shopUpdateRequest.shopName()) && oldShop.getEmail().equals(shopUpdateRequest.email())) {
            return 0;
        }

        return shopRepository.updateShopById(oldShop.getId(), shopUpdateRequest.shopName(),shopUpdateRequest.email());
    }


    @Override
    @Caching(evict = {
            @CacheEvict(value = "shopById", key = "#id"),
            @CacheEvict(value = "shopByName", key = "#result.name"),
            @CacheEvict(value = "allShops", allEntries = true),
            @CacheEvict(value = "existShopById", key = "#id")
    })
    @Transactional
    public Shop deleteShop(long id) throws ShopNotFoundException {
        final Shop shop = shopRepository.getShopById(id).orElseThrow(() -> new ShopNotFoundException("Shop not found"));

         shopRepository.deleteById(id);

         return shop;
    }

    @Override
    @Cacheable(value = "existShopById", key = "#id")
    public boolean existShopById(long id) {
        return shopRepository.existsShopById(id);
    }
}
