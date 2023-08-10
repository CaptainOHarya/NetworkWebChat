import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Leonid Zulin
 * @date 09.08.2023 19:21
 */
public class LoggerTest {

    @Test
    public void logTest1() throws FileNotFoundException {
        String message = "Это строка для проверки!!!";
        Logger.getInstance().logWrite(message);
        boolean result = false;
        Scanner scanner = new Scanner(new File(Logger.getInstance().getLOG_DIR()));
        while (scanner.hasNextLine()) {
            if (scanner.nextLine().contains(message)) {
                result = true;
            }
        }
        Assertions.assertTrue(result);
    }

    @Test
    public void logTest2() throws FileNotFoundException {
        int leftLimit = 33; // letter '!'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        System.out.println(generatedString);
        String message = "Это заведомо случайный набор из 10-ти символов, которых точно не должно быть = " + generatedString;
        System.out.println(message);
        // Logger.getInstance().logWrite(message);
        boolean result = false;
        Scanner scanner = new Scanner(new File(Logger.getInstance().getLOG_DIR()));
        while (scanner.hasNextLine()) {
            if (scanner.nextLine().contains(message)) {
                result = true;
            }
        }
        Assertions.assertFalse(result);
    }

}
