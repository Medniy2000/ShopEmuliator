package nonCom.testTask.ShopEmuliator.model;

import java.util.Objects;

/**
 * Created by medniy on 29.09.2017.
 */
public class SoftDrink extends Drink {

    private SoftDrinkGroup softDrinkGroup;

    private String contains;

    public SoftDrink(String name, Integer purchasePrice, SoftDrinkGroup softDrinkGroup, Float capacity, String contains,Integer availablePcs) {
        super(name, purchasePrice, capacity, availablePcs);
        this.softDrinkGroup = softDrinkGroup;
        this.contains = contains;
    }

    public SoftDrinkGroup getSoftDrinkGroup() {
        return softDrinkGroup;
    }

    public void setSoftDrinkGroup(SoftDrinkGroup softDrinkGroup) {
        this.softDrinkGroup = softDrinkGroup;
    }

    public String getContains() {
        return contains;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SoftDrink)) return false;
        if (!super.equals(o)) return false;
        SoftDrink softDrink = (SoftDrink) o;
        return Objects.equals(softDrinkGroup, softDrink.softDrinkGroup) &&
                Objects.equals(contains, softDrink.contains);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), softDrinkGroup, contains);
    }

    @Override
    public String toString() {
        return "SoftDrink{" +
                "name=" + name +
                ", purchasePrice=" + purchasePrice +
                ", softDrinkGroup=" + softDrinkGroup +
                ", capacity="+ capacity +
                ", contains=" + contains +
                ", availablePcs=" + availablePcs +
                '}';
    }
}
