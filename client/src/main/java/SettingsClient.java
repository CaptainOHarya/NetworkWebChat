import org.apache.commons.lang3.tuple.Pair;

import java.io.*;

/**
 * @author Leonid Zulin
 * @date 20.07.2023 20:11
 */
public class SettingsClient {

    static String userName = System.getProperty("user.name");
    static String systemFS = System.getProperty("file.separator");
    static int userPort;
    static final int DEFAULT_PORT = 11111;
    static String userHost;
    static final String DEFAULT_HOST = "127.0.0.1"; // т.е. localhost
    static final String MAIN_DIR = "C:" + systemFS + "Users" + systemFS + userName + systemFS
            + "Documents" + systemFS + "Online chat";
    static final String SETTINGS_DIR = MAIN_DIR + systemFS + "settings.txt";
    static final boolean APP_TO_END = false;

    public static Pair<String, Integer> getSettings() {
        File settingsUser = new File(SETTINGS_DIR);
        // если файл не создан, то в первый раз создадим его и запишем туда наши данные
        if (!settingsUser.exists()) {
            makeUserDir(MAIN_DIR);

            try {
                settingsUser.createNewFile();
            } catch (IOException e) {
                System.err.println("Файл \"settings.txt\" создать нельзя, ошибка " + e);
                LoggerClient.getInstance().logWrite("Файл \"settings.txt\" создать нельзя, ошибка " + e);
            }
            try (FileWriter writer = new FileWriter(SETTINGS_DIR, APP_TO_END)) {
                writer.write(Integer.toString(DEFAULT_PORT) + " " + DEFAULT_HOST);
                userPort = DEFAULT_PORT;
                userHost = DEFAULT_HOST;
                //System.out.println("Записанный порт =  " + userPort + ", " + " записанный хост = " + userHost);
                LoggerClient.getInstance().logWrite("Записанный порт =  " + userPort + ", " + " записанный хост = " + userHost);
            } catch (IOException e) {
                System.err.println("Не удаётся осуществить запись в файл \"settings.txt\",  ошибка " + e);
                LoggerClient.getInstance().logWrite("Не удаётся осуществить запись в файл \"settings.txt\",  ошибка " + e);
            }

        } else { // если файл с данными уже есть, то просто считаем
            try (BufferedReader reader = new BufferedReader(new FileReader(SETTINGS_DIR))) {
                String data[] = reader.readLine().split(" ");
                userPort = Integer.parseInt(data[0]);
                userHost = data[1];
                //System.out.println("Считанный порт =  " + userPort + ", " + " считанный хост = " + userHost);
                LoggerClient.getInstance().logWrite("Считанный порт =  " + userPort + ", " + " считанный хост = " + userHost);

            } catch (IOException e) {
                System.err.println("Не удаётся осуществить чтение из файла \"settings.txt\",  ошибка " + e);
                LoggerClient.getInstance().logWrite("Не удаётся осуществить чтение из файла \"settings.txt\",  ошибка " + e);
            }

        }

        return Pair.of(userHost, userPort);
    }

    protected static void makeUserDir(String path) {
        File creatureUserDir = new File(path);
        if (creatureUserDir.mkdir()) {
            System.out.println("Создана папка " + path);
            LoggerClient.getInstance().logWrite("Создана папка " + path);
        }

    }
}
