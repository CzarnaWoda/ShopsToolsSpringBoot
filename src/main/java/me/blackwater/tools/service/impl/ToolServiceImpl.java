package me.blackwater.tools.service.impl;

import lombok.RequiredArgsConstructor;
import me.blackwater.tools.exception.ToolAlreadyExistException;
import me.blackwater.tools.exception.ToolNotFoundException;
import me.blackwater.tools.model.Shop;
import me.blackwater.tools.model.Tool;
import me.blackwater.tools.repository.ToolRepository;
import me.blackwater.tools.service.ShopService;
import me.blackwater.tools.service.ToolService;
import me.blackwater.tools.web.requests.ToolCreateRequest;
import me.blackwater.tools.web.requests.ToolUpdateRequest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolServiceImpl implements ToolService {


    private final ToolRepository toolRepository;
    private final ShopService shopService;

    @Override
    @Cacheable(value = "toolByName", key = "#name")
    public Tool getToolByName(String name) throws ToolNotFoundException {
        return toolRepository.getToolByName(name).orElseThrow(() -> new ToolNotFoundException("Tool has not been found (name)"));
    }

    @Override
    @Cacheable(value = "toolById", key = "#id")
    public Tool getToolById(long id) throws ToolNotFoundException {
        return toolRepository.getToolById(id).orElseThrow(() -> new ToolNotFoundException("Tool has not been found (id)"));
    }

    @Override
    @Cacheable(value = "toolsByShopId", key = "#shopId")
    public List<Tool> getToolsByShopId(long shopId) throws ToolNotFoundException {
        return toolRepository.getToolsByShopId(shopId);
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = "toolById", key = "#result.id"),
                    @CachePut(value = "toolByName", key = "#result.name"),
            }
    )
    public Tool createTool(ToolCreateRequest toolCreateRequest) throws ToolAlreadyExistException {
        if(toolRepository.existsToolByName(toolCreateRequest.name())){
            throw new ToolAlreadyExistException("Tool has already been found (name)");
        }
        final Shop shop = shopService.getShopById(toolCreateRequest.shopId());

        return toolRepository.save(new Tool(toolCreateRequest.name(),toolCreateRequest.price(), shop));
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "toolByName", key = "#oldTool.name", beforeInvocation = true),
                    @CacheEvict(value = "toolsByShopId", key = "#oldTool.id", beforeInvocation = true),
            }
    )
    @Transactional
    public int updateTool(Tool oldTool, ToolUpdateRequest toolUpdateRequest) throws ToolAlreadyExistException, ToolNotFoundException {
        if(!oldTool.getName().equalsIgnoreCase(toolUpdateRequest.name())){
            if(toolRepository.existsToolByName(toolUpdateRequest.name())){
                throw new ToolAlreadyExistException("Tool has already been found (name)");
            }
        }
        return toolRepository.updateToolById(oldTool.getId(),toolUpdateRequest.name(),toolUpdateRequest.price());
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "toolById", key = "#id"),
                    @CacheEvict(value = "toolByName", key = "#result.name"),
                    @CacheEvict(value = "toolsByShopId", key = "#result.shop.id"),
            }
    )
    public Tool deleteTool(long id) throws ToolNotFoundException {
        final Tool tool = toolRepository.getToolById(id).orElseThrow(() -> new ToolNotFoundException("Tool has not been found (id)"));

        toolRepository.deleteById(id);

        return tool;
    }


}
