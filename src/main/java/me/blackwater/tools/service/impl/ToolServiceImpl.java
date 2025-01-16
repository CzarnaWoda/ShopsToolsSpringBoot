package me.blackwater.tools.service.impl;

import lombok.RequiredArgsConstructor;
import me.blackwater.tools.exception.ToolNotFoundException;
import me.blackwater.tools.model.Tool;
import me.blackwater.tools.repository.ToolRepository;
import me.blackwater.tools.service.ToolService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToolServiceImpl implements ToolService {


    private final ToolRepository toolRepository;

    @Override
    @Cacheable(cacheNames = "toolByName", key = "#name")
    public Tool getToolByName(String name) throws ToolNotFoundException {
        final Optional<Tool> tool = toolRepository.getToolByName(name);

        if(tool.isEmpty()){
            throw new ToolNotFoundException("Tool has not been found (name)");
        }

        return tool.get();
    }

    @Override
    @Cacheable(cacheNames = "toolById", key = "#id")
    public Tool getToolById(long id) throws ToolNotFoundException {
        final Optional<Tool> tool = toolRepository.getToolById(id);

        if (tool.isEmpty()){
            throw new ToolNotFoundException("Tool has not been found (id)");
        }
        return tool.get();
    }

    @Override
    @Cacheable(cacheNames = "toolByShopId", key = "#shopId")
    public Tool getToolByShopId(long shopId) throws ToolNotFoundException {
        final Optional<Tool> tool = toolRepository.getToolByShopId(shopId);
        if (tool.isEmpty()){
            throw new ToolNotFoundException("Tool has not been found (shopId)");
        }
        return tool.get();
    }
}
