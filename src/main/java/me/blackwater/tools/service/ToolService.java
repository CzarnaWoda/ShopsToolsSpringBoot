package me.blackwater.tools.service;


import me.blackwater.tools.exception.ToolAlreadyExistException;
import me.blackwater.tools.exception.ToolNotFoundException;
import me.blackwater.tools.model.Tool;
import me.blackwater.tools.web.requests.ToolCreateRequest;
import me.blackwater.tools.web.requests.ToolUpdateRequest;

import java.util.List;

public interface ToolService {

    Tool getToolByName(String name) throws ToolNotFoundException;

    Tool getToolById(long id) throws ToolNotFoundException;

    List<Tool> getToolsByShopId(long shopId) throws ToolNotFoundException;

    Tool createTool(ToolCreateRequest toolCreateRequest) throws ToolAlreadyExistException;

    int updateTool(Tool oldTool, ToolUpdateRequest toolUpdateRequest) throws ToolAlreadyExistException, ToolNotFoundException;

    Tool deleteTool(long id) throws ToolNotFoundException;
}
