package pl.lech.data;

public class Animal extends TimedEntity {
    private int createWeek;
    private final AnimalType type;
    private int weight;
    private int deathCounter = 0;
    private boolean alive = true;

    public Animal(int currentWeek, AnimalType type) {
        super(type.getMaturationWeeks());
        this.createWeek = currentWeek;
        this.type = type;
        this.weight = type.getWeightMin();
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
        deathCounter = 0;
        if(!isReady()){
            this.tick();
        }
        if(weight < type.getWeightMax()){
            this.weight = Math.min(type.getWeightMax(), weight + type.getWeightDiff());
        }
    }

    public int getFoodPerWeek() {
        return type.getFoodPerWeek();
    }

    public Item getFoodType() {
        return type.getFoodType();
    }

    public void noMeal() {
        if(weight > type.getWeightMin()){
            this.weight = Math.max(type.getWeightMin(), weight - type.getWeightDiff());
        }else{
            deathCounter++;
            if(deathCounter == 3){
                this.alive = false;
            }
        }
    }

    public int getSellPrice() {
        return (int) (type.getPrice() * (1f + (weight - type.getWeightMin())) / (type.getWeightMax() - type.getWeightMin()));
    }

    public boolean isAlive() {
        return alive;
    }
}
