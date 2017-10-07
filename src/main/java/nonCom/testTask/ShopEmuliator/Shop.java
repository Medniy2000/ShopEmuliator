package nonCom.testTask.ShopEmuliator;

import nonCom.testTask.ShopEmuliator.finances.PayMaster;
import nonCom.testTask.ShopEmuliator.production.Drink;
import nonCom.testTask.ShopEmuliator.utils.CsvHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by medniy on 07.10.2017.
 */
public class Shop {
    public static List<Drink> availableDrinks = new ArrayList<>(CsvHelper.getInstance().readDrinksFromCSV());

    public static void main(String[] args) {

        PayMaster payMaster = new PayMaster(availableDrinks);
        BucketsGenerator generator = new BucketsGenerator(availableDrinks);
        generator.addObserver(payMaster);
        generator.run();


    }

}
