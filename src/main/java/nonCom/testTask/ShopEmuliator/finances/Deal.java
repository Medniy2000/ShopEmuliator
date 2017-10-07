package nonCom.testTask.ShopEmuliator.finances;

import nonCom.testTask.ShopEmuliator.production.Drink;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by medniy on 01.10.2017.
 */
public class Deal {

    private static final SimpleDateFormat format = new SimpleDateFormat("MM-dd-YYYY hh:mm");
    private Date date;

    private Double totalPrice;

    // key=Drink, list: index0 = count, index1 = price
    private Map<Drink, List<Number>> shopBucket;

    private List<PricePolitic> pricePolitics;

    public Deal(Double totalPrice, Map<Drink, List<Number>> shopBucket, List<PricePolitic> pricePolitics) {
        this.totalPrice = (double) Math.round(totalPrice * 100) / 100;
        this.shopBucket = shopBucket;
        this.pricePolitics = pricePolitics;
        this.date = new Date();
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
                "   DATE: " + format.format(date) + System.lineSeparator() +
                "   BUCKET:" + System.lineSeparator() + bucketToString() +
                "   PRICE POLITICS:" + System.lineSeparator() + pricePoliticsToString() +
                "   TOTAL PRICE=" + totalPrice + System.lineSeparator();
    }
}