package nonCom.testTask.ShopEmuliator.utils;

import nonCom.testTask.ShopEmuliator.production.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.joda.time.DateTime;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormatSymbols;
import java.util.*;

import static nonCom.testTask.ShopEmuliator.Shop.DATE_TIME_ZONE;

/**
 * Created by medniy on 08.10.2017.
 */
public class CsvStatisticHelper {
    private static final CSVFormat DRINKS_FORMAT = CSVFormat.RFC4180.withHeader().withDelimiter(',');
    private static DateTime dateTime = new DateTime(DATE_TIME_ZONE);
    private static String date = dateTime.getYear() + "X" + getMonthForInt(dateTime.getMonthOfYear());
    private static  String SALES_FILE_NAME = date.concat("XSales.csv");
    private static  String PURCHASES_FILE_NAME = date.concat("XPurchases.csv");
    public  static  String CSV_SALES_FILE_PATH = Paths.get(".", "src/main/resources/statistic", SALES_FILE_NAME).normalize().toFile().getAbsolutePath();
    public  static  String CSV_PURCHASES_FILE_PATH = Paths.get(".", "src/main/resources/statistic", PURCHASES_FILE_NAME).normalize().toFile().getAbsolutePath();

    private static CsvStatisticHelper ourInstance = new CsvStatisticHelper();

    public static CsvStatisticHelper getInstance() {
        return ourInstance;
    }

    private CsvStatisticHelper() {
    }

    public Map<Drink, List<Number>> readStatisticFromCSV(String csvFilePath, String statisticType){

        File file = new File(csvFilePath);
        createFile(file);

        Map<Drink, List<Number>> result  = new HashMap<>();

        try (CSVParser parser = new CSVParser(new FileReader(file), DRINKS_FORMAT)) {
            for (CSVRecord record : parser) {

                String entityType = record.get(2);

                List<Number> value = new ArrayList<>();

                if (AlcoholDrinkClassification.isAlcoholDrink(entityType)) {

                    AlcoholDrink alcoholDrink = new AlcoholDrink();

                    alcoholDrink.setName(record.get(0));
                    alcoholDrink.setPurchasePrice(Double.valueOf(record.get(1)));
                    alcoholDrink.setClassification(AlcoholDrinkClassification.valueOf(record.get(2)));
                    alcoholDrink.setCapacity(Float.valueOf(record.get(3)));
                    alcoholDrink.setStrength(Float.valueOf(record.get(4)));
                    alcoholDrink.setAvailablePcs(Integer.valueOf(record.get(5)));

                    String stringCount = record.get(6).replaceAll(" COUNT: ","");
                    Integer count = Integer.valueOf(stringCount);

                    String stringX = record.get(7).replaceAll(" "+statisticType+": ","");
                    Double x = Double.valueOf(stringX);

                    value.add(0,count);
                    value.add(1,x);

                    result.put(alcoholDrink,value);


                } else if (SoftDrinkGroup.isSoftDrink(entityType)) {

                    SoftDrink softDrink = new SoftDrink();

                    softDrink.setName(String.valueOf(record.get(0)));
                    softDrink.setPurchasePrice(Double.valueOf(record.get(1)));
                    softDrink.setSoftDrinkGroup(SoftDrinkGroup.valueOf(record.get(2)));
                    softDrink.setCapacity(Float.valueOf(record.get(3)));
                    softDrink.setContains(String.valueOf(record.get(4)));
                    softDrink.setAvailablePcs(Integer.valueOf(record.get(5)));

                    String stringCount = record.get(6).replaceAll(" COUNT: ","");
                    Integer count = Integer.valueOf(stringCount);

                    String stringX = record.get(7).replaceAll(" "+statisticType+": ","");
                    Double x = Double.valueOf(stringX);

                    value.add(0,count);
                    value.add(1,x);

                    value.add(0,count);
                    value.add(1,x);

                    result.put(softDrink, value);
                }
            }
        } catch (IOException e) {
            ConsoleHelper.writeMessage(e.getMessage());
        }
        return result;
    }

    public boolean writeStatisticToCSV(Map<Drink, List<Number>> statisticData, String csvFilePath, String statisticType) {
        boolean result = true;

        File file = new File(csvFilePath);
        createFile(file);

        try (FileWriter fileWriter = new FileWriter(file);
             CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, DRINKS_FORMAT)) {
            for (Map.Entry<Drink, List<Number>> entry : statisticData.entrySet()) {

                Drink drink = entry.getKey();
                List<Number> value = entry.getValue();

                List data = new ArrayList<>();
                if (drink instanceof SoftDrink) {

                    data.add(drink.getName());
                    data.add(drink.getPurchasePrice().toString());
                    data.add(((SoftDrink) drink).getSoftDrinkGroup().toString());
                    data.add(drink.getCapacity().toString());
                    data.add(((SoftDrink) drink).getContains());
                    data.add(drink.getAvailablePcs().toString());

                } else if (drink instanceof AlcoholDrink) {

                    data.add(drink.getName());
                    data.add(drink.getPurchasePrice().toString());
                    data.add(((AlcoholDrink) drink).getClassification().toString());
                    data.add(drink.getCapacity().toString());
                    data.add(((AlcoholDrink) drink).getStrength().toString());
                    data.add(drink.getAvailablePcs().toString());

                }
                     data.add(" COUNT: ".concat(value.get(0).toString()));
                     data.add(" "+statisticType+": ".concat(value.get(1).toString()));

                csvFilePrinter.printRecord(data);

            }
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            result = false;
            ConsoleHelper.writeMessage(e.getMessage());
        }
        return result;
    }

    private static String getMonthForInt(int num) {
        String month = "wrong";
        num -= 1;
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.ENGLISH);
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }

    private boolean createFile(File file){
        if (!file.exists()){
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
