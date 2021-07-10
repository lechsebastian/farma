package pl.lech.data;

public class Animal extends TimedEntity {
    private int createWeek;
    private final AnimalType type;

    public Animal(int currentWeek, AnimalType type) {
        super(type.getMaturationWeeks());
        this.createWeek = currentWeek;
        this.type = type;
    }

    public AnimalType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return type.getName();
    }

    public String toString(int week) {
        return type.getName() + " wiek: " + (week - this.createWeek) + " tygodni";
    }

    public String getName() {
        return type.getName();
    }

    public int getMaturationWeeks() {
        return type.getMaturationWeeks();
    }

    public int getWeeklyEarnings() {
        return type.getWeeklyEarnings();
    }

    public void increaseWeight() {
        if(!isReady()){
            //increase weight and store in variable
            this.tick();
        }
    }

    public int getFoodPerWeek() {
        return type.getFoodPerWeek();
    }

    public Item getFoodType() {
        return type.getFoodType();
    }

    public void noMeal() {
        //todo: implement
    }
}
