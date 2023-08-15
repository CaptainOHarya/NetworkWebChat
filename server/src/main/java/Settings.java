import java.io.*;

/**
 * @author Leonid Zulin
 * @date 06.07.2023 6:43
 */
public class Settings {
    // static String systemUserName = System.getProperty("user.name");
    static String systemUserHome = System.getProperty("user.home");
    static String systemFS = System.getProperty("file.separator");
    static int userPort;
    static final int PORT = 11111;
    //    static final String MAIN_DIR = "C:" + systemFS + "Users" + systemFS + systemUserName + systemFS
//            + "Documents" + systemFS + "SettingsServer";
    static final String MAIN_DIR = systemUserHome + systemFS + "SettingsServer";
    static final String SETTINGS_DIR = MAIN_DIR + systemFS + "settings.txt";
    static final boolean APP_TO_END = false;

    public static int getSettings() {
        File settingsCreateFile = new File(SETTINGS_DIR);

        if (!settingsCreateFile.exists()) {
            makeDir(MAIN_DIR);
            try {
                settingsCreateFile.createNewFile();
            } catch (IOException e) {
                System.err.println("Файл \"settings.txt\" создать нельзя, ошибка " + e);
                Logger.getInstance().logWrite("Файл \"settings.txt\" создать нельзя, ошибка " + e);
            }
            try (FileWriter writer = new FileWriter(SETTINGS_DIR, APP_TO_END)) {
                writer.write(Integer.toString(PORT));
            } catch (IOException e) {
                System.err.println("Не удаётся осуществить запись в файл \"settings.txt\",  ошибка " + e);
                Logger.getInstance().logWrite("Не удаётся осуществить запись в файл \"settings.txt\",  ошибка " + e);
            }
            // System.out.println("Записанный порт =  " + PORT);
            Logger.getInstance().logWrite("Записанный порт =  " + PORT);
            userPort = PORT;

        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(SETTINGS_DIR))) {
                userPort = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                System.err.println("Не удаётся осуществить чтение из файла \"settings.txt\",  ошибка " + e);
                Logger.getInstance().logWrite("Не удаётся осуществить чтение из файла \"settings.txt\",  ошибка " + e);
            }
            // System.out.println("Считанный порт =  " + PORT);
            Logger.getInstance().logWrite("Считанный порт =  " + PORT);
        }
        return userPort;
    }

    public static void makeDir(String path) {
        File creatureDir = new File(path);
        if (creatureDir.mkdir()) {
            System.out.println("Создана папка " + path);
            Logger.getInstance().logWrite("Создана папка " + path);
        }

    }

}
