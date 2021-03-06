package pl.lech.data.buildings;

import pl.lech.data.Building;
import pl.lech.data.Item;

import java.util.HashMap;
import java.util.Map;

public class Barn extends Building {
    private final Map<Item, Integer> storage = new HashMap<>();

    public int getItem(Item item) {
        return storage.getOrDefault(item, 0);
    }

    public void storeItem(Item item, int amount) {
        storage.put(item, amount + getItem(item));
    }

    public Map<Item, Integer> getStorage() {
        return storage;
    }

    @Override
    public Type getType() {
        return Type.Barn;
    }

    public void takeItem(Item foodType, int take) {
        this.storage.put(foodType, getItem(foodType) - take);
    }

    public int removeItem(Item o) {
        return storage.remove(o);
    }
}
