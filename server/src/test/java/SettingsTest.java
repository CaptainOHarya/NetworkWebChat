import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Leonid Zulin
 * @date 09.08.2023 18:11
 */

public class SettingsTest {

    @Test
    public void getSettingsTest1() {
        int portActual = 11111;
        int portExpected = Settings.getSettings();
        Assertions.assertEquals(portExpected, portActual);
    }

    @Test
    public void getSettingsTest2() {
        int portActual = 0;
        int portExpected = Settings.getSettings();
        try (BufferedReader reader = new BufferedReader(new FileReader(Settings.SETTINGS_DIR))) {
            portActual = Integer.parseInt(reader.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(portExpected, portActual);
    }

    @Test
    public void getSettingsTest3() {
        int portActual = 9999;
        int portExpected = Settings.getSettings();
        Assertions.assertNotEquals(portExpected, portActual);
    }


}
