package nonCom.testTask.ShopEmuliator;

import nonCom.testTask.ShopEmuliator.finances.Deal;
import nonCom.testTask.ShopEmuliator.finances.PayMaster;
import nonCom.testTask.ShopEmuliator.production.Drink;
import nonCom.testTask.ShopEmuliator.utils.CsvStatisticHelper;
import nonCom.testTask.ShopEmuliator.utils.PurchaseWraper;

import java.util.*;

import static nonCom.testTask.ShopEmuliator.utils.CsvStatisticHelper.CSV_PURCHASES_FILE_PATH;
import static nonCom.testTask.ShopEmuliator.utils.CsvStatisticHelper.CSV_SALES_FILE_PATH;

/**
 * Created by medniy on 08.10.2017.
 */
public class StatisticManager implements Observer {

    private static StatisticManager ourInstance = null;

    private CsvStatisticHelper csvStatisticHelper = CsvStatisticHelper.getInstance();


    // key=Drink, list: index0 = sales count, index1 = profit
    private Map<Drink, List<Number>> sales;
    // key=Drink, list: index0 = purchases count, index1 = purchase losses
    private Map<Drink, List<Number>> purchases;

    public static StatisticManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new StatisticManager();
        }
        return ourInstance;
    }

    private StatisticManager() {
        readFromCsv();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof PayMaster) {
            Deal deal = (Deal) arg;
            putInNote(deal);
        } else if (o instanceof Administrator) {
            PurchaseWraper wraper = (PurchaseWraper) arg;
            Map<Drink, List<Number>> purchaced = wraper.getPurchased();
            addToPurchases(purchaced);
        }

    }

    public void saveInCsv() {
        csvStatisticHelper.writeStatisticToCSV(sales, CSV_SALES_FILE_PATH, "PROFIT");
        csvStatisticHelper.writeStatisticToCSV(purchases, CSV_PURCHASES_FILE_PATH, "PURCHASE_LOSES");
    }

    private void readFromCsv() {
        if (sales == null && purchases == null) {

            Map<Drink, List<Number>> m1 = csvStatisticHelper.readStatisticFromCSV(CSV_SALES_FILE_PATH, "PROFIT");
            Map<Drink, List<Number>> m2 = csvStatisticHelper.readStatisticFromCSV(CSV_PURCHASES_FILE_PATH, "PURCHASE_LOSES");

            this.sales = m1.isEmpty() ? new HashMap<Drink, List<Number>>() : m1;
            this.purchases = m2.isEmpty() ? new HashMap<Drink, List<Number>>() : m2;
        }
    }

    public boolean putInNote(Deal deal) {
        if (deal != null) {
            Map<Drink, List<Number>> bucket = deal.getShopBucket();
            return updateAvailableDrinks(bucket);
        }
        return false;
    }

    // INPUT: key=Drink, list: index0 = count, index1 = price
    private boolean updateAvailableDrinks(Map<Drink, List<Number>> bucket) {

        for (Map.Entry<Drink, List<Number>> entry : bucket.entrySet()) {

            List<Number> list = entry.getValue();
            Drink drink = entry.getKey();
            int count = list.get(0).intValue();
            decrease(drink, count);
            addToSales(drink, list);
        }
        return true;
    }

    private void addToSales(Drink drink, List<Number> countPrice) {
        if (!sales.containsKey(drink)) {
            sales.put(drink, countPrice);
        } else {
            List<Number> value = sales.get(drink);
            Integer newCount = value.get(0).intValue() + countPrice.get(0).intValue();
            Double newPrice = value.get(1).doubleValue() + countPrice.get(1).doubleValue();
            value.set(0, newCount);
            value.set(1, newPrice);
            sales.put(drink, value);
        }
    }

    private void addToPurchases(Map<Drink, List<Number>> purchased) {

        for (Map.Entry<Drink, List<Number>> entry : purchased.entrySet()) {

            Drink drink = entry.getKey();
            List<Number> countPrice = entry.getValue();

            if (!purchases.containsKey(drink)) {
                purchases.put(drink, countPrice);
            } else {

                List<Number> value = purchases.get(drink);

                Integer newCount = value.get(0).intValue() + countPrice.get(0).intValue();
                Double newPrice = value.get(1).doubleValue() + countPrice.get(1).doubleValue();

                value.set(0, newCount);
                value.set(1, newPrice);

                purchases.put(drink, value);
            }
        }
    }

    private boolean decrease(Drink drink, int count) {
        int index = Store.getInstance().getAvailableDrinks().indexOf(drink);
        Drink needUpdate = Store.getInstance().getAvailableDrinks().get(index);
        int available = needUpdate.getAvailablePcs();

        if (available >= count) {
            needUpdate.decreaseAvailablePcs(count);
            return true;
        }
        return false;
    }

    public Double getTotalProfitPerMonth() {
        return getProfitFromSalesPerMonth() - getLossesFromPurchasesPerMonth();
    }

    private Double getProfitFromSalesPerMonth() {
        Double result = 0.0;

        for (Map.Entry<Drink, List<Number>> entry : sales.entrySet()) {
            List<Number> list = entry.getValue();
            result += list.get(1).doubleValue();
        }
        return result;
    }

    private Double getLossesFromPurchasesPerMonth() {
        Double result = 0.0;

        for (Map.Entry<Drink, List<Number>> entry : purchases.entrySet()) {
            List<Number> list = entry.getValue();
            result += list.get(1).doubleValue();
        }
        return result;
    }


}
