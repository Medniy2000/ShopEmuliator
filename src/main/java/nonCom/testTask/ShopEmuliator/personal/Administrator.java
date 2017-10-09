package nonCom.testTask.ShopEmuliator.personal;

import nonCom.testTask.ShopEmuliator.finances.PaymentAccount;
import nonCom.testTask.ShopEmuliator.production.Drink;
import nonCom.testTask.ShopEmuliator.utils.ConsoleHelper;
import nonCom.testTask.ShopEmuliator.utils.CsvHelper;

import java.util.*;


/**
 * Created by medniy on 07.10.2017.
 */
public class Administrator extends Observable implements Runnable {
    private static int PURCHASE_COUNT = 150;
    private static int AVAILABLE_PCS_LIMIT = 10;

    private PaymentAccount paymentAccount = PaymentAccount.getInstance();

    public Administrator() {

    }

    @Override
    public void run() {

        makePurchases();
        ConsoleHelper.writeMessage("Purchases Made!");
        sleep(1000);
        makeSaves();
        ConsoleHelper.writeMessage("All data saved!");

        if (isLastDayOfMonth()){
            //TODO save statistic in txt file
        }

    }

    private void makePurchases() {

        Map<Drink, List<Number>> purchased = new HashMap<>();

        for (Drink drink : Storekeeper.getInstance().getAvailableDrinks()) {

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
        setChanged();
        notifyObservers(purchased);
    }

    private void makeSaves() {
        //save money
        paymentAccount.saveMoney();

        sleep(400);

        // save available drinks
        Storekeeper.getInstance().saveDrinks();

        sleep(400);

        // save statistic in file
        StatisticManager.getInstance().saveStatistic();

        sleep(400);
    }

    private boolean isLastDayOfMonth() {
        int lastDayOfMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        int curentDayOfMonth = Calendar.DAY_OF_MONTH;

        return lastDayOfMonth>=curentDayOfMonth;
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
