package me.blackwater.tools.repository;

import me.blackwater.tools.model.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ShopRepositoryTests {

    @Autowired
    private ShopRepository shopRepository;

    private Shop shop;

    @BeforeEach
    void setUp() {
        shop = new Shop();
        shop.setName("Test Shop");
        shop.setEmail("test@shop.com");
        shop = shopRepository.save(shop);
    }

    @Test
    void getShopByName_ShouldReturnShop() {
        Optional<Shop> foundShop = shopRepository.getShopByName("Test Shop");

        assertTrue(foundShop.isPresent());
        assertEquals("Test Shop", foundShop.get().getName());
    }

    @Test
    void getShopById_ShouldReturnShop() {
        Optional<Shop> foundShop = shopRepository.getShopById(shop.getId());

        assertTrue(foundShop.isPresent());
        assertEquals(shop.getId(), foundShop.get().getId());
    }

    @Test
    void existsShopById_ShouldReturnTrue() {
        assertTrue(shopRepository.existsShopById(shop.getId()));
    }

    @Test
    void existsShopByName_ShouldReturnTrue() {
        assertTrue(shopRepository.existsShopByName("Test Shop"));
    }

    @Test
    void updateShopById_ShouldUpdateShopDetails() {
        shopRepository.updateShopById(shop.getId(), "Updated Shop", "updated@shop.com");

        // Pobranie encji bez cache
        Optional<Shop> updatedShop = shopRepository.findById(shop.getId());

        assertTrue(updatedShop.isPresent());
        assertEquals("Updated Shop", updatedShop.get().getName());
        assertEquals("updated@shop.com", updatedShop.get().getEmail());
    }


    @Test
    void deleteShopById_ShouldRemoveShop() {
        shopRepository.deleteById(shop.getId());

        Optional<Shop> deletedShop = shopRepository.getShopById(shop.getId());

        assertFalse(deletedShop.isPresent());
    }
}
