package pl.lech.data;

public enum FoodType implements Item {
    DogFood("Jedzenie dla psa", 15),
    ;

    private String name;
    private int price;

    FoodType(String name, int price) {

        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getBuyPrice() {
        return price;
    }
}
