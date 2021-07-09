package pl.lech.data;

public enum CropType {
    Wheat("Pszenica", 12, 1000, 500, 5, new int []{13,14,15,16}, 1000, 10000),
    Carrot("Marchew", 8, 500, 200, 1, new int []{10,11,12,13}, 100, 1000),
    Beetroot("Buraki", 7, 600, 300, 1, new int []{10,11,12,13}, 100, 1200),
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
}
