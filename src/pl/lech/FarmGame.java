package pl.lech;

import pl.lech.data.Animal;
import pl.lech.data.ArableLand;
import pl.lech.data.Crop;
import pl.lech.data.Farm;

import java.util.*;

public class FarmGame {
    private static final int START_FARMS = 5;

    private int money = 500_000;
    private int week = 1;
    private boolean endGame = false;
    private final Calendar currentDate = new GregorianCalendar();
    private final List<Farm> farms = new ArrayList<>();

    private final List<Farm> availableFarms = new ArrayList<>();

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
            //availableFarms.add()
        }
    }

    private void startGame() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random(System.currentTimeMillis());
        while (!this.endGame) {
            System.out.println("Data: rok: " + this.currentDate.get(Calendar.YEAR) + ", tydzień: " + this.currentDate.get(Calendar.WEEK_OF_YEAR));
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
                case BuyFarm:
                    //todo: implement
                    continue;
                case ShowCrops:
                    for (Farm farm : farms) {
                        System.out.println("Farma o nazwie: " + farm.getName());
                        for (ArableLand arableLand : farm.getArableLands()) {
                            arableLand.getCrop().ifPresent(crop -> {
                                System.out.println(" - " + crop.toString(week));
                            });
                        }
                    }
                    continue;
                case PlantCrops:
                    //todo: implement
                    continue;
                case ShowStocks:
                    //todo: implement
                    continue;
                case BuyBuilding:
                    //todo: implement
                    continue;
                case ShowAnimals:
                    for (Farm farm : farms) {
                        System.out.println("Farma o nazwie: " + farm.getName());
                        for (Animal animal : farm.getAnimals()) {
                            System.out.println(" - " + animal.toString(week));
                        }
                    }
                    continue;
                case HarvestCrops:
                    //todo: implement
                    //sprzedajemy albo przechowujemy (to sprawdza stodole)
                    continue;
                case BuyAnimalCrop:
                    //todo: implement
                    continue;
                case SellAnimalCrop:
                    //todo: implement
                    continue;
                case ArableLandMenu:
                    //todo: implement
                    continue;
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
                        if (random.nextFloat() < 0.001f) {
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
        }

    }

    public static void main(String[] args) {
        new FarmGame().startGame();
    }
}