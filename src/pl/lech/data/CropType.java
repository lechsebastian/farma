package pl.lech.data;

public enum CropType implements Item {
    Wheat("Pszenica", 12, 1000, 500, 5, new int[]{13, 14, 15, 16}, 1000, 10000),
    Grass("Trawa", 4, 400, 100, 3, new int[]{13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36}, 200, 2000),
    Carrot("Marchew", 8, 500, 200, 1, new int[]{10, 11, 12, 13}, 100, 1000),
    Beetroot("Buraki", 7, 600, 300, 1, new int[]{10, 11, 12, 13}, 100, 1200),
    Peas("Groszek", 6, 300, 100, 2, new int[]{10, 11, 12, 13}, 200, 1200),
    ;
    private final String name;
    private final int weeksToFinish;
    private final int preparationCost;
    private final int pestsProtection;
    private final int performanceTperHa;
    private final int[] whenCanBePlanted;
    private final int collectionCost;
    private final int sellPrice;

    CropType(String name, int weeksToFinish, int preparationCost, int pestsProtection, int performance, int[] whenCanBePlanted, int collectionCost, int sellPrice) {
        this.name = name;
        this.weeksToFinish = weeksToFinish;
        this.preparationCost = preparationCost;
        this.pestsProtection = pestsProtection;
        this.performanceTperHa = performance;
        this.whenCanBePlanted = whenCanBePlanted;
        this.collectionCost = collectionCost;
        this.sellPrice = sellPrice;
    }

    public int getWeeksToFinish() {
        return weeksToFinish;
    }

    public int getPreparationCost() {
        return preparationCost;
    }

    public int getPestsProtection() {
        return pestsProtection;
    }

    public int getPerformanceTperHa() {
        return performanceTperHa;
    }

    public int[] getWhenCanBePlanted() {
        return whenCanBePlanted;
    }

    public int getCollectionCost() {
        return collectionCost;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getBuyPrice() {
        return getSellPrice() * 10 / 8;
    }

    @Override
    public String toString() {
        return name + " cena przygotowania: " + this.preparationCost + "/ha,  cena ochrony: " + pestsProtection + "/ha co tydzien";
    }
}
