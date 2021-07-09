package pl.lech.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Farm {
    private final List<Building> buildings = new ArrayList<>();
    private final List<ArableLand> arableLands = new ArrayList<>();
    private final List<Animal> animals = new ArrayList<>();
    private final String name;


    private final List<ArableLand> availableArableLands = new ArrayList<>();

    public Farm(String name) {
        this.name = name;
        int max = ThreadLocalRandom.current().nextInt(5, 10);
        for(int i = 0; i<max;i++){
            availableArableLands.add(new ArableLand(ThreadLocalRandom.current().nextInt(100, 1000),ThreadLocalRandom.current().nextInt(100, 1000)));
        }
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<ArableLand> getArableLands() {
        return arableLands;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public String getName() {
        return this.name;
    }
}