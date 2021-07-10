package pl.lech.data;

public class Crop extends TimedEntity {

    private final CropType type;
    private final int createWeek;

    public Crop(int currentWeek, CropType type) {
        super(type.getWeeksToFinish());
        this.createWeek = currentWeek;
        this.type = type;
    }

    public int getWeeksToFinish() {
        return type.getWeeksToFinish();
    }

    public int getPreparationCost() {
        return type.getPreparationCost();
    }

    public int getPestsProtection() {
        return type.getPestsProtection();
    }

    public int getPerformance() {
        return type.getPerformanceTperHa();
    }

    public int[] getWhenCanBePlanted() {
        return type.getWhenCanBePlanted();
    }

    public int getCollectionCost() {
        return type.getCollectionCost();
    }

    public int getSellPrice() {
        return type.getSellPrice();
    }

    public void grow() {
        this.tick();
    }


    public String toString(int week) {
        return type.getName() + " od posiania: " + (week - this.createWeek) + " tygodni, zbiory za: " + (this.createWeek + this.type.getWeeksToFinish() - week) + " tygodni";
    }

    public CropType getType() {
        return type;
    }
}
