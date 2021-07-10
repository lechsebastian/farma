package pl.lech.data;

import pl.lech.data.buildings.Barn;

import java.util.function.Supplier;

public abstract class Building {

    public abstract Type getType();

    public enum Type {
        Barn("Stodola", 100000, Barn::new),
        ;

        private String name;
        private int price;
        private Supplier<Building> generator;

        Type(String name, int price, Supplier<Building> generator) {

            this.name = name;
            this.price = price;
            this.generator = generator;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return name + ", cena: " + price;
        }

        public Building createNew() {
            return generator.get();
        }
    }
}
