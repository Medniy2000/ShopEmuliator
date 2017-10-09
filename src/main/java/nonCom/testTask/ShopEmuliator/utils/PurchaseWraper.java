package nonCom.testTask.ShopEmuliator.utils;

import nonCom.testTask.ShopEmuliator.production.Drink;

import java.util.List;
import java.util.Map;

/**
 * Created by medniy on 09.10.2017.
 */
public class PurchaseWraper {
    Map<Drink, List<Number>> purchased;

    public PurchaseWraper(Map<Drink, List<Number>> purchased) {
        this.purchased = purchased;
    }

    public Map<Drink, List<Number>> getPurchased() {
        return purchased;
    }
}

