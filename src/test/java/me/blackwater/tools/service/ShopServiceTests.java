package me.blackwater.tools.service;

import me.blackwater.tools.exception.ShopAlreadyExistException;
import me.blackwater.tools.exception.ShopNotFoundException;
import me.blackwater.tools.model.Shop;
import me.blackwater.tools.repository.ShopRepository;
import me.blackwater.tools.service.impl.ShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShopServiceTests {

    @Mock
    private ShopRepository shopRepository;

    @InjectMocks
    private ShopServiceImpl shopService;

    private Shop shop;

    @BeforeEach
    void setup() {
        shop = new Shop("Test Shop", "test@example.com");
        shop.setId(1L);
    }

    @Test
    void getShopByNameTests() {
        when(shopRepository.getShopByName("Test Shop")).thenReturn(Optional.of(shop));

        Shop result = shopService.getShopByName("Test Shop");

        assertNotNull(result);
        assertEquals("Test Shop", result.getName());
        assertEquals("test@example.com", result.getEmail());
        verify(shopRepository, times(1)).getShopByName("Test Shop");
    }

    @Test
    void getShopByNameExceptionTests() {
        when(shopRepository.getShopByName("Nonexistent Shop")).thenReturn(Optional.empty());

        assertThrows(ShopNotFoundException.class, () -> shopService.getShopByName("Nonexistent Shop"));
        verify(shopRepository, times(1)).getShopByName("Nonexistent Shop");
    }

    @Test
    void getShopByIdTests() {
        when(shopRepository.getShopById(1L)).thenReturn(Optional.of(shop));

        Shop result = shopService.getShopById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(shopRepository, times(1)).getShopById(1L);
    }

    @Test
    void getShopByIdExceptionTests() {
        when(shopRepository.getShopById(99L)).thenReturn(Optional.empty());

        assertThrows(ShopNotFoundException.class, () -> shopService.getShopById(99L));
        verify(shopRepository, times(1)).getShopById(99L);
    }

    @Test
    void getAllShopsTests() {
        // Przygotowanie danych
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Shop> page = new PageImpl<>(Collections.singletonList(shop));

        // Stubbing
        when(shopRepository.findAll(pageable)).thenReturn(page);

        // Wywołanie metody
        Page<Shop> result = shopService.getAllShops(0, 10, "id", "asc");

        // Weryfikacja
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(shopRepository, times(1)).findAll(pageable);
    }


    @Test
    void createShopTests() {
        when(shopRepository.getShopByName("Test Shop")).thenReturn(Optional.empty());
        when(shopRepository.save(shop)).thenReturn(shop);

        Shop result = shopService.createShop(shop);

        assertNotNull(result);
        assertEquals("Test Shop", result.getName());
        verify(shopRepository, times(1)).getShopByName("Test Shop");
        verify(shopRepository, times(1)).save(shop);
    }

    @Test
    void createShopExceptionTests() {
        when(shopRepository.getShopByName("Test Shop")).thenReturn(Optional.of(shop));

        assertThrows(ShopAlreadyExistException.class, () -> shopService.createShop(shop));
        verify(shopRepository, times(1)).getShopByName("Test Shop");
        verify(shopRepository, never()).save(any(Shop.class));
    }

    @Test
    void updateShopTests() {
        Shop newShop = new Shop("Updated Shop", "updated@example.com");

        when(shopRepository.getShopByName("Updated Shop")).thenReturn(Optional.empty());
        when(shopRepository.save(shop)).thenReturn(shop);

        Shop result = shopService.updateShop(shop, newShop);

        assertNotNull(result);
        assertEquals("Updated Shop", result.getName());
        assertEquals("updated@example.com", result.getEmail());
        verify(shopRepository, times(1)).getShopByName("Updated Shop");
        verify(shopRepository, times(1)).save(shop);

        assertThrows(ShopNotFoundException.class, () -> shopService.getShopByName("Test Shop"));
    }

    @Test
    void updateShopExceptionTests() {
        Shop newShop = new Shop("Updated Shop", "updated@example.com");
        newShop.setId(1L);

        when(shopRepository.getShopByName("Updated Shop")).thenReturn(Optional.of(newShop));

        assertThrows(ShopAlreadyExistException.class, () -> shopService.updateShop(shop, newShop));
        verify(shopRepository, times(1)).getShopByName("Updated Shop");
        verify(shopRepository, never()).save(any(Shop.class));
    }

    @Test
    void deleteShopTests() {
        when(shopRepository.getShopById(1L)).thenReturn(Optional.of(shop));
        doNothing().when(shopRepository).deleteById(1L);

        Shop result = shopService.deleteShop(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(shopRepository, times(1)).getShopById(1L);
        verify(shopRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteShopExceptionTests() {
        when(shopRepository.getShopById(99L)).thenReturn(Optional.empty());

        assertThrows(ShopNotFoundException.class, () -> shopService.deleteShop(99L));
        verify(shopRepository, times(1)).getShopById(99L);
        verify(shopRepository, never()).deleteById(anyLong());
    }
}
