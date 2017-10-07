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
    private Map<Drink, List<Number>> shopBasket;
    private List<PricePerCent> perCentList;

    public Deal(Double totalPrice, Map<Drink, List<Number>> priceList, List<PricePerCent> perCentList) {
        this.totalPrice = totalPrice;
        this.shopBasket = priceList;
        this.perCentList = perCentList;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return "Deal{" +
                "date=" + format.format(date) +
                ", shopBasket=" + shopBasket +
                ", perCentList=" + perCentList +
                ",totalPrice=" + totalPrice +
                '}';
    }
}