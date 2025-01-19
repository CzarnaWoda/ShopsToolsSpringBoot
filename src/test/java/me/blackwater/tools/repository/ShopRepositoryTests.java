package me.blackwater.tools.repository;

import me.blackwater.tools.model.Shop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ShopRepositoryTests {

    @Autowired
    private ShopRepository shopRepository;


    @Test
    @Transactional
    @Rollback
    void testCreateShop(){
        Shop shop = new Shop("test","test@email.com");

        Shop saved = shopRepository.save(shop);


        assertNotNull(saved);


        assertEquals(shop.getName(), saved.getName());
    }

    @Test
    @Transactional
    @Rollback
    void testGetShopById(){
        // Przygotowanie danych
        Shop shop = new Shop("test", "test@email.com");
        Shop savedShop = shopRepository.save(shop); // Zapis obiektu w bazie danych

        // Pobranie obiektu z bazy danych
        Optional<Shop> foundShop = shopRepository.findById(savedShop.getId());

        // Weryfikacja
        assertNotNull(foundShop);
        assertTrue(foundShop.isPresent());

        final Shop s = foundShop.get();

        assertNotNull(s);

        assertEquals(savedShop.getId(), s.getId());
        assertEquals(savedShop.getName(), s.getName());

    }

    @Test
    @Transactional
    @Rollback
    void testUpdateShop(){
        Shop shop = new Shop("test", "test@email.com");
        Shop savedShop = shopRepository.save(shop); // Zapis obiektu w bazie danych

        Optional<Shop> foundShop = shopRepository.findById(savedShop.getId());

        assertNotNull(foundShop);
        assertTrue(foundShop.isPresent());

        final Shop get = foundShop.get();

        assertNotNull(get);

        get.setName("New");
        get.setEmail("new@email.com");

        shopRepository.save(get);


        final Optional<Shop> updatedShop = shopRepository.findById(get.getId());

        assertTrue(updatedShop.isPresent());

        assertEquals("New", updatedShop.get().getName());
        assertEquals("new@email.com", updatedShop.get().getEmail());
    }

    @Test
    @Transactional
    @Rollback
    void testDeleteShop(){
        Shop shop = new Shop("test", "test@email.com");
        Shop savedShop = shopRepository.save(shop);

        Optional<Shop> foundShop = shopRepository.findById(savedShop.getId());

        assertNotNull(foundShop);
        assertTrue(foundShop.isPresent());

        shopRepository.delete(shop);
        Optional<Shop> deletedShop = shopRepository.findById(savedShop.getId());

        assertFalse(deletedShop.isPresent());
    }

}
