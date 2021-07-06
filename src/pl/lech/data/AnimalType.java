package pl.lech.data;

public enum AnimalType {
    Cow("krowa", 40, 100),
    Sheep("owca", 30, 50),
    Goat("koza", 20, 70),
    Chihuahua("Chihuahua", 10, 0),
    ;

    private final String name;
    private final int maturationWeeks;
    private int weeklyEarnings;

    AnimalType(String name, int maturationTime, int weeklyEarnings) {
        this.name = name;
        this.maturationWeeks = maturationTime;
        this.weeklyEarnings = weeklyEarnings;
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
}
