package me.blackwater.tools.repository;

import me.blackwater.tools.model.Shop;
import me.blackwater.tools.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToolRepository extends JpaRepository<Tool,Long> {


    Optional<Tool> getToolByName(String name);

    Optional<Tool> getToolById(Long id);

    Optional<Tool> getToolByShopId(long shopId);
}
