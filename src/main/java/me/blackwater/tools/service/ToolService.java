package me.blackwater.tools.service;


import me.blackwater.tools.exception.ToolAlreadyExistException;
import me.blackwater.tools.exception.ToolNotFoundException;
import me.blackwater.tools.model.Tool;

public interface ToolService {

    Tool getToolByName(String name) throws ToolNotFoundException;

    Tool getToolById(long id) throws ToolNotFoundException;

    Tool getToolByShopId(long shopId) throws ToolNotFoundException;

    Tool createTool(Tool tool) throws ToolAlreadyExistException;

    Tool updateTool(Tool oldTool, Tool newTool) throws ToolAlreadyExistException, ToolNotFoundException;

    Tool deleteTool(long id) throws ToolNotFoundException;
}
