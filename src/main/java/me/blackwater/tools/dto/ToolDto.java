package me.blackwater.tools.dto;

public record ToolDto(
        long id,
        String name,
        int price,
        String shopName
) {
}
