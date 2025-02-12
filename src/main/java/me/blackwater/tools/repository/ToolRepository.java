package me.blackwater.tools.repository;

import me.blackwater.tools.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToolRepository extends JpaRepository<Tool,Long> {


    Optional<Tool> getToolByName(String name);

    Optional<Tool> getToolById(Long id);

    List<Tool> getToolsByShopId(long shopId);

    boolean existsToolByName(String name);

    boolean existsToolById(long id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE tool t set t.name = :name, t.price = :price WHERE t.id = :id")
    int updateToolById(@Param("id") long id, @Param("name") String name, @Param("price") int price);

}
