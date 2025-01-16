package me.blackwater.tools.repository;

import me.blackwater.tools.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {

     Optional<Shop> getShopByName(String name);

     Optional<Shop> getShopById(long id);
}
