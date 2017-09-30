package nonCom.testTask.ShopEmuliator.utils;

import nonCom.testTask.ShopEmuliator.model.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by medniy on 30.09.2017.
 */
public class CsvHelper {

    private static final CSVFormat DRINKS_FORMAT = CSVFormat.RFC4180.withHeader().withDelimiter(',');
    public static final String CSV_FILE_PATH = Paths.get(".", "src/main/resources/store", "drinks.csv").normalize().toFile().getAbsolutePath();

    private static CsvHelper ourInstance = new CsvHelper();

    private CsvHelper() {
    }

    public static CsvHelper getInstance() {
        return ourInstance;
    }

    public List<Drink> readDrinksFromCSV(String csvFilePath) {

        List<Drink> result = new ArrayList<>();

        try (CSVParser parser = new CSVParser(new FileReader(csvFilePath), DRINKS_FORMAT)) {
            for (CSVRecord record : parser) {

                String entityType = record.get(2);

                if (AlcoholDrinkClassification.isAlcoholDrink(entityType)) {

                    AlcoholDrink alcoholDrink = new AlcoholDrink();

                    alcoholDrink.setName(record.get(0));
                    alcoholDrink.setPurchasePrice(Double.valueOf(record.get(1)));
                    alcoholDrink.setClassification(AlcoholDrinkClassification.valueOf(record.get(2)));
                    alcoholDrink.setCapacity(Float.valueOf(record.get(3)));
                    alcoholDrink.setStrength(Float.valueOf(record.get(4)));
                    alcoholDrink.setAvailablePcs(Integer.valueOf(record.get(5)));

                    result.add(alcoholDrink);

                } else if (SoftDrinkGroup.isSoftDrink(entityType)) {

                    SoftDrink softDrink = new SoftDrink();

                    softDrink.setName(String.valueOf(record.get(0)));
                    softDrink.setPurchasePrice(Double.valueOf(record.get(1)));
                    softDrink.setSoftDrinkGroup(SoftDrinkGroup.valueOf(record.get(2)));
                    softDrink.setCapacity(Float.valueOf(record.get(3)));
                    softDrink.setContains(String.valueOf(record.get(4)));
                    softDrink.setAvailablePcs(Integer.valueOf(record.get(5)));

                    result.add(softDrink);

                }

            }

        } catch (IOException e) {
            ConsoleHelper.writeMessage(e.getMessage());
        }

        return result;
    }

    public boolean writeDrinksToCSV(List<Drink> drinks) {
        boolean result = true;
        try (FileWriter fileWriter = new FileWriter(CSV_FILE_PATH);
             CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, DRINKS_FORMAT)) {
            for (Drink drink : drinks) {
                List drinkData = new ArrayList<>();
                if (drink instanceof SoftDrink) {
                    drinkData.add(drink.getName());
                    drinkData.add(drink.getPurchasePrice().toString());
                    drinkData.add(((SoftDrink) drink).getSoftDrinkGroup().toString());
                    drinkData.add(drink.getCapacity().toString());
                    drinkData.add(((SoftDrink) drink).getContains());
                    drinkData.add(drink.getAvailablePcs().toString());
                } else if (drink instanceof AlcoholDrink) {
                    drinkData.add(drink.getName());
                    drinkData.add(drink.getPurchasePrice().toString());
                    drinkData.add(((AlcoholDrink) drink).getClassification().toString());
                    drinkData.add(drink.getCapacity().toString());
                    drinkData.add(((AlcoholDrink) drink).getStrength().toString());
                    drinkData.add(drink.getAvailablePcs().toString());
                }
                csvFilePrinter.printRecord(drinkData);

            }
            fileWriter.flush();
            fileWriter.close();


        } catch (IOException e) {
            result = false;
            ConsoleHelper.writeMessage(e.getMessage());
        }
        return result;
    }

}
