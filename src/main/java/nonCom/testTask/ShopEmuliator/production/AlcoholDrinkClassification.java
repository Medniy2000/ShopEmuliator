package nonCom.testTask.ShopEmuliator.production;

/**
 * Created by medniy on 29.09.2017.
 */
public enum AlcoholDrinkClassification {
    WINE,
    STRONG_ALCOHOL,
    BEER,
    LIQUOR;

    public static boolean isAlcoholDrink(String alchoholDrink) {
        if (WINE.name().equals(alchoholDrink)){
            return true;
        }else if (STRONG_ALCOHOL.name().equals(alchoholDrink)){
            return true;
        }else if (BEER.name().equals(alchoholDrink)){
            return true;
        }else if (LIQUOR.name().equals(alchoholDrink)){
            return true;
        }
        return false;
    }

}
