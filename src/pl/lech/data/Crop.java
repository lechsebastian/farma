package pl.lech.data;

public class Crop extends TimedEntity {

    private final CropType type;

    public Crop(int currentWeek, CropType type) {
        super(currentWeek, type.getWeeksToFinish());
        this.type = type;
    }
}
