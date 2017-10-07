package nonCom.testTask.ShopEmuliator.finances;

/**
 * Created by medniy on 01.10.2017.
 */
public class PricePerCent {

    private String name;
    private int perCent;

    public PricePerCent(String name, int perCent) {
        this.name = name;
        this.perCent = perCent;
    }

    public String getName() {
        return name;
    }

    public int getPerCent() {
        return perCent;
    }

    @Override
    public String toString() {
        return "PricePerCent{" +
                "name='" + name +
                ", perCent=" + perCent + "% from purchase price" +
                '}';
    }
}
