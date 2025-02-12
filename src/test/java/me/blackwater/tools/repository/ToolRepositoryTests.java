package me.blackwater.tools.repository;

import me.blackwater.tools.model.Shop;
import me.blackwater.tools.model.Tool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ToolRepositoryTests {

    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private ShopRepository shopRepository;

    private Tool tool;
    private Shop shop;

    @BeforeEach
    void setUp() {
        shop = new Shop();
        shop.setName("Test Shop");
        shop = shopRepository.save(shop); // Musimy najpierw zapisać sklep

        tool = new Tool("Test Tool", 100, shop);
        tool = toolRepository.save(tool); // Zapisujemy narzędzie
    }

    @Test
    void getToolByName_ShouldReturnTool() {
        Optional<Tool> foundTool = toolRepository.getToolByName("Test Tool");

        assertTrue(foundTool.isPresent());
        assertEquals("Test Tool", foundTool.get().getName());
    }

    @Test
    void getToolByName_ShouldReturnEmpty_WhenNotFound() {
        Optional<Tool> foundTool = toolRepository.getToolByName("Unknown Tool");

        assertFalse(foundTool.isPresent());
    }

    @Test
    void getToolById_ShouldReturnTool() {
        Optional<Tool> foundTool = toolRepository.getToolById(tool.getId());

        assertTrue(foundTool.isPresent());
        assertEquals(tool.getId(), foundTool.get().getId());
    }

    @Test
    void getToolById_ShouldReturnEmpty_WhenNotFound() {
        Optional<Tool> foundTool = toolRepository.getToolById(999L);

        assertFalse(foundTool.isPresent());
    }

    @Test
    void getToolsByShopId_ShouldReturnTools() {
        List<Tool> tools = toolRepository.getToolsByShopId(shop.getId());

        assertFalse(tools.isEmpty());
        assertEquals(1, tools.size());
        assertEquals("Test Tool", tools.get(0).getName());
    }

    @Test
    void getToolsByShopId_ShouldReturnEmptyList_WhenNoToolsFound() {
        List<Tool> tools = toolRepository.getToolsByShopId(999L);

        assertTrue(tools.isEmpty());
    }

    @Test
    void existsToolByName_ShouldReturnTrue() {
        assertTrue(toolRepository.existsToolByName("Test Tool"));
    }

    @Test
    void existsToolByName_ShouldReturnFalse_WhenNotFound() {
        assertFalse(toolRepository.existsToolByName("Unknown Tool"));
    }

    @Test
    void existsToolById_ShouldReturnTrue() {
        assertTrue(toolRepository.existsToolById(tool.getId()));
    }

    @Test
    void existsToolById_ShouldReturnFalse_WhenNotFound() {
        assertFalse(toolRepository.existsToolById(999L));
    }

    @Test
    void updateToolById_ShouldUpdateToolDetails() {
        int updatedRows = toolRepository.updateToolById(tool.getId(), "Updated Tool", 300);

        assertEquals(1, updatedRows); // Powinien zostać zmieniony 1 rekord

        Optional<Tool> updatedTool = toolRepository.getToolById(tool.getId());
        assertTrue(updatedTool.isPresent());
        assertEquals("Updated Tool", updatedTool.get().getName());
        assertEquals(300, updatedTool.get().getPrice());
    }

    @Test
    void updateToolById_ShouldReturnZero_WhenToolNotFound() {
        int updatedRows = toolRepository.updateToolById(999L, "Updated Tool", 300);

        assertEquals(0, updatedRows); // Powinno zwrócić 0, bo nie znaleziono narzędzia
    }

    @Test
    void deleteToolById_ShouldRemoveTool() {
        toolRepository.deleteById(tool.getId());

        Optional<Tool> deletedTool = toolRepository.getToolById(tool.getId());

        assertFalse(deletedTool.isPresent());
    }
}
