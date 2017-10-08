package nonCom.testTask.ShopEmuliator.finances;

import nonCom.testTask.ShopEmuliator.production.Drink;
import nonCom.testTask.ShopEmuliator.utils.ConsoleHelper;

import java.util.*;

/**
 * Created by medniy on 01.10.2017.
 */
public class PayMaster extends Observable implements Observer {
    public static final PricePolitic STANDARD = new PricePolitic("STANDART", 10);
    public static final PricePolitic WEEKENDS = new PricePolitic("WEEKENDS", 15);
    public static final PricePolitic PERIOD = new PricePolitic("PERIOD 18:00-20.00", 8);
    public static final PricePolitic FROM_TWO_PIECES = new PricePolitic("FROM TWO PIECES OF GOODS", 7);

    private PaymentAccount paymentAccount = PaymentAccount.getInstance();

    public PayMaster() {

    }

    @Override
    public void update(Observable o, Object arg) {
        Map<Drink, Integer> bucket = (Map<Drink, Integer>) arg;
        if (!bucket.isEmpty()) {
            Deal deal = makeDeal(bucket);
            paymentAccount.deposit(deal.getTotalPrice());
            ConsoleHelper.writeMessage(deal.toString());
            // send deal in statistic
            setChanged();
            notifyObservers(deal);
        }
    }

    // key=Drink, value = count;
    public Deal makeDeal(Map<Drink, Integer> bucket) {

        // key=Drink, list: index0 = count, index1 = price
        Map<Drink, List<Number>> bucketAfterCalculation = new HashMap<>();
        List<PricePolitic> pricePolitics = new ArrayList<>();
        Double totalPrice = 0.0;

        boolean MoreThanTwo = isMoreThanTwo(bucket);
        if (MoreThanTwo) {
            pricePolitics.add(FROM_TWO_PIECES);
        }

        if (isWeekEnd()) {
            pricePolitics.add(WEEKENDS);
            bucketAfterCalculation = makeCalculation(bucket, 15, MoreThanTwo);
        } else if (isPeriod()) {
            pricePolitics.add(PERIOD);
            bucketAfterCalculation = makeCalculation(bucket, 8, MoreThanTwo);
        } else {
            pricePolitics.add(STANDARD);
            bucketAfterCalculation = makeCalculation(bucket, 8, MoreThanTwo);
        }

        totalPrice = calculateTotalPrice(bucketAfterCalculation);

        return new Deal(totalPrice, bucketAfterCalculation, pricePolitics);
    }

    private Map<Drink, List<Number>> makeCalculation(Map<Drink, Integer> bucket, float perCent, boolean moreThanTwo) {
        Map<Drink, List<Number>> result = new HashMap<>();
        Integer itemCount = 0;


        for (Map.Entry<Drink, Integer> entry : bucket.entrySet()) {

            Drink drink = entry.getKey();
            Integer count = entry.getValue();
            Double purchasePrice = drink.getPurchasePrice();
            Double delta = 0.0;
            Double price = 0.0;

            for (int i = 0; i < count; i++) {

                itemCount++;
                if (moreThanTwo && itemCount > 2) {
                    perCent = 7;
                }

                delta = (purchasePrice / 100) * perCent;
                Double itemPrice = purchasePrice += delta;
                price += itemPrice;
            }

            List<Number> value = new ArrayList<>();
            price = (double) Math.round(price * 100) / 100;
            value.add(0, count);
            value.add(1, price);

            result.put(drink, value);
        }

        return result;
    }

    private boolean isWeekEnd() {

        Date date = new Date();
        switch (date.getDay()) {
            //Sunday
            case 0:
                return true;
            //Saturday
            case 6:
                return true;

            default:
                return false;

        }
    }

    private boolean isMoreThanTwo(Map<Drink, Integer> bucket) {
        int count = 0;
        for (Map.Entry<Drink, Integer> entry : bucket.entrySet()) {

            int items = entry.getValue();
            for (int i = 0; i < items; i++) {

                if (count > 2) {
                    return true;
                }
                count++;
            }
        }
        return false;
    }

    private boolean isPeriod() {
        Date date = new Date();

        if (date.getHours() >= 18 && date.getHours() <= 20) {
            return false;
        }

        return false;

    }

    private Double calculateTotalPrice(Map<Drink, List<Number>> bucketAfterCalculation) {
        Double result = 0.0;

        for (Map.Entry<Drink, List<Number>> entry : bucketAfterCalculation.entrySet()) {
            List<Number> list = entry.getValue();
            result += list.get(1).doubleValue();
        }

        return result;
    }


}



