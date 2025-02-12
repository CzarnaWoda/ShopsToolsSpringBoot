package me.blackwater.tools.service;

import me.blackwater.tools.exception.ToolAlreadyExistException;
import me.blackwater.tools.exception.ToolNotFoundException;
import me.blackwater.tools.model.Shop;
import me.blackwater.tools.model.Tool;
import me.blackwater.tools.repository.ToolRepository;
import me.blackwater.tools.service.impl.ToolServiceImpl;
import me.blackwater.tools.web.requests.ToolCreateRequest;
import me.blackwater.tools.web.requests.ToolUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToolServiceTests {

    @Mock
    private ToolRepository toolRepository;

    @Mock
    private ShopService shopService;

    @InjectMocks
    private ToolServiceImpl toolService;

    private Tool sampleTool;
    private ToolCreateRequest toolCreateRequest;
    private ToolUpdateRequest toolUpdateRequest;
    private Shop sampleShop;

    @BeforeEach
    void setUp() {

        sampleShop = new Shop();
        sampleShop.setId(1L);
        sampleShop.setName("Test Shop");

        sampleTool = new Tool("Test Tool", 100, sampleShop);
        sampleTool.setId(1L);

        toolCreateRequest = new ToolCreateRequest("New Tool", 200, sampleShop.getId());
        toolUpdateRequest = new ToolUpdateRequest("Updated Tool", 300);
    }

    @Test
    void getToolByName_ShouldReturnTool() throws ToolNotFoundException {
        when(toolRepository.getToolByName("Test Tool")).thenReturn(Optional.of(sampleTool));

        Tool foundTool = toolService.getToolByName("Test Tool");

        assertNotNull(foundTool);
        assertEquals("Test Tool", foundTool.getName());
        verify(toolRepository, times(1)).getToolByName("Test Tool");
    }

    @Test
    void getToolByName_ShouldThrowException_WhenNotFound() {
        when(toolRepository.getToolByName("Unknown Tool")).thenReturn(Optional.empty());

        assertThrows(ToolNotFoundException.class, () -> toolService.getToolByName("Unknown Tool"));
    }

    @Test
    void getToolById_ShouldReturnTool() throws ToolNotFoundException {
        when(toolRepository.getToolById(1L)).thenReturn(Optional.of(sampleTool));

        Tool foundTool = toolService.getToolById(1L);

        assertNotNull(foundTool);
        assertEquals(1L, foundTool.getId());
        verify(toolRepository, times(1)).getToolById(1L);
    }

    @Test
    void getToolById_ShouldThrowException_WhenNotFound() {
        when(toolRepository.getToolById(999L)).thenReturn(Optional.empty());

        assertThrows(ToolNotFoundException.class, () -> toolService.getToolById(999L));
    }

    @Test
    void getToolsByShopId_ShouldReturnTools() {
        when(toolRepository.getToolsByShopId(sampleShop.getId())).thenReturn(List.of(sampleTool));

        List<Tool> tools = toolService.getToolsByShopId(sampleShop.getId());

        assertFalse(tools.isEmpty());
        assertEquals(1, tools.size());
        verify(toolRepository, times(1)).getToolsByShopId(sampleShop.getId());
    }

    @Test
    void createTool_ShouldReturnCreatedTool() throws ToolAlreadyExistException {
        when(toolRepository.existsToolByName(toolCreateRequest.name())).thenReturn(false);
        when(shopService.getShopById(toolCreateRequest.shopId())).thenReturn(sampleShop);
        when(toolRepository.save(any(Tool.class))).thenReturn(sampleTool);

        Tool createdTool = toolService.createTool(toolCreateRequest);

        assertNotNull(createdTool);
        assertEquals("Test Tool", createdTool.getName());
        verify(toolRepository, times(1)).save(any(Tool.class));
    }

    @Test
    void createTool_ShouldThrowException_WhenToolAlreadyExists() {
        when(toolRepository.existsToolByName(toolCreateRequest.name())).thenReturn(true);

        assertThrows(ToolAlreadyExistException.class, () -> toolService.createTool(toolCreateRequest));
    }

    @Test
    void updateTool_ShouldReturnUpdatedRowsCount() throws ToolAlreadyExistException, ToolNotFoundException {
        when(toolRepository.existsToolByName(toolUpdateRequest.name())).thenReturn(false);
        when(toolRepository.updateToolById(sampleTool.getId(), toolUpdateRequest.name(), toolUpdateRequest.price()))
                .thenReturn(1); // 1 oznacza, że zmieniono jeden rekord

        int updatedRows = toolService.updateTool(sampleTool, toolUpdateRequest);

        assertEquals(1, updatedRows); // Oczekujemy, że zaktualizowano 1 rekord
        verify(toolRepository, times(1)).updateToolById(sampleTool.getId(), "Updated Tool", 300);
    }


    @Test
    void updateTool_ShouldThrowException_WhenNewNameAlreadyExists() {
        when(toolRepository.existsToolByName(toolUpdateRequest.name())).thenReturn(true);

        assertThrows(ToolAlreadyExistException.class, () -> toolService.updateTool(sampleTool, toolUpdateRequest));
    }

    @Test
    void deleteTool_ShouldReturnDeletedTool() throws ToolNotFoundException {
        when(toolRepository.getToolById(1L)).thenReturn(Optional.of(sampleTool));

        Tool deletedTool = toolService.deleteTool(1L);

        assertNotNull(deletedTool);
        assertEquals(1L, deletedTool.getId());
        verify(toolRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTool_ShouldThrowException_WhenNotFound() {
        when(toolRepository.getToolById(999L)).thenReturn(Optional.empty());

        assertThrows(ToolNotFoundException.class, () -> toolService.deleteTool(999L));
    }
}
