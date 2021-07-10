package pl.lech.data;

import javax.swing.text.html.Option;
import java.util.Optional;

public class ArableLand {
    private final int width;
    private final int length;
    private Crop crop = null;

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
        return Optional.ofNullable(crop);
    }

    public String toString(int week) {
        return "Pole uprawne o wymiarach " + this.width + "m na " + this.length + "m (" + getHa() + "ha) -> " + getCrop().map(c -> c.toString(week)).orElse("brak roślin");
    }

    public int getSellPrice() {
        return getBuyPrice() * 8 / 10;
    }

    public int getBuyPrice() {
        return getHa() * 12300;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }
}
