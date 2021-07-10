package pl.lech;

import pl.lech.data.*;
import pl.lech.data.buildings.Barn;

import java.io.IOException;
import java.util.*;

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
        BuyAnimalCrop("Kup zwierzeta/rosliny"),
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
                case PlantCrops:
                    //todo: implement
                    break;
                case ShowStocks:
                    //todo: implement
                    continue;
                case BuyBuilding:
                    //todo: implement
                    break;
                case ShowAnimals: {
                    for (Farm farm : farms) {
                        System.out.println("Farma o nazwie: " + farm.getName());
                        for (Animal animal : farm.getAnimals()) {
                            System.out.println(" - " + animal.toString(week));
                        }
                    }
                    continue;
                }
                case HarvestCrops:
                    //todo: implement
                    //sprzedajemy albo przechowujemy (to sprawdza stodole)
                    break;
                case BuyAnimalCrop:
                    //todo: implement
                    break;
                case SellAnimalCrop:
                    //todo: implement
                    break;
                case ArableLandMenu: {
                    Farm farm = selectFarm();
                    if (farm == null)
                        continue;
                    System.out.println("Chcesz kupic czy sprzedac dzialke? (k/s)");
                    char input = (char) scanner.next().charAt(0);
                    if (input == 'k') {
                        System.out.println("Wybierz dzialke na sprzedaz:");
                        ArableLand land = selectArableLand(farm.getAvailableLands(), false);
                        if (land == null)
                            continue;
                        farm.getAvailableLands().remove(land);
                        farm.getArableLands().add(land);
                        money += land.getSellPrice();
                        System.out.println("Sprzedales dzialke o powierzchni " + land.getHa() + "ha za kwote " + land.getBuyPrice());
                    } else if (input == 's') {
                        System.out.println("Wybierz dzialke na sprzedaz:");
                        ArableLand land = selectArableLand(farm.getArableLands(), true);
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
                farm.getAnimals().forEach(Animal::increaseWeight);

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
                    this.money -= pestsProtectionCost;
                }
            }


            //todo: implement
            //zwierzęta wcinają paszę, jeśli masz dla nich odłożone plony to w pierwszej kolejności ze stodoły, jeżeli nie to musisz je kupić.

            if (money < 0) {
                //todo: implement
                //Jeżeli skończą się pieniądze:
                //zwierzęta zaczynają chudnąć
                //w każdym tygodniu istnieje niewielkie ryzyko, że robaki zjedzą plony na polach
            }

            this.currentDate.add(Calendar.WEEK_OF_YEAR, 1);
            this.week += 1;
            for (int i = 0; i < 5; i++) System.out.println();

            if(
                    farms.stream().flatMap(farm -> farm.getArableLands().stream()).mapToInt(ArableLand::getHa).sum() > 20 &&
                            farms.stream().flatMap(farm -> farm.getAnimals().stream()).map(Animal::getType).distinct().count() >= 5 &&
                            farms.stream().flatMap(farm -> farm.getArableLands().stream()).map(ArableLand::getCrop)
                                    .filter(Optional::isPresent).map(Optional::get).distinct().count() >= 5
            ){
                boolean ok = true;
                for (AnimalType value : AnimalType.values()) {
                    if(getFoodCount(value.getFoodType()) < farms.stream().flatMap(farm -> farm.getAnimals().stream()).filter(animal -> animal.getType() == value)
                            .mapToInt(animal -> animal.getFoodPerWeek() * 52).sum()){
                        ok = false;
                    }
                }
                if(ok){
                    System.out.println("**********************************************");
                    System.out.println("* Brawo, osiagnales status rolnika doskonalego");
                    System.out.println("* Udalo Ci sie to osiagnac w " + week + " tygodni");
                    System.out.println("**********************************************");
                    endGame = true;
                }
            }
        }

    }

    private int getFoodCount(Item foodType) {
        return farms.stream().flatMap(farm -> farm.getBuildings().stream())
                .filter(building -> building instanceof Barn).mapToInt(barn -> ((Barn) barn).getItem(foodType)).sum();
    }

    private ArableLand selectArableLand(List<ArableLand> arableLands, boolean sell) {
        int i;
        do {
            System.out.println("0. Cofnij");
            i = 1;
            for (ArableLand land : arableLands) {
                System.out.println(i + ". " + land.toString(week) + (sell ? (" cena: " + land.getSellPrice()) : (" cena: " + land.getBuyPrice())));
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
