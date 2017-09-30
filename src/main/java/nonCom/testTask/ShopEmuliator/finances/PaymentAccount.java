package nonCom.testTask.ShopEmuliator.finances;

import nonCom.testTask.ShopEmuliator.utils.ConsoleHelper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by medniy on 30.09.2017.
 */
public class PaymentAccount {

    private static final String MONEY_FILE_PATH = Paths.get(".", "src/main/resources/finances", "AmountOfMoney.txt").normalize().toFile().getAbsolutePath();
    private static final Integer DEFAULT_MOMEY_AMOUNT = 1000;

    private static PaymentAccount ourInstance = new PaymentAccount();

    private volatile int amountOfMoney;

    public static PaymentAccount getInstance() {
        return ourInstance;
    }

    private PaymentAccount() {
        int valueFromFile = readAmmountOfMoneyFromFile();
        if (valueFromFile == -1) {
            amountOfMoney = DEFAULT_MOMEY_AMOUNT;
        } else {
            amountOfMoney = valueFromFile;
        }

    }

    private int readAmmountOfMoneyFromFile() {
        File file = new File(MONEY_FILE_PATH);
        String value1 = "";
        Integer value2 = -1;
        try {
            value1 = FileUtils.readFileToString(file);
            value2 = Integer.valueOf(value1);
        } catch (Exception e) {
            ConsoleHelper.writeMessage(e.getMessage());
        }

        return value1.equals("not used") ? -1 : value2;
    }

    public void deposit(int amount) {
        amountOfMoney += amount;
    }

    public boolean withdraw(int amount) {

        if (amountOfMoney >= amount) {
            amountOfMoney -= amount;
            return true;
        }

        return false;
    }

    public boolean saveMoney() {
        File file = new File(MONEY_FILE_PATH);
        try {
            FileUtils.writeStringToFile(file, String.valueOf(this.amountOfMoney));
        } catch (IOException e) {
            ConsoleHelper.writeMessage(e.getMessage());
            return false;
        }
        return true;
    }

    public int getAmountOfMoney() {
        return amountOfMoney;
    }
}
