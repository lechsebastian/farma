package pl.lech.data;

public class TimedEntity {
    private int createWeek;
    private int weeksToFinish;

    public TimedEntity(int currentWeek, int weeksToFinish) {
        this.createWeek = currentWeek;
        this.weeksToFinish = weeksToFinish;
    }

    protected boolean isReady(int week){
        return week >= createWeek + weeksToFinish;
    }
}
