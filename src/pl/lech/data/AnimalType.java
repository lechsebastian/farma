package pl.lech.data;

import pl.lech.data.buildings.Barn;

public enum AnimalType {
    Cow("krowa", 40, 100, CropType.Grass, 18, 1000, Building.Type.Barn),
    Sheep("owca", 30, 50, CropType.Grass, 12, 800,  Building.Type.Barn),
    Goat("koza", 20, 70, CropType.Grass, 12, 900,  Building.Type.Barn),
    Chihuahua("Chihuahua", 10, 0, FoodType.DogFood, 7, 4000, null),
    ;

    private final String name;
    private final int maturationWeeks;
    private int weeklyEarnings;
    private Item foodType;
    private int foodPerWeek;
    private int price;
    private Building.Type requiredBuilding;

    AnimalType(String name, int maturationTime, int weeklyEarnings, Item foodType, int foodPerWeek, int price, Building.Type requiredBuilding) {
        this.name = name;
        this.maturationWeeks = maturationTime;
        this.weeklyEarnings = weeklyEarnings;
        this.foodType = foodType;
        this.foodPerWeek = foodPerWeek;
        this.price = price;
        this.requiredBuilding = requiredBuilding;
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

    @Override
    public String toString() {
        return name + " Jedzenie: " + foodType.toString() + " x" + foodPerWeek + "/tydzien, Cena: " + getPrice() +
                (requiredBuilding != null ? (", Wymaga budynku: " + requiredBuilding.getName()) : "");
    }
}
