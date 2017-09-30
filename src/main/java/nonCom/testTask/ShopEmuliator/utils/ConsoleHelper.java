package nonCom.testTask.ShopEmuliator.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by medniy on 30.09.2017.
 */
public class ConsoleHelper {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString(){
        String rezult=null;
        try {
            rezult = reader.readLine();
        }catch (IOException e){
            ConsoleHelper.writeMessage(e.getMessage());
        }
        return  rezult;
    }

}