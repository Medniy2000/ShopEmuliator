package nonCom.testTask.ShopEmuliator;

import nonCom.testTask.ShopEmuliator.finances.PayMaster;
import nonCom.testTask.ShopEmuliator.production.Drink;
import nonCom.testTask.ShopEmuliator.utils.CsvHelper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.DateFormatSymbols;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by medniy on 07.10.2017.
 */
public class Shop {
    public static final int SHOP_OPEN_HOUR = 23;
    public static final int SHOP_ClOSE_HOUR = 24;
    public static  final DateTimeZone DATE_TIME_ZONE = DateTimeZone.forID("Europe/Kiev");
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static List<Drink> availableDrinks = new ArrayList<>(CsvHelper.getInstance().readDrinksFromCSV());

    public static void main(String[] args) {

        StatisticManager statisticManager = StatisticManager.getInstance(availableDrinks);
        PayMaster payMaster = new PayMaster();


        BucketsGenerator generator = new BucketsGenerator(availableDrinks);
        payMaster.addObserver(statisticManager);
        generator.addObserver(payMaster);

        // scheduler must run generator at 8:00 add terminate after 21:00;
        scheduler.scheduleAtFixedRate(generator, generator.initialDelay(), SHOP_ClOSE_HOUR-SHOP_OPEN_HOUR, TimeUnit.MINUTES);

    }

}
