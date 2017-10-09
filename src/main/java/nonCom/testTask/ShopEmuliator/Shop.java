package nonCom.testTask.ShopEmuliator;

import nonCom.testTask.ShopEmuliator.personal.Administrator;
import nonCom.testTask.ShopEmuliator.personal.PayMaster;
import nonCom.testTask.ShopEmuliator.personal.StatisticManager;
import org.joda.time.DateTimeZone;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by medniy on 07.10.2017.
 */
public class Shop {
    public static final int SHOP_OPEN_HOUR = 12;
    public static final int SHOP_ClOSE_HOUR = 21;
    public static final DateTimeZone DATE_TIME_ZONE = DateTimeZone.forID("Europe/Kiev");
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    public static void main(String[] args) {

        StatisticManager statisticManager = StatisticManager.getInstance();

        PayMaster payMaster = new PayMaster();

        BucketsGenerator generator = new BucketsGenerator();

        Administrator admin = new Administrator();

        admin.addObserver(statisticManager);
        payMaster.addObserver(statisticManager);
        generator.addObserver(payMaster);


        // scheduler must run generator at 8:00 add terminate after 21:00;
        ScheduledFuture<?> result = scheduler.schedule(generator, generator.initialDelay(), TimeUnit.MINUTES);
        int milisecondsDelay = (SHOP_ClOSE_HOUR - SHOP_OPEN_HOUR) * 60 * 60 * 1000;

        //for tests
        //ScheduledFuture<?> result = scheduler.schedule(generator, 0, TimeUnit.MINUTES);
        //int milisecondsDelay = 1000*60*15;

        if (!result.isDone()) {
            try {

                Thread.sleep(milisecondsDelay);
                result.cancel(true);
                // before shop closing  scheduler must run admin and he must save data;
                scheduler.schedule(admin, 0, TimeUnit.SECONDS);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
