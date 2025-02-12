package me.blackwater.tools.service;

import me.blackwater.tools.exception.ShopAlreadyExistException;
import me.blackwater.tools.exception.ShopNotFoundException;
import me.blackwater.tools.mapper.ShopMapper;
import me.blackwater.tools.model.Shop;
import me.blackwater.tools.repository.ShopRepository;
import me.blackwater.tools.service.impl.ShopServiceImpl;
import me.blackwater.tools.web.requests.ShopCreateRequest;
import me.blackwater.tools.web.requests.ShopUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShopServiceTests {

        @Mock
        private ShopRepository shopRepository;

        @Mock
        private ShopMapper shopMapper;

        @InjectMocks
        private ShopServiceImpl shopService;

        private Shop sampleShop;
        private ShopCreateRequest shopCreateRequest;
        private ShopUpdateRequest shopUpdateRequest;

        @BeforeEach
        void setUp() {
            sampleShop = new Shop();
            sampleShop.setId(1L);
            sampleShop.setName("Test Shop");
            sampleShop.setEmail("test@shop.com");

            shopCreateRequest = new ShopCreateRequest("New Shop", "new@shop.com");
            shopUpdateRequest = new ShopUpdateRequest("Updated Shop", "updated@shop.com");
        }

        @Test
        void getShopByName_Success() throws ShopNotFoundException {
            when(shopRepository.getShopByName("Test Shop")).thenReturn(Optional.of(sampleShop));

            Shop foundShop = shopService.getShopByName("Test Shop");

            assertNotNull(foundShop);
            assertEquals("Test Shop", foundShop.getName());
            verify(shopRepository, times(1)).getShopByName("Test Shop");
        }

        @Test
        void getShopByName_ShouldThrowException_WhenShopNotFound() {
            when(shopRepository.getShopByName("Nonexistent Shop")).thenReturn(Optional.empty());

            assertThrows(ShopNotFoundException.class, () -> shopService.getShopByName("Nonexistent Shop"));
        }

        @Test
        void getShopById_Success() throws ShopNotFoundException {
            when(shopRepository.getShopById(1L)).thenReturn(Optional.of(sampleShop));

            Shop foundShop = shopService.getShopById(1L);

            assertNotNull(foundShop);
            assertEquals(1L, foundShop.getId());
            verify(shopRepository, times(1)).getShopById(1L);
        }

        @Test
        void getShopById_ShouldThrowException_WhenShopNotFound() {
            when(shopRepository.getShopById(999L)).thenReturn(Optional.empty());

            assertThrows(ShopNotFoundException.class, () -> shopService.getShopById(999L));
        }

        @Test
        void getAllShops_Success() {
            Page<Shop> page = new PageImpl<>(List.of(sampleShop));
            Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));

            when(shopRepository.findAll(pageable)).thenReturn(page);

            Page<Shop> result = shopService.getAllShops(0, 10, "name", "asc");

            assertFalse(result.isEmpty());
            assertEquals(1, result.getTotalElements());
            verify(shopRepository, times(1)).findAll(pageable);
        }

        @Test
        void createShop_Success() throws ShopAlreadyExistException {
            when(shopRepository.existsShopByName(shopCreateRequest.name())).thenReturn(false);
            when(shopMapper.toEntity(shopCreateRequest)).thenReturn(sampleShop);
            when(shopRepository.save(sampleShop)).thenReturn(sampleShop);

            Shop createdShop = shopService.createShop(shopCreateRequest);

            assertNotNull(createdShop);
            assertEquals("Test Shop", createdShop.getName());
            verify(shopRepository, times(1)).save(sampleShop);
        }

        @Test
        void createShop_ShouldThrowException_WhenShopAlreadyExists() {
            when(shopRepository.existsShopByName(shopCreateRequest.name())).thenReturn(true);

            assertThrows(ShopAlreadyExistException.class, () -> shopService.createShop(shopCreateRequest));
        }

        @Test
        void updateShop_Success() throws ShopAlreadyExistException, ShopNotFoundException {
            when(shopRepository.getShopByName(shopUpdateRequest.shopName())).thenReturn(Optional.empty());
            when(shopRepository.updateShopById(sampleShop.getId(), shopUpdateRequest.shopName(), shopUpdateRequest.email()))
                    .thenReturn(1); // 1 oznacza, że aktualizacja się powiodła

            int updatedRows = shopService.updateShop(sampleShop, shopUpdateRequest);

            assertEquals(1, updatedRows); // Oczekujemy, że metoda zwróci 1, bo 1 rekord został zmieniony
            verify(shopRepository, times(1)).updateShopById(sampleShop.getId(), "Updated Shop", "updated@shop.com");
        }




        @Test
        void updateShop_ShouldThrowException_WhenNewNameExists() {
            when(shopRepository.getShopByName(shopUpdateRequest.shopName())).thenReturn(Optional.of(new Shop()));

            assertThrows(ShopAlreadyExistException.class, () -> shopService.updateShop(sampleShop, shopUpdateRequest));
        }
        @Test
        void updateShop_NoChanges_ShouldReturnZero() throws ShopAlreadyExistException, ShopNotFoundException {
            ShopUpdateRequest noChangeRequest = new ShopUpdateRequest(sampleShop.getName(), sampleShop.getEmail());

            int updatedRows = shopService.updateShop(sampleShop, noChangeRequest);

            assertEquals(0, updatedRows); // Oczekujemy, że 0 wierszy zostało zmienionych
            verify(shopRepository, never()).updateShopById(anyLong(), anyString(), anyString()); // Nie powinno być wywołania update
        }



    @Test
        void deleteShop_Success() throws ShopNotFoundException {
            when(shopRepository.getShopById(1L)).thenReturn(Optional.of(sampleShop));

            Shop deletedShop = shopService.deleteShop(1L);

            assertNotNull(deletedShop);
            verify(shopRepository, times(1)).deleteById(1L);
        }

        @Test
        void deleteShop_ShouldThrowException_WhenShopNotFound() {
            when(shopRepository.getShopById(999L)).thenReturn(Optional.empty());

            assertThrows(ShopNotFoundException.class, () -> shopService.deleteShop(999L));
        }

        @Test
        void existShopById_Success() {
            when(shopRepository.existsShopById(1L)).thenReturn(true);

            boolean exists = shopService.existShopById(1L);

            assertTrue(exists);
            verify(shopRepository, times(1)).existsShopById(1L);
        }
}
