package pl.lech.data;

public enum CropType {

    ;
    private final int weeksToFinish;
    private final int preparationCost;
    private final int pestsProtection;
    private final int performance;
    private final int[] whenCanBePlanted;
    private final int collectionCost;
    private final int sellPrice;

    CropType(int weeksToFinish, int preparationCost, int pestsProtection, int performance, int[] whenCanBePlanted, int collectionCost, int sellPrice) {
        this.weeksToFinish = weeksToFinish;
        this.preparationCost = preparationCost;
        this.pestsProtection = pestsProtection;
        this.performance = performance;
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

    public int getPerformance() {
        return performance;
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
}
