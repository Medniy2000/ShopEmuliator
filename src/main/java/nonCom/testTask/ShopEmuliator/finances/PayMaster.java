package nonCom.testTask.ShopEmuliator.finances;

import nonCom.testTask.ShopEmuliator.production.Drink;

import java.util.*;

/**
 * Created by medniy on 01.10.2017.
 */
public class PayMaster {
    public static final PricePerCent STANDARD = new PricePerCent("STANDART", 10);
    public static final PricePerCent WEEKENDS = new PricePerCent("WEEKENDS", 15);
    public static final PricePerCent PERIOD = new PricePerCent("PERIOD 18:00-20.00", 8);
    public static final PricePerCent FROM_TWO_PIECES = new PricePerCent("FROM TWO PIECES OF GOODS", 7);

    private PaymentAccount paymentAccount = PaymentAccount.getInstance();

    // key=Drink, value = count;
    public Deal makeDeal(Map<Drink, Integer> shopingBasket) {

        // key=Drink, list: index0 = count, index1 = price
        Map<Drink, List<Number>> basketAfterCalculation = new TreeMap<>();
        List<PricePerCent> perCentList = new ArrayList<>();
        Double totalPrice = 0.0;

        boolean  MoreThanTwo = isMoreThanTwo(shopingBasket);
        if(MoreThanTwo){
            perCentList.add(FROM_TWO_PIECES);
        }


        if (isWeekEnd()){
            perCentList.add(WEEKENDS);
            basketAfterCalculation = makeCalculation(shopingBasket,15, MoreThanTwo);
        }else if (isPeriod()){
            perCentList.add(PERIOD);
            basketAfterCalculation = makeCalculation(shopingBasket,8, MoreThanTwo);
        }else {
            perCentList.add(STANDARD);
            basketAfterCalculation = makeCalculation(shopingBasket,8, MoreThanTwo);
        }

        totalPrice = calculateTotalPrice(basketAfterCalculation);

        return new Deal(totalPrice,basketAfterCalculation,perCentList);
    }

    private Map<Drink, List<Number>> makeCalculation(Map<Drink, Integer> shopingBasket, float perCent, boolean moreThanTwo) {
        Map<Drink, List<Number>> result = new HashMap<>();
        Integer itemCount = 0;


        for (Map.Entry<Drink, Integer> entry : shopingBasket.entrySet()) {

            Drink drink = entry.getKey();
            Integer count = entry.getValue();
            Double purchasePrice = drink.getPurchasePrice();
            Double delta = 0.0;
            Double price = 0.0;

            for (int i = 0; i<count;i++) {

                itemCount++;
                if (moreThanTwo && itemCount > 2) {
                    perCent = 7;
                }

                delta = (purchasePrice / 100) * perCent;
                Double itemPrice = purchasePrice += delta;
                price+=itemPrice;
            }

            List<Number> value = new ArrayList<>();

            value.add(0,count);
            value.add(1,price);

            result.put(drink, value);
        }

        return result.isEmpty() ? Collections.EMPTY_MAP : result;
    }

    private boolean isWeekEnd(){

        Date date = new Date();
        switch (date.getDay()){
            //Sunday
            case 0: return true;
            //Saturday
            case 6: return true;

            default:return false;

        }
    }

    private boolean isMoreThanTwo(Map<Drink, Integer> shopingBasket){
        int count = 0;
        for (Map.Entry<Drink, Integer> entry : shopingBasket.entrySet()) {

            int items = entry.getValue();
            for (int i=0;i<items;i++){

                if (count>2){
                    return true;
                }
                count++;
            }
        }
        return false;
    }

    private boolean isPeriod(){
        Date date = new Date();

        if (date.getHours()>=18 && date.getHours()<=20){
            return false;
        }

        return false;

    }

    private Double calculateTotalPrice(Map<Drink, List<Number>> basketAfterCalculation ){
        Double result = 0.0;

        for (Map.Entry<Drink, List<Number>> entry : basketAfterCalculation.entrySet()) {
            List<Number> list = entry.getValue();
            result += list.get(1).doubleValue();
        }

        return result;
    }
}



