package nonCom.testTask.ShopEmuliator;

import nonCom.testTask.ShopEmuliator.finances.PaymentAccount;
import nonCom.testTask.ShopEmuliator.production.Drink;
import nonCom.testTask.ShopEmuliator.utils.CsvHelper;
import nonCom.testTask.ShopEmuliator.utils.PurchaseWraper;
import org.joda.time.DateTime;

import java.util.*;

import static nonCom.testTask.ShopEmuliator.Shop.DATE_TIME_ZONE;


/**
 * Created by medniy on 07.10.2017.
 */
public class Administrator extends Observable implements Runnable {
    private static int PURCHASE_COUNT = 15;
    private static int AVAILABLE_PCS_LIMIT = 10;

    private PaymentAccount paymentAccount = PaymentAccount.getInstance();

    public Administrator() {

    }

    @Override
    public void run() {

        makePurchases();
        sleep(1000);
        makeSaves();

        if (isLastDayOfMonth()){
            //TODO save statistic in txt file
        }

    }

    private void makePurchases() {

        Map<Drink, List<Number>> purchased = new HashMap<>();

        for (Drink drink : Store.getInstance().getAvailableDrinks()) {

            if (drink.getAvailablePcs() <= AVAILABLE_PCS_LIMIT) {

                List<Number> value = new ArrayList<>();
                double purchaseCost = drink.getPurchasePrice() * PURCHASE_COUNT;

                if (paymentAccount.withdraw(purchaseCost)) {
                    drink.increaseAvailablePcs(PURCHASE_COUNT);
                    value.add(0, PURCHASE_COUNT);
                    value.add(1, purchaseCost);
                    purchased.put(drink, value);
                }
            }
        }
        // send purchases in statistic
        PurchaseWraper wraper = new PurchaseWraper(purchased);
        setChanged();
        notifyObservers(wraper);
    }

    private void makeSaves() {
        //save money
        paymentAccount.saveMoney();
        sleep(400);

        // save available drinks
        List<Drink> drinks = Store.getInstance().getAvailableDrinks();
        CsvHelper.getInstance().writeDrinksToCSV(drinks);

        sleep(400);

        // save statistic in file
        StatisticManager.getInstance().saveInCsv();

        sleep(400);
    }

    private boolean isLastDayOfMonth() {
        int lastDayOfMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        int curentDayOfMonth = Calendar.DAY_OF_MONTH;

        return lastDayOfMonth>=curentDayOfMonth;
    }

    private void sleep(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
