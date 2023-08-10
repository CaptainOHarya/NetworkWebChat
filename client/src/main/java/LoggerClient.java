import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Leonid Zulin
 * @date 21.07.2023 15:43
 */
public class LoggerClient {

    private static LoggerClient instance = null;
    private static String systemFS = System.getProperty("file.separator");
    private static String loggerName;
    private final Charset charset = Charset.forName("UTF-8");


    public static void setLoggerName(String chatName) {
        loggerName = chatName;

    }


    private final String LOG_DIR;
    private final String USER_LOG_DIR;
    private final boolean APP_TO_END;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");

    public String getLOG_DIR() {
        return LOG_DIR;
    }

    // used pattern Singleton - private constructor
    private LoggerClient() {
        this.APP_TO_END = true;
        this.USER_LOG_DIR = SettingsClient.MAIN_DIR + systemFS + loggerName;
        SettingsClient.makeUserDir(USER_LOG_DIR);
        this.LOG_DIR = USER_LOG_DIR + systemFS + "file.log";

    }

    public static LoggerClient getInstance() {
        if (instance == null) {
            synchronized (LoggerClient.class) {
                if (instance == null) {
                    instance = new LoggerClient();
                }

            }
        }

        return instance;
    }

    public void logWrite(String text) {
        try (FileOutputStream fos = new FileOutputStream(LOG_DIR, APP_TO_END)) {
            synchronized (this) {
                String textLoggingString = getTimeView(LocalDateTime.now()) + " : " + text + "\n";
                System.out.println(textLoggingString);
                fos.write(textLoggingString.getBytes(charset));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String getTimeView(LocalDateTime time) {
        return time.format(DATE_TIME_FORMATTER);
    }


}


