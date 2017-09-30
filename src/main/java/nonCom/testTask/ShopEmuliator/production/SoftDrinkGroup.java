package nonCom.testTask.ShopEmuliator.production;

/**
 * Created by medniy on 29.09.2017.
 */
public enum SoftDrinkGroup {
    MINERAL_WATERS,
    JUICES,
    COCTAILS,
    LEMONADE,
    OTHER_DRINK;

    public static boolean isSoftDrink(String softDrink) {
        if (MINERAL_WATERS.name().equals(softDrink)){
            return true;
        }else if(JUICES.name().equals(softDrink)){
            return true;
        }else if(COCTAILS.name().equals(softDrink)){
            return true;
        }else if(LEMONADE.name().equals(softDrink)){
            return true;
        }else if (OTHER_DRINK.name().equals(softDrink)){
            return true;
        }
        return false;

    }
}
