package pl.lech.data;

import java.util.Optional;

public class ArableLand {
    private final int width;
    private final int length;
    private final Optional<Crop> crop = Optional.empty();

    public ArableLand(int width, int length) {
        this.width = width;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getHa(){
        return width * length / 10000;
    }

    public Optional<Crop> getCrop() {
        return crop;
    }

    public String toString(int week) {
        return "Pole uprawne o wymiarach " + this.width + "m na " + this.length + "m (" + getHa() + "ha) -> " + crop.map(c -> c.toString(week)).orElse("brak roślin");
    }

    public int getSellPrice() {
        return getBuyPrice() * 8 / 10;
    }

    public int getBuyPrice() {
        return getHa() * 1000;
    }
}
