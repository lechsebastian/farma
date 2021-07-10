package pl.lech;

import pl.lech.data.*;
import pl.lech.data.buildings.Barn;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FarmGame {
    private static final int START_FARMS = 5;

    private int money = 500_000;
    private int week = 1;
    private boolean endGame = false;
    private final Calendar currentDate = new GregorianCalendar();
    private final List<Farm> farms = new ArrayList<>();

    private final List<Farm> availableFarms = new ArrayList<>();
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private final String[] prefixName = new String[]{
            "Bajeczna",
            "Fantastyczna",
            "Wyjątkowa",
            "Unikalna",
            "Superowa",
            "Cukierkowa",
    };

    private final String[] sufixName = new String[]{
            "Farma",
            "Zagroda",
            "Prowincja",
            "Miejscówa",
            "Zagródka",
    };
    private Scanner scanner;

    private enum MenuEntries {
        BuyFarm("Kup farme"),
        ArableLandMenu("Kup/sprzedaj ziemie uprawna"),
        BuyBuilding("Kup nowy budynek"),
        BuyAnimal("Kup zwierzeta"),
        PlantCrops("Posadz rosliny"),
        HarvestCrops("Zbierz plony"),
        SellAnimalCrop("Sprzedaj zwierzeta/rosliny"),
        ShowStocks("Sprawdz stany zapasow"),
        ShowAnimals("Pokaz zwierzeta"),
        ShowCrops("Pokaz uprawy"),
        NextRound("Nastepna tura"),
        EndGame("Koniec gry"),
        ;

        private final String title;

        MenuEntries(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    public FarmGame() {
        currentDate.set(Calendar.YEAR, 2020);
        currentDate.set(Calendar.WEEK_OF_YEAR, 1);
        generateAvailableFarms();
    }

    private void generateAvailableFarms() {
        for (int i = 0; i < START_FARMS; i++) {
            availableFarms.add(new Farm(prefixName[RANDOM.nextInt(prefixName.length)] + " " + sufixName[RANDOM.nextInt(sufixName.length)]));
        }
    }

    private void startGame() {
        scanner = new Scanner(System.in);
        while (!this.endGame) {
            System.out.println("Data: rok: " + this.currentDate.get(Calendar.YEAR) + ", tydzień: " + this.currentDate.get(Calendar.WEEK_OF_YEAR));
            System.out.println("Stan konta: " + money + " ilość farm: " + farms.size());
            for (int i = 0; i < MenuEntries.values().length; i++) {
                System.out.println((i + 1) + ". " + MenuEntries.values()[i].getTitle());
            }
            System.out.print("Wybierz opcje [1-" + MenuEntries.values().length + "]: ");
            int choice = scanner.nextInt();
            if (choice <= 0 || choice > MenuEntries.values().length) {
                System.out.println("Niepoprawny wybór!");
                continue;
            }
            switch (MenuEntries.values()[choice - 1]) {
                case BuyFarm: {
                    System.out.println();
                    System.out.println();
                    int i = 1;
                    for (Farm availableFarm : availableFarms) {
                        System.out.println(i + ". " + availableFarm.toString() + " Price: " + availableFarm.getBuyPrice());
                        i++;
                    }
                    int farmChoice = scanner.nextInt() - 1;
                    if (availableFarms.get(farmChoice).getBuyPrice() > money) {
                        System.out.println("Nie posiadasz wystarczającej ilości pieniędzy... :(");
                        continue;
                    }
                    Farm remove = availableFarms.remove(farmChoice);
                    System.out.println();
                    System.out.println("Zakupiłeś farmę: " + remove.toString());
                    money -= remove.getBuyPrice();
                    farms.add(remove);
                    break;
                }
                case ShowCrops: {
                    for (Farm farm : farms) {
                        System.out.println("Farma o nazwie: " + farm.getName());
                        for (ArableLand arableLand : farm.getArableLands()) {
                            arableLand.getCrop().ifPresent(crop -> {
                                System.out.println(" - " + crop.toString(week));
                            });
                        }
                    }
                    continue;
                }
                case PlantCrops: {
                    Farm farm = selectFarm();
                    if (farm == null) {
                        continue;
                    }
                    ArableLand arableLand = selectArableLand(farm.getArableLands(), l -> "");
                    if (arableLand == null) {
                        continue;
                    }
                    CropType type = selectCropType();
                    if (type == null) {
                        continue;
                    }
                    if (money < arableLand.getHa() * type.getPreparationCost()) {
                        System.out.println("Nie posiadasz pieniedzy na przygotowanie upraw...");
                        continue;
                    }
                    arableLand.setCrop(new Crop(week, type));
                    money -= arableLand.getHa() * type.getPreparationCost();
                    break;
                }
                case ShowStocks: {
                    for (Farm farm : farms) {
                        for (Building building : farm.getBuildings()) {
                            if (building instanceof Barn) {
                                ((Barn) building).getStorage().forEach((item, amount) -> System.out.println(amount + "x " + item.getName()));
                            }
                        }
                    }
                    continue;
                }
                case BuyBuilding: {
                    Farm farm = selectFarm();
                    if(farm == null){
                        continue;
                    }

                    Building.Type type = selectBuildingType();
                    if(type == null){
                        continue;
                    }
                    if(money < type.getPrice()){
                        System.out.println("Nie posiadasz wystarczajacej ilosci pieniedzy!");
                        continue;
                    }
                    money -= type.getPrice();
                    farm.getBuildings().add(type.createNew());
                    break;
                }
                case ShowAnimals: {
                    for (Farm farm : farms) {
                        System.out.println("Farma o nazwie: " + farm.getName());
                        for (Animal animal : farm.getAnimals()) {
                            System.out.println(" - " + animal.toString(week));
                        }
                    }
                    continue;
                }
                case HarvestCrops:{
                    Farm farm = selectFarm();
                    if(farm == null){
                        continue;
                    }
                    ArableLand arableLand = selectArableLand(farm.getArableLands().stream().filter(l -> l.getCrop().isPresent()).collect(Collectors.toList()), l -> "");
                    if(arableLand == null){
                        continue;
                    }
                    Crop crop = arableLand.getCrop().get();
                    if(!crop.isReady(week)){
                        System.out.println("Uprawy nie sa gotowe!");
                        continue;
                    }
                    Optional<Building> first = farm.getBuildings().stream().filter(building -> building.getType() == Building.Type.Barn).findFirst();
                    boolean sell = !first.isPresent();
                    if(first.isPresent()){
                        System.out.println("Wolisz plony sprzedac czy przechowac w stodole? p/s");
                        char input = scanner.next().charAt(0);
                        if(input == 'p'){
                            System.out.println("Przechowales plony: " + crop.getType().getName() + " x" + (crop.getPerformance() * arableLand.getHa()));
                            ((Barn)first.get()).storeItem(crop.getType(), crop.getPerformance() * arableLand.getHa());
                        }else if(input == 's'){
                            sell = true;
                        }
                    }
                    if(sell){
                        System.out.println("zebrales plony z: " + arableLand.toString(week));
                        money -= crop.getCollectionCost() * arableLand.getHa();
                        money += crop.getPerformance() * arableLand.getHa() * crop.getSellPrice();
                    }
                    break;
                }
                case BuyAnimal: {
                    AnimalType type = selectAnimal();
                    if (type == null) {
                        continue;
                    }
                    if (money < type.getPrice()) {
                        System.out.println("Nie posiadasz wystarczajacej ilosci pieniedzy!");
                        continue;
                    }
                    if (type.getRequiredBuilding() != null && !farms.stream().flatMap(farm -> farm.getBuildings().stream()).anyMatch(building -> building.getType() == type.getRequiredBuilding())) {
                        System.out.println("Nie posiadasz wymaganego budynku!");
                        continue;
                    }
                    farms.stream().filter(farm -> farm.getBuildings().stream().anyMatch(building -> building.getType() == type.getRequiredBuilding()))
                            .findFirst().ifPresent(farm -> {
                        farm.getAnimals().add(new Animal(week, type));
                        money -= type.getPrice();
                    });
                    break;
                }
                case SellAnimalCrop: {
                    Farm farm = selectFarm();
                    if(farm == null){
                        continue;
                    }
                    System.out.println("Chcesz sprzedac zwierze czy plony? (z/p)");
                    char input = scanner.next().charAt(0);
                    if(input == 'z'){
                        Animal animal = selectItem(farm.getAnimals(), a -> a.toString(week));
                        if(animal == null){
                            continue;
                        }
                        if(!animal.isReady()){
                            System.out.println("Zwierze jeszcze nie jest gotowe na sprzedaz!");
                            continue;
                        }
                        int sellPrice = animal.getSellPrice();
                        money += sellPrice;
                        farm.getAnimals().remove(animal);
                        System.out.println("Sprzedales " + animal.toString(week));
                    }else if(input == 'p'){
                        for (Building building : farm.getBuildings()) {
                            if(building instanceof Barn){
                                System.out.println("Znaleziono stodole z plonami, wybierz ktore chcesz sprzedac: ");
                                Set<Item> items = ((Barn) building).getStorage().keySet();
                                Item o = selectItem(new ArrayList<>(items), Object::toString);
                                if(o == null){
                                    continue;
                                }
                                int count = ((Barn) building).removeItem(o);
                                money += o.getSellPrice() * count;
                                System.out.println("sprzedano za " + (o.getSellPrice() * count));
                            }
                        }
                    }else{
                        continue;
                    }
                    break;
                }
                case ArableLandMenu: {
                    Farm farm = selectFarm();
                    if (farm == null)
                        continue;
                    System.out.println("Chcesz kupic czy sprzedac dzialke? (k/s)");
                    char input = (char) scanner.next().charAt(0);
                    if (input == 'k') {
                        System.out.println("Wybierz dzialke na sprzedaz:");
                        ArableLand land = selectArableLand(farm.getAvailableLands(), l -> "cena " + l.getSellPrice());
                        if (land == null)
                            continue;
                        farm.getAvailableLands().remove(land);
                        farm.getArableLands().add(land);
                        money += land.getSellPrice();
                        System.out.println("Sprzedales dzialke o powierzchni " + land.getHa() + "ha za kwote " + land.getBuyPrice());
                    } else if (input == 's') {
                        System.out.println("Wybierz dzialke na sprzedaz:");
                        ArableLand land = selectArableLand(farm.getArableLands(), l -> "cena " + l.getBuyPrice());
                        if (land == null)
                            continue;
                        farm.getAvailableLands().add(land);
                        farm.getArableLands().remove(land);
                        money += land.getSellPrice();
                        System.out.println("Sprzedales dzialke o powierzchni " + land.getHa() + "ha za kwote " + land.getSellPrice());
                    } else {
                        continue;
                    }
                    break;
                }
                case NextRound:
                    System.out.println("Przechodzenie do nastepnego tygodnia gry...");
                    break;
                case EndGame:
                    this.endGame = true;
                    System.out.println("Wybrałeś koniec gry. Do zobaczenia!");
                    continue;
                default:
                    System.out.println("Niepoprawny wybór!");
                    continue;
            }

            for (Farm farm : farms) {
                farm.getArableLands().stream()
                        .map(ArableLand::getCrop)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .forEach(Crop::grow);

                farm.getAnimals().stream()
                        .map(Animal::getType)
                        .distinct().forEach(type -> {
                    int count = (int) farm.getAnimals().stream().filter(animal -> animal.getType() == type).count();
                    int pairs = (int) Math.floor(count / 2f);
                    for (int i = 0; i < pairs; i++) {
                        if (RANDOM.nextFloat() < 0.001f) {
                            Animal animal = new Animal(week, type);
                            farm.getAnimals().add(animal);
                            System.out.println("Twoje zwierzeta sie rozmnożyły, oto nowe zwierze w Twojej farmie: " + animal.toString(week));
                        }
                    }
                });

                int weeklyEarnings = farm.getAnimals().stream().mapToInt(Animal::getWeeklyEarnings).sum();
                if (weeklyEarnings > 0) {
                    System.out.println("Za sprzedaż produktów odzwierzęcych otrzymujesz: " + weeklyEarnings);
                    this.money += weeklyEarnings;
                }

                int pestsProtectionCost = farm.getArableLands().stream()
                        .map(ArableLand::getCrop)
                        .filter(Optional::isPresent)
                        .map(Optional::get).mapToInt(Crop::getPestsProtection).sum();
                if (pestsProtectionCost > 0) {
                    System.out.println("Płacisz " + pestsProtectionCost + " za ochrone roslin przed pasozytami!");
                    if(pestsProtectionCost > money){
                        for (ArableLand arableLand : farm.getArableLands()) {
                            if(arableLand.getCrop().isPresent()){
                                if(RANDOM.nextFloat() < 0.01f){
                                    System.out.println("Plony zostaly zjedzone przez robaki!!");
                                    arableLand.setCrop(null);
                                }
                            }
                        }
                    }else{
                        this.money -= pestsProtectionCost;
                    }
                }
            }


            List<Barn> barns = farms.stream().flatMap(farm -> farm.getBuildings().stream()).filter(b -> b.getType() == Building.Type.Barn).map(b -> (Barn)b).collect(Collectors.toList());
            for (Farm farm : farms) {
                for (Animal animal : farm.getAnimals()) {
                    Item foodType = animal.getFoodType();
                    int foodPerWeek = animal.getFoodPerWeek();
                    for (Barn barn : barns) {
                        int take = Math.min(barn.getItem(foodType), foodPerWeek);
                        foodPerWeek -= take;
                        barn.takeItem(foodType, take);
                    }
                    if(foodPerWeek > 0){
                        System.out.println("Nie posiadasz " + foodType.getName() + " a wiec musisz kupic wydajac " + foodType.getBuyPrice() * foodPerWeek);
                        int cost = foodType.getBuyPrice() * foodPerWeek;
                        if(money < cost){
                            animal.noMeal();
                        }else{
                            money -= cost;
                            animal.increaseWeight();
                        }
                    }else{
                        animal.increaseWeight();
                    }
                }
            }

            if (
                    farms.stream().flatMap(farm -> farm.getArableLands().stream()).mapToInt(ArableLand::getHa).sum() > 20 &&
                            farms.stream().flatMap(farm -> farm.getAnimals().stream()).map(Animal::getType).distinct().count() >= 5 &&
                            farms.stream().flatMap(farm -> farm.getArableLands().stream()).map(ArableLand::getCrop)
                                    .filter(Optional::isPresent).map(Optional::get).distinct().count() >= 5
            ) {
                boolean ok = true;
                for (AnimalType value : AnimalType.values()) {
                    if (getFoodCount(value.getFoodType()) < farms.stream().flatMap(farm -> farm.getAnimals().stream()).filter(animal -> animal.getType() == value)
                            .mapToInt(animal -> animal.getFoodPerWeek() * 52).sum()) {
                        ok = false;
                    }
                }
                if (ok) {
                    System.out.println("**********************************************");
                    System.out.println("* Brawo, osiagnales status rolnika doskonalego");
                    System.out.println("* Udalo Ci sie to osiagnac w " + week + " tygodni");
                    System.out.println("**********************************************");
                    endGame = true;
                }
            }

            this.currentDate.add(Calendar.WEEK_OF_YEAR, 1);
            this.week += 1;
            for (int i = 0; i < 5; i++) System.out.println();
        }

    }

    private <T> T selectItem(List<T> keys, Function<T, String> toString) {
        int i;
        do {
            System.out.println("0. Cofnij");
            i = 1;
            for (T type : keys) {
                System.out.println(i + ". " + toString.apply(type));
                i++;
            }
            i = scanner.nextInt() - 1;
        } while (i < -1 || i >= keys.size());
        if (i == -1) return null;
        return keys.get(i);
    }

    private Building.Type selectBuildingType() {
        int i;
        do {
            System.out.println("0. Cofnij");
            i = 1;
            for (Building.Type type : Building.Type.values()) {
                System.out.println(i + ". " + type.toString());
                i++;
            }
            i = scanner.nextInt() - 1;
        } while (i < -1 || i >= Building.Type.values().length);
        if (i == -1) return null;
        return Building.Type.values()[i];
    }

    private AnimalType selectAnimal() {
        int i;
        do {
            System.out.println("0. Cofnij");
            i = 1;
            for (AnimalType type : AnimalType.values()) {
                System.out.println(i + ". " + type.toString());
                i++;
            }
            i = scanner.nextInt() - 1;
        } while (i < -1 || i >= AnimalType.values().length);
        if (i == -1) return null;
        return AnimalType.values()[i];
    }

    private CropType selectCropType() {
        int i;
        List<CropType> collect = Arrays.stream(CropType.values()).filter(type -> Arrays.stream(type.getWhenCanBePlanted()).anyMatch(z -> z == week % 52)).collect(Collectors.toList());
        if (collect.size() == 0) {
            System.out.println("Nie mozna teraz siac zadnej z roslin...");
            return null;
        }
        do {
            System.out.println("0. Cofnij");
            i = 1;
            for (CropType type : collect) {
                System.out.println(i + ". " + type.toString());
                i++;
            }
            i = scanner.nextInt() - 1;
        } while (i < -1 || i >= collect.size());
        if (i == -1) return null;
        return collect.get(i);
    }

    private int getFoodCount(Item foodType) {
        return farms.stream().flatMap(farm -> farm.getBuildings().stream())
                .filter(building -> building instanceof Barn).mapToInt(barn -> ((Barn) barn).getItem(foodType)).sum();
    }

    private ArableLand selectArableLand(List<ArableLand> arableLands, Function<ArableLand, String> sufix) {
        int i;
        do {
            System.out.println("0. Cofnij");
            i = 1;
            for (ArableLand land : arableLands) {
                System.out.println(i + ". " + land.toString(week) + " " + sufix.apply(land));
                i++;
            }
            i = scanner.nextInt() - 1;
        } while (i < -1 || i >= farms.size());
        if (i == -1) return null;
        return arableLands.get(i);
    }

    private Farm selectFarm() {
        int i;
        do {
            System.out.println("0. Cofnij");
            i = 1;
            for (Farm farm : farms) {
                System.out.println(i + ". " + farm.toString());
                i++;
            }
            i = scanner.nextInt() - 1;
        } while (i < -1 || i >= farms.size());
        if (i == -1) return null;
        return farms.get(i);
    }

    public static void main(String[] args) {
        new FarmGame().startGame();
    }
}
