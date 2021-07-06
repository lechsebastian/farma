package pl.lech.data;

public class TimedEntity {
    protected int weeksToFinish;

    public TimedEntity(int weeksToFinish) {
        this.weeksToFinish = weeksToFinish;
    }

    public void tick(){
        this.weeksToFinish--;
    }

    protected boolean isReady(){
        return weeksToFinish <= 0;
    }
}
