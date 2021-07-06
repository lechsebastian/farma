package pl.lech;

import pl.lech.data.Animal;
import pl.lech.data.ArableLand;
import pl.lech.data.Crop;
import pl.lech.data.Farm;

import java.util.*;

public class FarmGame {
    private static final int START_FARMS = 5;

    private final int money = 2000;
    private final int week = 1;
    private boolean endGame = false;
    private final Calendar currentDate = new GregorianCalendar();
    private final List<Farm> farms = new ArrayList<>();
    private final List<ArableLand> arableLands = new ArrayList<>();
    private final List<Animal> animals = new ArrayList<>();
    private final List<Crop> crops = new ArrayList<>();

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
            switch (MenuEntries.values()[choice-1]) {
                case BuyFarm:
                    continue;
                case ShowCrops:
                    continue;
                case PlantCrops:
                    continue;
                case ShowStocks:
                    continue;
                case BuyBuilding:
                    continue;
                case ShowAnimals:
                    continue;
                case HarvestCrops:
                    //sprzedajemy albo przechowujemy (to sprawdza stodole)
                    continue;
                case BuyAnimalCrop:
                    continue;
                case SellAnimalCrop:
                    continue;
                case ArableLandMenu:
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
            //rośliny rosną, zwierzęta przybierają na masie
            //istnieje pewna, niewielka szansa, że zwierzęta się rozmnożą jeżeli posiada więcej niż jedno
            //ponosisz koszty ochrony roślin przed szkodnikami
            //jeżeli masz kury/krowy/owce dostajesz pieniądze za jajka albo mleko
            //zwierzęta wcinają paszę, jeśli masz dla nich odłożone plony to w pierwszej kolejności ze stodoły, jeżeli nie to musisz je kupić.

            //Jeżeli skończą się pieniądze:
            //zwierzęta zaczynają chudnąć
            //w każdym tygodniu istnieje niewielkie ryzyko, że robaki zjedzą plony na polach

            this.currentDate.add(Calendar.WEEK_OF_YEAR, 1);
        }

    }

    public static void main(String[] args) {
        new FarmGame().startGame();
    }
}
