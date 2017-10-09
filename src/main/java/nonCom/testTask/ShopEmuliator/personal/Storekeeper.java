package nonCom.testTask.ShopEmuliator.personal;

import nonCom.testTask.ShopEmuliator.production.Drink;
import nonCom.testTask.ShopEmuliator.utils.CsvHelper;

import java.util.List;

/**
 * Created by medniy on 09.10.2017.
 */
public class Storekeeper {
    private static Storekeeper ourInstance = null;

    private List<Drink> availableDrinks;

    public static Storekeeper getInstance() {
        if (ourInstance == null){
            ourInstance = new Storekeeper();
        }
        return ourInstance;
    }

    private Storekeeper() {
        this.availableDrinks = CsvHelper.getInstance().readDrinksFromCSV();

    }

    public synchronized List<Drink> getAvailableDrinks() {
        return availableDrinks;
    }

    protected boolean saveDrinks(){
        return CsvHelper.getInstance().writeDrinksToCSV(availableDrinks);
    }
}
