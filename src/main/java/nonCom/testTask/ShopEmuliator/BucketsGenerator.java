package nonCom.testTask.ShopEmuliator;

import nonCom.testTask.ShopEmuliator.production.Drink;
import org.joda.time.DateTime;

import java.util.*;

import static nonCom.testTask.ShopEmuliator.Shop.SHOP_OPEN_HOUR;
import static nonCom.testTask.ShopEmuliator.Shop.DATE_TIME_ZONE;

/**
 * Created by medniy on 07.10.2017.
 */
public class BucketsGenerator extends Observable implements Runnable {
    private static final long BUCKET_CREATING_INTERVAL = initInterval();
    private List<Drink> availableDrinks;

    public BucketsGenerator(List<Drink> availableDrinks) {
        this.availableDrinks = availableDrinks;
    }

    @Override
    public void run() {
        if (availableDrinks.isEmpty()) return;
        try {

            while (!Thread.currentThread().isInterrupted()) {

                int bucketCount = getAmount(1, 10);
                long durationOfCreating = 0;

                for (int i = 0; i < bucketCount; i++) {

                    Map<Drink, Integer> bucket = generateBucket();
                    setChanged();
                    notifyObservers(bucket);

                    //from 5sec to 5min
                    int nextBucketInterval = getAmount(5000, 5000 * 60);
                    durationOfCreating += nextBucketInterval;
                    Thread.sleep(nextBucketInterval);
                }
                Thread.sleep(BUCKET_CREATING_INTERVAL - durationOfCreating);
            }
        } catch (InterruptedException e) {
        }
    }

//    private synchronized boolean isAvailable(Drink drink, int count) {
//
//        int index = availableDrinks.indexOf(drink);
//        Drink availableDrink = availableDrinks.get(index);
//        int available = availableDrink.getAvailablePcs();
//
//        if (available >= count) {
//            return true;
//        }
//        return false;
//    }

    private Map<Drink, Integer> generateBucket() {

        Map<Drink, Integer> result = new HashMap<>();
        int itemCount = getAmount(0, 10);

        while (itemCount > 0) {

            int drinkNumer = getAmount(0, availableDrinks.size() - 1);
            Drink drink = availableDrinks.get(drinkNumer);
            int count = getAmount(1, itemCount);

            if (isAvailable(drink,count)) {
                itemCount -= count;
                result.put(drink, count);
            }
        }
        return result;
    }
    private synchronized boolean isAvailable(Drink drink, int count) {

        int index = availableDrinks.indexOf(drink);
        Drink availableDrink = availableDrinks.get(index);
        int available = availableDrink.getAvailablePcs();

        if (available >= count) {
            return true;
        }
        return false;
    }

    private int getAmount(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    private static long initInterval() {

        Calendar c = Calendar.getInstance();

        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR, 1);

        return c.getTime().getTime();
    }

    public long initialDelay() {
        DateTime dateTime = new DateTime(DATE_TIME_ZONE);
        int shopOpenMinutes = SHOP_OPEN_HOUR *60+ 16;

        int minuteOfDay = dateTime.getMinuteOfDay();

        if (minuteOfDay <= shopOpenMinutes) {
           int result = shopOpenMinutes - minuteOfDay;
            return result;
        }

        return 24*60 - minuteOfDay + shopOpenMinutes;
    }

}
