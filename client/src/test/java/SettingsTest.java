import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Leonid Zulin
 * @date 09.08.2023 20:06
 */
public class SettingsTest {

    @Test
    public void getSettingsTestPort1() {
        int portActual = 11111;
        int portExpected = SettingsClient.getSettings().getRight();
        Assertions.assertEquals(portExpected, portActual);

    }

    @Test
    public void getSettingsTestPort2() {
        int portActual = 9999;
        int portExpected = SettingsClient.getSettings().getRight();
        Assertions.assertNotEquals(portExpected, portActual);

    }

    @Test
    public void getSettingsTestPort3() {
        int portActual = 0;
        int portExpected = SettingsClient.getSettings().getRight();
        try (BufferedReader reader = new BufferedReader(new FileReader(SettingsClient.SETTINGS_DIR))) {
            String inputData[] = reader.readLine().split(" ");
            portActual = Integer.parseInt(inputData[0]);

        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean isPortTheSame = portActual == portExpected;
        Assertions.assertTrue(isPortTheSame);

    }

    @Test
    public void getSettingsTestHost1() {
        String hostActual = null;
        String hostExpected = SettingsClient.getSettings().getLeft();
        try (BufferedReader reader = new BufferedReader(new FileReader(SettingsClient.SETTINGS_DIR))) {
            String inputData[] = reader.readLine().split(" ");
            hostActual = inputData[1];

        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean isHostTheSame = hostActual.equals(hostExpected);
        Assertions.assertTrue(isHostTheSame);

    }

    @Test
    public void getSettingsTestHost2() {
        String hostActual = "127.0.23.9";
        String hostExpected = SettingsClient.getSettings().getLeft();
        Assertions.assertNotEquals(hostExpected, hostActual);

    }

    @Test
    public void getSettingsTestHost3() {
        String hostActual = "127.0.0.1";
        String hostExpected = SettingsClient.getSettings().getLeft();
        Assertions.assertEquals(hostExpected, hostActual);

    }

}
