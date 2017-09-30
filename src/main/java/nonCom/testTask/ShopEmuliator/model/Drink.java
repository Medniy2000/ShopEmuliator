package nonCom.testTask.ShopEmuliator.model;

import java.util.Objects;

/**
 * Created by medniy on 29.09.2017.
 */
public class Drink {

    protected String name;
    protected Double purchasePrice;
    protected Float capacity;
    protected Integer availablePcs;

    public Drink() {
    }

    public Drink( String name, Double purchasePrice, Float capacity, Integer availablePcs) {
        this.purchasePrice = purchasePrice;
        this.name = name;
        this.capacity = capacity;
        this.availablePcs = availablePcs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Float getCapacity() {
        return capacity;
    }

    public void setCapacity(Float capacity) {
        this.capacity = capacity;
    }

    public Integer getAvailablePcs() {
        return availablePcs;
    }

    public void setAvailablePcs(Integer availablePcs) {
        this.availablePcs = availablePcs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drink)) return false;
        Drink drink = (Drink) o;
        return Objects.equals(name, drink.name) &&
                Objects.equals(purchasePrice, drink.purchasePrice) &&
                Objects.equals(capacity, drink.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, purchasePrice, capacity);
    }

    @Override
    public String toString() {
        return "Drink{" +
                "name='" + name +
                ", purchasePrice=" + purchasePrice +
                ", capacity=" + capacity +
                ", availablePcs=" + availablePcs +
                '}';
    }
}
