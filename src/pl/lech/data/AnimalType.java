package pl.lech.data;

public enum AnimalType {
    Cow("krowa", 40, 100, CropType.Grass, 18),
    Sheep("owca", 30, 50, CropType.Grass, 12),
    Goat("koza", 20, 70, CropType.Grass, 12),
    Chihuahua("Chihuahua", 10, 0, FoodType.DogFood, 7),
    ;

    private final String name;
    private final int maturationWeeks;
    private int weeklyEarnings;
    private Item foodType;
    private int foodPerWeek;

    AnimalType(String name, int maturationTime, int weeklyEarnings, Item foodType, int foodPerWeek) {
        this.name = name;
        this.maturationWeeks = maturationTime;
        this.weeklyEarnings = weeklyEarnings;
        this.foodType = foodType;
        this.foodPerWeek = foodPerWeek;
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
}
