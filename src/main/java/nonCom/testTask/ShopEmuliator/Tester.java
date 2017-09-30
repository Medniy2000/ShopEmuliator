package nonCom.testTask.ShopEmuliator;

import nonCom.testTask.ShopEmuliator.finances.PaymentAccount;
import nonCom.testTask.ShopEmuliator.production.*;
import nonCom.testTask.ShopEmuliator.utils.CsvHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by medniy on 30.09.2017.
 */
public class Tester {
    public static void main(String[] args) {
        CsvHelper csvHelper = CsvHelper.getInstance();

        SoftDrink softDrink1 = new SoftDrink("Живчик", 12.5, SoftDrinkGroup.LEMONADE, 0.5f, "вода, сахар", 60);
        SoftDrink softDrink2 = new SoftDrink("Фанта", 18.0, SoftDrinkGroup.LEMONADE, 1.0f, "вода, сахар, сок", 90);
        SoftDrink softDrink3 = new SoftDrink("Бананово-молочный коктейль", 10.4, SoftDrinkGroup.COCTAILS, 0.4f, "молоко, сахар, частицы банана, вода", 53);
        SoftDrink softDrink4 = new SoftDrink("Енерджи бум Плюс", 24.15, SoftDrinkGroup.OTHER_DRINK, 0.33f, "вода, лимонная кислота, ароматизатор Яблоко, Е-345, Е-120, Е-630, Е-632, краситель Вишня", 78);
        AlcoholDrink alcoholDrink1 = new AlcoholDrink("Пиво Одесское Новое",13.25, AlcoholDrinkClassification.BEER,0.5f,4.3f,120);
        AlcoholDrink alcoholDrink2 = new AlcoholDrink("Мартини Биссе",205.00, AlcoholDrinkClassification.LIQUOR,1.00f,13f,12);
        AlcoholDrink alcoholDrink3 = new AlcoholDrink("Два моря",195.00, AlcoholDrinkClassification.WINE,0.75f,12f,10);

        List<Drink> drinks = new ArrayList<>();
        drinks.add(softDrink1);
        drinks.add(softDrink2);
        drinks.add(softDrink3);
        drinks.add(softDrink4);
        drinks.add(alcoholDrink1);
        drinks.add(alcoholDrink2);
        drinks.add(alcoholDrink3);



        csvHelper.writeDrinksToCSV(drinks);

        for(Drink drink : csvHelper.readDrinksFromCSV()){
            System.out.println(drink);
        }

        PaymentAccount paymentAccount = PaymentAccount.getInstance();

        System.out.println(paymentAccount.getAmountOfMoney());
        paymentAccount.saveMoney();
    }
}
