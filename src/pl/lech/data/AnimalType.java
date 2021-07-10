package pl.lech.data;

import pl.lech.data.buildings.Barn;

public enum AnimalType {
    Cow("krowa", 40, 100, CropType.Grass, 18, 1000, Building.Type.Barn, 10, 50, 4),
    Sheep("owca", 30, 50, CropType.Grass, 12, 800,  Building.Type.Barn, 5, 30, 2),
    Goat("koza", 20, 70, CropType.Grass, 12, 900,  Building.Type.Barn, 10, 40, 3),
    Chihuahua("Chihuahua", 10, 0, FoodType.DogFood, 7, 4000, null, 1, 5, 1),
    ;

    private final String name;
    private final int maturationWeeks;
    private int weeklyEarnings;
    private Item foodType;
    private int foodPerWeek;
    private int price;
    private Building.Type requiredBuilding;
    private int weightMin;
    private int weightMax;
    private int weightDiff;

    AnimalType(String name, int maturationTime, int weeklyEarnings, Item foodType, int foodPerWeek, int price, Building.Type requiredBuilding, int weightMin, int weightMax, int weightDiff) {
        this.name = name;
        this.maturationWeeks = maturationTime;
        this.weeklyEarnings = weeklyEarnings;
        this.foodType = foodType;
        this.foodPerWeek = foodPerWeek;
        this.price = price;
        this.requiredBuilding = requiredBuilding;
        this.weightMin = weightMin;
        this.weightMax = weightMax;
        this.weightDiff = weightDiff;
    }

    public String getName() {
        return this.name;
    }

    public int getMaturationWeeks() {
        return this.maturationWeeks;
    }

    public int getWeeklyEarnings() {
        return weeklyEarnings;
    }

    public Item getFoodType() {
        return foodType;
    }

    public int getFoodPerWeek() {
        return foodPerWeek;
    }

    public int getPrice() {
        return price;
    }

    public Building.Type getRequiredBuilding() {
        return requiredBuilding;
    }

    public int getWeightDiff() {
        return weightDiff;
    }

    public int getWeightMax() {
        return weightMax;
    }

    public int getWeightMin() {
        return weightMin;
    }

    @Override
    public String toString() {
        return name + " Jedzenie: " + foodType.toString() + " x" + foodPerWeek + "/tydzien, Cena: " + getPrice() +
                (requiredBuilding != null ? (", Wymaga budynku: " + requiredBuilding.getName()) : "");
    }
}
