package nonCom.testTask.ShopEmuliator;

import nonCom.testTask.ShopEmuliator.production.Drink;
import nonCom.testTask.ShopEmuliator.utils.CsvHelper;

import java.util.List;

/**
 * Created by medniy on 09.10.2017.
 */
public class Store {
    private static Store ourInstance = null;

    private List<Drink> availableDrinks;

    public static Store getInstance() {
        if (ourInstance == null){
            ourInstance = new Store();
        }
        return ourInstance;
    }

    private Store() {
        this.availableDrinks = CsvHelper.getInstance().readDrinksFromCSV();

    }

    public synchronized List<Drink> getAvailableDrinks() {
        return availableDrinks;
    }
}
