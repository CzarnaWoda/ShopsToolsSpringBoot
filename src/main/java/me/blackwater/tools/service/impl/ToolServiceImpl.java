package me.blackwater.tools.service.impl;

import lombok.RequiredArgsConstructor;
import me.blackwater.tools.exception.ToolAlreadyExistException;
import me.blackwater.tools.exception.ToolNotFoundException;
import me.blackwater.tools.model.Tool;
import me.blackwater.tools.repository.ToolRepository;
import me.blackwater.tools.service.ToolService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class ToolServiceImpl implements ToolService {


    private final ToolRepository toolRepository;

    @Override
    @Cacheable(cacheNames = "toolByName", key = "#name")
    public Tool getToolByName(String name) throws ToolNotFoundException {
        return toolRepository.getToolByName(name).orElseThrow(() -> new ToolNotFoundException("Tool has not been found (name)"));
    }

    @Override
    @Cacheable(cacheNames = "toolById", key = "#id")
    public Tool getToolById(long id) throws ToolNotFoundException {
        return toolRepository.getToolById(id).orElseThrow(() -> new ToolNotFoundException("Tool has not been found (id)"));
    }

    @Override
    @Cacheable(cacheNames = "toolByShopId", key = "#shopId")
    public Tool getToolByShopId(long shopId) throws ToolNotFoundException {
        return toolRepository.getToolByShopId(shopId).orElseThrow(() -> new ToolNotFoundException("Tool has not been found (shopId)"));
    }

    @Override
    public Tool createTool(Tool tool) throws ToolAlreadyExistException {
        return null;
    }

    @Override
    public Tool updateTool(Tool oldTool, Tool newTool) throws ToolAlreadyExistException, ToolNotFoundException {
        return null;
    }

    @Override
    public Tool deleteTool(long id) throws ToolNotFoundException {
        return null;
    }


}
