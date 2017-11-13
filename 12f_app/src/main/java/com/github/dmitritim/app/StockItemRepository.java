package com.github.dmitritim.app;

import com.github.dmitritim.app.domain.model.ItemType;
import com.github.dmitritim.app.domain.model.StockItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StockItemRepository extends CrudRepository<StockItem, Long> {
    List<StockItem> findByType(ItemType type);
}
