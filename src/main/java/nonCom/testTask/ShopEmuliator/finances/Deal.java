package nonCom.testTask.ShopEmuliator.finances;

import nonCom.testTask.ShopEmuliator.production.Drink;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static nonCom.testTask.ShopEmuliator.Shop.DATE_TIME_ZONE;

/**
 * Created by medniy on 01.10.2017.
 */
public class Deal {

    private static final DateTimeFormatter format = DateTimeFormat.forPattern("MM-dd-YYYY hh:mm");
    private DateTime date;

    private Double totalPrice;

    // key=Drink, list: index0 = count, index1 = price
    private Map<Drink, List<Number>> shopBucket;

    private List<PricePolitic> pricePolitics;

    public Deal(Double totalPrice, Map<Drink, List<Number>> shopBucket, List<PricePolitic> pricePolitics) {
        this.totalPrice = (double) Math.round(totalPrice * 100) / 100;
        this.shopBucket = shopBucket;
        this.pricePolitics = pricePolitics;
        this.date = new DateTime(DATE_TIME_ZONE);
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    private String bucketToString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Drink, List<Number>> entry : shopBucket.entrySet()) {
            Drink drink = entry.getKey();
            List<Number> list = entry.getValue();
            result.append(drink).append(", ").append(list.get(0)).append("pcs, price: ").append(list.get(1).toString()).append(System.lineSeparator()).append("").toString();
        }
        return result.toString();
    }

    private String pricePoliticsToString() {
        StringBuilder result = new StringBuilder();
        for (PricePolitic pricePolitic : pricePolitics) {
            result.append(pricePolitic).append(System.lineSeparator()).toString();
        }
        return result.toString();
    }

    @Override
    public String toString() {
        return "DEAL:" + System.lineSeparator() +
                "   DATE: " + format.print(date) + System.lineSeparator() +
                "   BUCKET:" + System.lineSeparator() + bucketToString() +
                "   PRICE POLITICS:" + System.lineSeparator() + pricePoliticsToString() +
                "   TOTAL PRICE=" + totalPrice + System.lineSeparator();
    }
}