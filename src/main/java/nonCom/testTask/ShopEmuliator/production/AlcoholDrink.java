package nonCom.testTask.ShopEmuliator.production;

import java.util.Objects;

/**
 * Created by medniy on 29.09.2017.
 */
public class AlcoholDrink extends Drink{

    private AlcoholDrinkClassification classification;

    private Float strength;

    public AlcoholDrink() {
    }

    public AlcoholDrink(String name, Double purchasePrice, AlcoholDrinkClassification classification, Float capacity, Float strength, Integer availablePcs) {
        super(name, purchasePrice, capacity, availablePcs);
        this.classification = classification;
        this.strength = strength;
    }

    public AlcoholDrinkClassification getClassification() {
        return classification;
    }

    public void setClassification(AlcoholDrinkClassification classification) {
        this.classification = classification;
    }

    public Float getStrength() {
        return strength;
    }

    public void setStrength(Float strength) {
        this.strength = strength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlcoholDrink)) return false;
        if (!super.equals(o)) return false;
        AlcoholDrink that = (AlcoholDrink) o;
        return Objects.equals(classification, that.classification) &&
                Objects.equals(strength, that.strength);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), classification, strength);
    }

    @Override
    public String toString() {
        return "AlcoholDrink{" +
                "name=" + name +
                ", purchasePrice=" + purchasePrice +
                ", classification=" + classification +
                ", capacity="+ capacity +
                ", strength=" + strength +
                ", availablePcs=" + availablePcs +
                '}';
    }
}
