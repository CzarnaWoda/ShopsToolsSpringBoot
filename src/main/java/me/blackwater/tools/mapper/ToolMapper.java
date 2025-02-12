package me.blackwater.tools.mapper;

import me.blackwater.tools.dto.ToolDto;
import me.blackwater.tools.model.Tool;
import me.blackwater.tools.web.requests.ToolCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class ToolMapper {

    public ToolDto toDto(Tool tool) {
        return new ToolDto(tool.getId(), tool.getName(),tool.getPrice(),tool.getShop().getName());
    }

    public Tool toEntity(ToolCreateRequest toolCreateRequest){
        return new Tool();
    }
}
