package me.blackwater.tools.repository;

import me.blackwater.tools.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {

     Optional<Shop> getShopByName(String name);

     Optional<Shop> getShopById(long id);

     @Modifying(clearAutomatically = true)
     @Transactional
     @Query("UPDATE shop s set s.name = :shopName, s.email = :shopEmail WHERE s.id = :id")
     int updateShopById(long id, String shopName, String shopEmail);

     boolean existsShopById(long id);

     boolean existsShopByName(String name);
}
